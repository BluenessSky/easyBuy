package org.java.sso.contorller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.CookieUtils;
import org.java.common.utils.JsonUtils;
import org.java.pojo.EasybuyUser;
import org.java.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Value("${EASYBUY_COOKIE}")
	private String EASYBUY_COOKIE;
	
	@RequestMapping(value="/user/checkdata")
	@ResponseBody
	public boolean checkData(String param,Integer type){
		EasyBuyResult result = userService.checkData(param, type);
		return (boolean) result.getData();
	}
	
	@RequestMapping("/user/register")
	public String register(EasybuyUser user){
		//将用户持久化到数据库
		userService.register(user);
		return "redirect:/login";
	}
	@RequestMapping("/user/dologin")
	@ResponseBody
	public EasyBuyResult register(String username,String password,
			HttpServletRequest request,HttpServletResponse response,
			Model model){
		EasyBuyResult result = userService.dologin(username, password);
		if(result.getStatus() == 200){
			//将token写入到cookie中
			CookieUtils.setCookie(request, response, EASYBUY_COOKIE, result.getData().toString());
		}
		return result;
	}
	
	/*4.1spring
	 * 
	 */
	/*@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable("token")String token,String callback){
		EasyBuyResult result = userService.getByToken(token);
		if(StringUtils.isNoneBlank(callback)){
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}*/
	
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object getUserByToken(@PathVariable("token")String token,String callback){
		EasyBuyResult result = userService.getByToken(token);
		if(StringUtils.isNoneBlank(callback)){
			String str=callback+"("+JsonUtils.objectToJson(result)+");";
			return str;
		}
		return result;
	}
	@RequestMapping("/user/logout/{token}")
	public String log(HttpServletRequest request,HttpServletResponse response,@PathVariable("token")String token){
		//删除cookie中的token
		CookieUtils.deleteCookie(request, response,EASYBUY_COOKIE);
		//
		return "redirect:/login";
	}
	@RequestMapping("/user/jihuo")
	public String jihuo(String code,Model model){
		EasyBuyResult result=userService.jihuo(code);
		if(result.getStatus()==200){
			return "login";
		}else{
			model.addAttribute("error",result.getMsg());
			return "error";
		}
	}
	
	//手机验证码验证
		@RequestMapping("/user/checkCode")
		@ResponseBody
		public boolean checkCode(String phone,String code){
			boolean result = userService.checkCode(phone, code);
			return result;
		}
		//发送验证码
		@RequestMapping("/user/sendCode")
		@ResponseBody
		public boolean checkCode(String phone){
			//生成验证码
			userService.sendPhoneMessage(phone);
			return true;
		}
}
