package org.java.order.service;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.pojo.EasybuyUser;
import org.java.pojo.EasybuyUserAddress;

public interface OrderService {
	/**
	 * Id查询地址列表
	 * @return EasybuyUserAddress
	 */
	List<EasybuyUserAddress> getAddressbyUserId(Integer userid);
	/**
	 * 创建订单
	 * @param addressId     地址ID
	 * @param newAddress 	地址
	 * @param newRemark
	 * @param userid 		用户Id
	 * @param loginname 	登录名
	 * @param sum 			数量
	 * @return
	 */
	EasyBuyResult createOrder(Integer addressId,String newAddress,String newRemark,Integer userid,String loginname,Float sum);
	/**
	 * 支付成功修改状态
	 * @param orderId
	 */
	void updateOrder(String orderId);
}
