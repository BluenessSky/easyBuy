package org.java.contorller;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.pojo.Page;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyProductCategory;
import org.java.service.CategoryService;
import org.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * 测试：根据商品编号获取到商品对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/show/{id}")
	@ResponseBody
	public EasybuyProduct deleteById(Integer id){
		return productService.getById(id);
	}
	/**
	 * 展示商品信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/product/list")
	@ResponseBody
	public ModelAndView list(ModelAndView mv,Integer currentPage,Integer pageSize){
		PageInfo<EasybuyProduct> pageInfo = productService.getProducts(currentPage, pageSize);
		mv.addObject("productList",pageInfo.getList());
		Page pager=new Page(pageInfo.getPages(),"product/list?1=1",pageInfo.getPageNum(),pageInfo.getTotal());
		mv.addObject("pager",pager);
		mv.setViewName("productList");
		return mv;
	}
	
	@RequestMapping("/product/toadd")
	public ModelAndView toAdd(ModelAndView mv,Integer categorylevel1id,Integer categorylevel2id){
		EasyBuyResult result = categoryService.getcategory1id(0);
		List<EasybuyProductCategory> list=(List<EasybuyProductCategory>) result.getData();
		mv.addObject("productCategoryList1",list);
		mv.addObject("menu",6);
		mv.setViewName("toAddProduct");
		return mv;
	}
	@RequestMapping("/product/add")
	public String Add(EasybuyProduct product,Model model){
		productService.updateOrSave(product);
		model.addAttribute("menu",6);
		return "toAddProduct";
	}
	@RequestMapping("/product/toupdate")
	public ModelAndView toUpda(Integer id,ModelAndView mv,Integer categorylevel1id,Integer categorylevel2id){
		EasybuyProduct byId = productService.getById(id);
		mv.addObject("product",byId);
		EasyBuyResult result = categoryService.getcategory1id(0);
		List<EasybuyProductCategory> list=(List<EasybuyProductCategory>) result.getData();
		EasyBuyResult productCategoryList2 = categoryService.getcategory1id(byId.getCategorylevel1id());
		EasyBuyResult productCategoryList3 = categoryService.getcategory1id(byId.getCategorylevel2id());
		mv.addObject("productCategoryList1",list);
		mv.addObject("productCategoryList2",(List<EasybuyProductCategory>)productCategoryList2.getData());
		mv.addObject("productCategoryList3",(List<EasybuyProductCategory>)productCategoryList3.getData());
		mv.addObject("menu",6);
		mv.setViewName("toAddProduct");
		return mv;
	}
	@RequestMapping("/product/deleteById")
	@ResponseBody
	public EasyBuyResult show(Integer id){
		System.out.println(id);
		EasyBuyResult result = productService.deleteProduct(id);
		return result;
	}
	
}
