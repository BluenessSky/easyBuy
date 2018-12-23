package org.java.protal.contorller;

import java.util.List;

import org.java.pojo.EasybuyBigcontent;
import org.java.pojo.Seckill;
import org.java.seckill.service.SeckillService;
import org.java.service.BigContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@Autowired
	private BigContentService bigContentService;
	@Autowired
	private SeckillService seckillService;
	/**
	 * 展示大广告和秒杀商品
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		List<EasybuyBigcontent> bigList = bigContentService.getAllBigContents();
		model.addAttribute("biglist",bigList);
		
		List<Seckill> seckills = seckillService.getSeckills();
		model.addAttribute("seckills", seckills);
		return "index";
	}
}
