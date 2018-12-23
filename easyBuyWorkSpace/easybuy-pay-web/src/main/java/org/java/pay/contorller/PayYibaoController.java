package org.java.pay.contorller;

import org.java.order.service.OrderService;
import org.java.pay.utils.PaymentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayYibaoController {
	@Autowired
	private OrderService orderService;
	@RequestMapping("/pay/yibao")
	public String confirmPay(String orderId,String pdFrpId,Model model){
		String p0_Cmd = "Buy";				//业务类型
		String p1_MerId = "10001126856";	//商户编号
		String p2_Order = orderId;			// 商户订单号(如果不写，那么易宝支付会为我们生成一个订单号),如果用的订单号是自己的订单号，那么一旦是跟之前用易宝支付重复的订单号那么会导致无法支付
		String p3_Amt = "0.01";				//支付金额(为了保证能正常的测试，所以设置每次支付一分钱,正常情况下应该是每次要传递过来要支付的金额)
		String p4_Cur = "CNY";				//交易币种
		String p5_Pid = "yimaiwang_name";	//商品名称
		String p6_Pcat = "shouji";			//商品种类
		String p7_Pdesc = "form easybuy";	// 商品描述
		String p8_Url = "http://localhost:8086/pay/success";	//商户接收支付成功数据的地址
		String p9_SAF = "0";				// 送货地址为“1”: 需要用户将送货地址留在易宝支付系统;为“0”: 不需要，默认为 ”0”.
		String pa_MP = "";					//商户扩展信息
		String pd_FrpId = pdFrpId;			//支付通道编码
		String pr_NeedResponse = "1";		//应答机制
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";//商户密钥
		//签名数据
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		model.addAttribute("p0_Cmd", p0_Cmd);
		model.addAttribute("p1_MerId", p1_MerId);
		model.addAttribute("p2_Order", p2_Order);
		model.addAttribute("p3_Amt", p3_Amt);
		model.addAttribute("p4_Cur", p4_Cur);
		model.addAttribute("p5_Pid", p5_Pid);
		model.addAttribute("p6_Pcat", p6_Pcat);
		model.addAttribute("p7_Pdesc", p7_Pdesc);
		model.addAttribute("p8_Url", p8_Url);
		model.addAttribute("p9_SAF", p9_SAF);
		model.addAttribute("pa_MP", pa_MP);
		model.addAttribute("pd_FrpId",pd_FrpId);
		model.addAttribute("pr_NeedResponse", pr_NeedResponse);
		model.addAttribute("hmac", hmac);
		return "confrimpay";
	}
	
	@RequestMapping("/pay/success")
	public String success(String r6_Order,String r3_Amt,Model model,String r1_Code){
		model.addAttribute("r6_Order", r6_Order);	//订单号
		model.addAttribute("r3_Amt", r3_Amt);	//支付金额
		//修改订单状态
		if("1".equals(r1_Code)){
			//创建pay的聚合工程来实现对订单状态的修改
			orderService.updateOrder(r6_Order);
		}
		return "success";
	}
}
