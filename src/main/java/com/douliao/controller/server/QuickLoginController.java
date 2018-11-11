package com.douliao.controller.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.BindParam;
import com.douliao.controller.server.model.QuickLoginParam;
import com.douliao.result.ResultView;
import com.douliao.service.QuickLoginService;
import com.douliao.util.NetUtil;

@RestController
public class QuickLoginController {
	@Autowired
	private QuickLoginService quickLoginService;
	
	//快速注册，登入
	@RequestMapping(value="/Quick/QuickLogin",method=RequestMethod.POST)
	public ResultView<QuickLoginParam> QuickLogin(QuickLoginParam quickLoginParam,HttpServletRequest request) {
		String ip=NetUtil.getRemortIP(request);
		quickLoginParam.setLogin_ip(ip);
		if(quickLoginParam.getCountry_id()==0) {
			quickLoginParam.setCountry_id(2);
		}
		quickLoginParam.setRegisterWay(4);
		//1.注册
		//2.登入
		return quickLoginService.QuickLogin(quickLoginParam);
	} 
	
	
	
	//绑定手机号或第三方
	@RequestMapping(value="/Quick/bindAccount",method=RequestMethod.POST)
	public ResultView<String> bindAccount(BindParam bindParam){
		return quickLoginService.bindAccount(bindParam);
	}
	
	
}
