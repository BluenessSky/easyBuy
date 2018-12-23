package org.java.contorller;

import org.java.common.pojo.EasyBuyResult;
import org.java.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SolrController {
	@Autowired
	private SearchService searchService;
	/**
	 * 跳转到上传商品到solr页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/solr")
	public String solr(Model model){
		model.addAttribute("menu",8);
		return "solr";
	}
	@RequestMapping("/solr/intosolr")
	@ResponseBody
	public EasyBuyResult intosolr(){
		//调用search中的service来实现上传到solr
		EasyBuyResult result = searchService.insertAllProductToSolr();
		return result;
	}
}
