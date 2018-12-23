package org.java.sso.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/{pages}")
	public String page(@PathVariable("pages") String pages,String url,Model model){
		model.addAttribute("url",url);
		
		return pages;
	}
}
