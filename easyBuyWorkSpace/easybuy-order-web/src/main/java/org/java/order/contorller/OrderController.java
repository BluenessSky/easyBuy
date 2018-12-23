package org.java.order.contorller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.order.service.OrderService;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyUser;
import org.java.pojo.EasybuyUserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CART}")
	private String REDIS_CART;
	@Value("${CART_REDIS_EXPIRE}")
	private Integer CART_REDIS_EXPIRE;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/settlement2")
	public String confirmOrder(HttpServletRequest response,Model model,@CookieValue(name="EASYBUY_COOKIE",required=false) String token){
		EasybuyUser user = (EasybuyUser)response.getAttribute("user");
		//将对当前登录的用户放入到模型
		model.addAttribute("loginUser", user);
		//购物车中的商品列表
		String json = jedisClient.get(REDIS_CART + user.getId());
		//转换成list集合
		List<EasybuyProduct> list = JsonUtils.jsonToList(json, EasybuyProduct.class);
		model.addAttribute("items", list);
		Float totalPrice = 0F;
		for (EasybuyProduct product : list) {
			totalPrice += product.getPrice() * product.getStock();
		}
		model.addAttribute("sum", totalPrice);
		//展示用户的收货地址
		List<EasybuyUserAddress> addresslist = orderService.getAddressbyUserId(user.getId());
		model.addAttribute("userAddressList", addresslist);
		return "settlement2";
	}
	/**
	 * 创建订单
	 * @return
	 */
	@RequestMapping("/order/settlement3")
	public String createOrder(Integer addressId,String newAddress,String newRemark,Integer userid,String loginname,
			Float sum,Model model){
		EasyBuyResult result = orderService.createOrder(addressId, newAddress, newRemark, userid, loginname, sum);//创建订单
		model.addAttribute("currentOrder", result.getData());
		return "settlement3";
	}
}
