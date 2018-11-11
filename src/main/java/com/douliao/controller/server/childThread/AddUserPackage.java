package com.douliao.controller.server.childThread;

import com.douliao.service.UserService;
import com.douliao.util.Log4jUtil;
import com.douliao.util.SpringContextUtils;

public class AddUserPackage {
	
	
	public static void AddUserGold(String order_id) {
		new Thread() {
			public void run() {
				//执行支付接口后 用户充值后余额增加
				UserService userService=SpringContextUtils.getBean(UserService.class);
				boolean result=userService.AddUserGold(order_id);
				if(!result) {
					Log4jUtil.info("订单号："+order_id+"关联用户失败");
				}
				
			}
		}.start();
	}
	
}
