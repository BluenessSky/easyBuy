package org.java.order.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private UserService userService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		Cookie[] cookies = request.getCookies();
		String token = "";
		if(cookies == null || cookies.length == 0){
			//表示没有cookie
			//表示cookie中没有token
			response.sendRedirect("http://localhost:8083/login");
			return false;//表示拦截
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("EASYBUY_COOKIE")){
				//取出token
				token = cookie.getValue();
				break;
			}
		}
		if(StringUtils.isBlank(token)){
			//表示cookie中没有token
			response.sendRedirect("http://localhost:8083/login");
			return false;//表示拦截
		}
		//表示cookie中是有token
		//根据token到redis中查询
		EasyBuyResult result = userService.getByToken(token);
		if(result.getStatus() == 400){
			//已经过期
			response.sendRedirect("http://localhost:8083/login");
			return false;//表示拦截
		}
		//表示正常
		request.setAttribute("user", result.getData());
		return true;
	}

}
