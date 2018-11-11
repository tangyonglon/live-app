package com.douliao.controller.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.IMChannelParam;
import com.douliao.controller.server.model.LoginOutParam;
import com.douliao.controller.server.model.LoginParam;
import com.douliao.result.ResultView;
import com.douliao.service.UserService;
import com.douliao.util.Log4jUtil;
import com.douliao.util.NetUtil;


@RestController
public class LoginController {
	@Autowired
	private UserService userService;
	
	
	/**
	 * 登入接口
	 */
	@ResponseBody
	@RequestMapping(value="/login/appLogin",method=RequestMethod.POST)
	public ResultView<LoginParam> applogin(LoginParam loginParam,HttpServletRequest request) {
		String ip=NetUtil.getRemortIP(request);
		loginParam.setIp(ip);
		ResultView<LoginParam> resultView=userService.login(loginParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 生成用户独特的IM频道
	 * @param CreateIMChannel
	 * @return
	 */
	@RequestMapping(value="/login/CreateIMChannel",method=RequestMethod.POST)
	public ResultView<String> CreateIMChannel(IMChannelParam iMChannelParam) {
		ResultView<String> resultView=userService.CreateIMChannel(iMChannelParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 登出接口
	 * @param loginParam
	 */
	@ResponseBody
	@RequestMapping(value="/login/appLoginOut",method=RequestMethod.POST)
	public ResultView<String> appLoginOut(LoginOutParam loginOutParam) {
		ResultView<String> resultView=userService.loginOut(loginOutParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
}
