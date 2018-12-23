package org.java.sso.service;

import org.java.common.pojo.EasyBuyResult;
import org.java.pojo.EasybuyUser;

public interface UserService {
	/**
	 * 数据验证用户是否已存在
	 * @param param 请求验证的参数
	 * @param type  类型（1.等录2.邮箱3.手机号
	 * @return      200表示可以注册400表示不可以注册
	 */
	EasyBuyResult checkData(String param,Integer type);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	EasyBuyResult register(EasybuyUser user);
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	EasyBuyResult dologin(String username,String password);
	/**
	 * 根据通行证查询用户对象
	 * @param token
	 * @return
	 */
	EasyBuyResult getByToken(String token);
	/**
	 * 删除cookie
	 * @param token
	 * @return
	 */
	EasyBuyResult logout(String token);
	
	/**
	 * 根据激活码激活账户
	 * @param code
	 * @return
	 */
	EasyBuyResult jihuo(String code);
	boolean checkCode(String phone, String code);
	void sendPhoneMessage(String phone);
}
