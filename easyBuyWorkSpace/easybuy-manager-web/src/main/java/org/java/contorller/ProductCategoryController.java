package org.java.contorller;



import javax.servlet.http.HttpServletResponse;

import org.java.common.pojo.EasyBuyResult;
import org.java.common.pojo.Page;
import org.java.common.vo.CategoryVO;
import org.java.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

@Controller
public class ProductCategoryController {
	@Autowired
	private CategoryService categoryService;
	@RequestMapping(value="/queryProductCategoryList",method=RequestMethod.POST)
	@ResponseBody
	public  EasyBuyResult queryPr(Integer parentId,HttpServletResponse response){
		 EasyBuyResult result = categoryService.getcategory1id(parentId);
		return result;
	}
	@RequestMapping(value="/categorylist",method=RequestMethod.GET)
	public String toCategoryList(Model model,Integer currentPage,Integer pageSize){
		PageInfo<CategoryVO> pageInfo = categoryService.getCategoryPage(currentPage, pageSize);
		model.addAttribute("productCategoryList", pageInfo.getList());
		Page pager=new Page(pageInfo.getPages(),"categorylist?1=1",pageInfo.getPageNum(),pageInfo.getTotal());
		model.addAttribute("pager", pager);
		model.addAttribute("menu",4);
		return "productCategoryList";
	}
	@RequestMapping(value="/toAddProductCategory",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView toAddProductCategory(){
		ModelAndView mv = new ModelAndView("toAddProductCategory");
		return mv;
	}
}
