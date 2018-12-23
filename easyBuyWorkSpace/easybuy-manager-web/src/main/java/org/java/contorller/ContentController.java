package org.java.contorller;

import org.java.pojo.EasybuyBigcontent;
import org.java.service.BigContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContentController {
	@Autowired
	private BigContentService bigContentService;
	
	//跳转进入到添加大广告页面
	@RequestMapping("/content/toadd")
	public String toadd(){
		return "toAddContent";
	}
	@RequestMapping("/content/add")
	public String add(EasybuyBigcontent bigContent){
		bigContentService.saveBigContent(bigContent);
		return "redirect:toadd";
	}
}
