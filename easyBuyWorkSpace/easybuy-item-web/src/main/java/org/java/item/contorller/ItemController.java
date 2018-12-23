package org.java.item.contorller;

import org.java.pojo.EasybuyProduct;
import org.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
	@Autowired
	private ProductService productService;
	
	/*redis作为缓存*/
	  @RequestMapping("/item/queryProductDeatil")
	public String queryProductDeatil(@RequestParam("id")Integer id,Model model){
		//根据ID查询商品对策
		System.out.println(id);
		EasybuyProduct byId = productService.getById(id);
		model.addAttribute("product",byId);
		return "productDeatil";
	}
	
	//使用HTML页面作为访问页面
	/*@RequestMapping("/item/queryProductDeatil")
	public String queryProductDeatil(@RequestParam("id")Integer id,Model model){
		//访问id+HTML
		return id+".html";
	}*/
}
