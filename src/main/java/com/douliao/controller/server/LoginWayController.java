package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.LoginWayParam;
import com.douliao.model.Pay_way_info;
import com.douliao.model.database.Login_way_info;
import com.douliao.result.ResultView;
import com.douliao.service.LoginWayService;

@RestController
public class LoginWayController {
	@Autowired
	private LoginWayService loginWayService;
	
	
	@RequestMapping(value="/LoginWay/allLogin",method=RequestMethod.POST)
	public ResultView<List<Login_way_info>> getPayWay(LoginWayParam loginWayParam) {
		//根据国家ID和渠道ID获取支付方式
		ResultView<List<Login_way_info>> result=loginWayService.getLoginWay(loginWayParam);
		return result;
	}
	
}
