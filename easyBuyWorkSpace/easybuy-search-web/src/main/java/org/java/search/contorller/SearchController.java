package org.java.search.contorller;

import org.java.common.pojo.Page;
import org.java.common.pojo.SearchItem;
import org.java.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/item/search")
	public String itemSearch(Model model,String query,Integer pageNow,Integer pagesize) throws Exception{
		//searchService=null;
		//service变量：总的信息条数，总的页数，一页信息集合
		SearchItem result=searchService.getSearchResult(query, pageNow, pagesize);
		//solr中查询到商品列表
		model.addAttribute("productList",result.getProductList());
		Page pager=new Page(result.getPageCount(), "item/search?query=" + query, pageNow, result.getCount());
		model.addAttribute("pager",pager);
		model.addAttribute("query",query);
		return "queryProductList";
	}
}
