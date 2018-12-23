package org.java.seckill.contorller;

import java.util.Date;

import org.java.common.pojo.EasyBuyResult;
import org.java.dto.Exposer;
import org.java.exception.RepeatSeckillException;
import org.java.exception.SeckillClosedException;
import org.java.exception.SeckillException;
import org.java.pojo.Seckill;
import org.java.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeckillContorller {
	@Autowired
	private SeckillService seckillService;
	/**
	 * 根据id查询秒杀商品街对象
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping("/seckill/getSecill")
	public String showSeckill(Long seckillId,Model model){
		Seckill seckill = seckillService.getById(seckillId);
		model.addAttribute("seckill", seckill);
		return "seckill";
	}
	/**
	 * 获取到当前系统时间，判断是该商品已经开始秒杀
	 * @return
	 */
	@RequestMapping("/time/now")
	@ResponseBody
	public EasyBuyResult timeNow(){
		Date now = new Date();		//获取到服务器的时间
		return EasyBuyResult.ok(now.getTime());
	}
	
	/**
	 * 判断是否需要暴漏秒杀接口
	 * @return
	 */
	@RequestMapping("/{seckillId}/exposer")
	@ResponseBody
	public Exposer seckillIdExposer(@PathVariable("seckillId") Long seckillId){
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		return exposer;
	}
	
	/**
	 * 真正的秒杀
	 * @return
	 */
	@RequestMapping("/{seckillId}/{md5}/execution")
	@ResponseBody
	public EasyBuyResult seckillIdExposer(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value="killPhone",required=false) Long userPhone){
		try {
			EasyBuyResult result = seckillService.executeSeckill(seckillId, userPhone, md5);
			return result;
		} catch (SeckillException e) {
			return EasyBuyResult.build(400, e.getMessage());
		} catch (RepeatSeckillException e) {
			return EasyBuyResult.build(400, e.getMessage());
		} catch (SeckillClosedException e) {
			return EasyBuyResult.build(400, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return EasyBuyResult.build(400, "秒杀未知错误");
		}
		
		
	}
	
}
