package org.java.cart.contorller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.java.cart.pojo.Cart;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.CookieUtils;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyUser;
import org.java.service.ProductService;
import org.java.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CartController {
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	@Value("${CART_COOKIE_EXPIRE}")
	private Integer CART_COOKIE_EXPIRE;
	@Value("${CART_REDIS_EXPIRE}")
	private Integer CART_REDIS_EXPIRE;
	@Value("${CHARACTER_ENCODING}")
	private String CHARACTER_ENCODING;
	@Value("${REDIS_CART}")
	private String REDIS_CART;
	
	@Autowired
	private UserService userService;
	@Autowired 
	private ProductService productService;
	@Autowired
	private JedisClient jsedisClient;
	
	@RequestMapping("/cart/addToCart")
	public String addToCart(Integer entityId,Integer quantity,HttpServletRequest request,HttpServletResponse response,
							@CookieValue(name="EASYBUY_COOKIE",required=false) String token){
				//1.获取购物车
				List<EasybuyProduct> list=this.getCartList(request, response,token);
				//2.判断要添加的商品是否在购物车中已存在
				//inteage判断范围-128~127
				boolean flag=false;
				for(EasybuyProduct product:list){
					if(product.getId().equals(entityId)){
						//表示要添加的商品在购物车总已存在，修改数量
						product.setStock(product.getStock()+quantity);
						flag=true;//购物车已存在
						break;
					}
				}
				if(flag== false){
					//表示要添加的商品购物车总不存在
					EasybuyProduct product = productService.getById(entityId);
					product.setStock(quantity);
					product.setCategorylevel1id(null);
					product.setCategorylevel2id(null);
					product.setCategorylevel3id(null);
					product.setDescription(null);
					product.setIsdelete(null);
					//添加购物车
					list.add(product);
				}
				CookieUtils.setCookie(request, response,CART_COOKIE,JsonUtils.objectToJson(list),CART_COOKIE_EXPIRE, "Utf-8");
				//判断用户是否登录，取出cookie中的商品信息存入Redis，再将cookie删除
				if(StringUtils.isNoneBlank(token)){
					//表示用户登录过
					//判断是否登录过期
					EasyBuyResult result = userService.getByToken(token);
					if(result.getStatus() == 200){
						//用户真登录成功
						//4.我们关注登录成功了：需要将购物车放入到redis中，然后将cookie中的购物车清除
						//5.以后我们将商品添加到购物车，就直接将商品添加到redis中，取得时候也是直接从redis中取出
						//取出用户对象
						EasybuyUser user = (EasybuyUser) result.getData();
						jsedisClient.set(REDIS_CART + user.getId(), JsonUtils.objectToJson(list));
						//设置存在时间(7天)
						jsedisClient.expire("REDIS_CART:" + user.getId(), CART_REDIS_EXPIRE);
						//将cookie从redis中删除
						CookieUtils.deleteCookie(request, response, CART_COOKIE);
					}
				}
			return "cartSuccess";
	}
	
	public List<EasybuyProduct> getCartList(HttpServletRequest request,HttpServletResponse response,String token){
			List<EasybuyProduct> list=new ArrayList<>();
			if(StringUtils.isNoneBlank(token)){
				//表示用户登录过
				//判断是否登录过期
				EasyBuyResult result = userService.getByToken(token);
				if(result.getStatus() == 200){
					//用户真登录成功
					//取出用户对象
					EasybuyUser user = (EasybuyUser) result.getData();
					//将购物车从reids中取出
					String json = jsedisClient.get(REDIS_CART + user.getId());
					//判断是否之前就有购物车
					if(StringUtils.isBlank(json)){
						return new ArrayList<>();
					}
					//重新设置存在时间(7天)
					jsedisClient.expire("REDIS_CART:" + user.getId(), CART_REDIS_EXPIRE);
					//将json转换成list（购物车）
					list = JsonUtils.jsonToList(json, EasybuyProduct.class);
					return list;
				}
			}
			//从cookie中获取到购物车
			String json = CookieUtils.getCookieValue(request,CART_COOKIE,true);
			//判断之前是否已经在购物车
			if(StringUtils.isNoneBlank(json)){
				//购物车已存在，将json格式字符串转化集合
				return JsonUtils.jsonToList(json, EasybuyProduct.class);
			}
			//购物车不存在,创建购物车
			CookieUtils.setCookie(request, response,CART_COOKIE,JsonUtils.objectToJson(list),CART_COOKIE_EXPIRE,CHARACTER_ENCODING);
			return list;
	}

	@RequestMapping("/cart/showcart")
	public String showcart(Model model,HttpServletRequest request,HttpServletResponse response,@CookieValue(name="EASYBUY_COOKIE",required=false) String token){
		//1.获取到购物车中的商品信息
		List<EasybuyProduct> list = this.getCartList(request, response,token);
		Cart cart=new Cart();
		cart.setItems(list);
		model.addAttribute("cart",cart);
		return "settlement1";
	}
	@RequestMapping("/cart/modCart")
	@ResponseBody
	public EasyBuyResult showcart(Integer entityId,Integer quantity,Model model,
			HttpServletRequest request, HttpServletResponse response,@CookieValue(name="EASYBUY_COOKIE",required=false) String token){
		//取出购物车
		List<EasybuyProduct> list = this.getCartList(request, response,token);
		for (EasybuyProduct product : list) {
			if(product.getId().equals(entityId)){
				product.setStock(quantity);//重新设置库存
				break;	//修改完成后循环结束
			}
		}
		//重新将商品放入到cookie中
		CookieUtils.setCookie(request, response, CART_COOKIE, JsonUtils.objectToJson(list), CART_COOKIE_EXPIRE, "UTF-8");
				if(StringUtils.isNoneBlank(token)){
					//表示用户登录过
					//判断是否登录过期
					EasyBuyResult result = userService.getByToken(token);
					if(result.getStatus() == 200){
					//用户真登录成功
					//4.我们关注登录成功了：需要将购物车放入到redis中，然后将cookie中的购物车清除
					//5.以后我们将商品添加到购物车，就直接将商品添加到redis中，取得时候也是直接从redis中取出
					//取出用户对象
					EasybuyUser user = (EasybuyUser) result.getData();
					jsedisClient.set(REDIS_CART + user.getId(), JsonUtils.objectToJson(list));
					//设置存在时间(7天)
					jsedisClient.expire("REDIS_CART:" + user.getId(), CART_REDIS_EXPIRE);
					//将cookie从redis中删除
					CookieUtils.deleteCookie(request, response, CART_COOKIE);
					}
				}
				//得到修改后的总价格
				Cart cart = new Cart();
				cart.setItems(list);
				return EasyBuyResult.ok(cart.getSum());
	}
	@RequestMapping("/cart/removeInCart")
	@ResponseBody
	public EasyBuyResult removeInCart(Integer entityId,Integer quantity,Model model,
			HttpServletRequest request, HttpServletResponse response,@CookieValue(name="EASYBUY_COOKIE",required=false) String token){
		try{
			List<EasybuyProduct> list = this.getCartList(request, response,token);

			for(int i=0;i<list.size();i++){
				EasybuyProduct product=list.get(i);
				//找到要删除的商品
				if(product.getId().equals(entityId)){
				list.remove(product);
				break;
				}
			}
			//将商品从新放入cookie中
			CookieUtils.setCookie(request, response, CART_COOKIE, JsonUtils.objectToJson(list), CART_COOKIE_EXPIRE, "UTF-8");
			if(StringUtils.isNoneBlank(token)){
				//表示用户登录过
				//判断是否登录过期
				EasyBuyResult result = userService.getByToken(token);
				if(result.getStatus() == 200){
					//用户真登录成功
					//4.我们关注登录成功了：需要将购物车放入到redis中，然后将cookie中的购物车清除
					//5.以后我们将商品添加到购物车，就直接将商品添加到redis中，取得时候也是直接从redis中取出
					//取出用户对象
					EasybuyUser user = (EasybuyUser) result.getData();
					jsedisClient.set(REDIS_CART + user.getId(), JsonUtils.objectToJson(list));
					//设置存在时间(7天)
					jsedisClient.expire("REDIS_CART:" + user.getId(), CART_REDIS_EXPIRE);
					//将cookie从redis中删除
					CookieUtils.deleteCookie(request, response, CART_COOKIE);
				}
			}
			Cart cart = new Cart();
			cart.setItems(list);
		return EasyBuyResult.ok(cart.getSum());
		}catch(Exception e){
			e.printStackTrace();
			return EasyBuyResult.build(400,"购物车中的商品删除失败");
		}
		
	}
}
