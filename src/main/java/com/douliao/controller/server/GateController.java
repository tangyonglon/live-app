package com.douliao.controller.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.GateParams;
import com.douliao.model.database.Gate;
import com.douliao.result.ResultView;
import com.douliao.service.CheckTokenService;
import com.douliao.service.GateService;
import com.douliao.util.JwtToken;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;

@RestController
public class GateController {
	@Autowired
	private GateService gateService;
	@Autowired
	private CheckTokenService checkTokenService;
	@Value("${jwt.secret}")
	private String secret;
	
	@ResponseBody
	@RequestMapping(value="/open/gate.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public ResultView<String> openGate(GateParams gateParams,HttpServletRequest request,HttpServletResponse response) {
		response.reset();
		ResultView<String> resultView=new ResultView<String>();
		
		//获取开关 判断是否需要过滤ip
		String status=new ReadResourceConfigUtils().getRedisConfig("/config/globalConfig.properties", "country_ip_status");
		//status  1标识要过滤 2.标识不需要过滤
		if("1".equals(status)) {
			boolean r=checkTokenService.blackIp(request);
			if(r) {
				resultView.setCode(1010);
				resultView.setMessage("访问限制");
				Log4jUtil.info(resultView.toString());
				return resultView;
			}
		}
		
		Log4jUtil.info("关键字参数"+gateParams.getKeyword());
		if(!StringUtils.isNoneBlank(gateParams.getKeyword())) {
			resultView.setCode(2010);
			resultView.setMessage("参数不能为空，请检查提交的参数");
			Log4jUtil.info(resultView.toString());
			return resultView;
		}
		//根据关键字获取接口地址
		Gate gate=gateService.selInterface(gateParams);
		if(gate==null) {
			resultView.setCode(2010);
			resultView.setMessage("参数不能为空，请检查提交的参数");
			Log4jUtil.info(resultView.toString());
			return resultView;
		}
		//模块ID 1.需要验证token模块 2.不需要验证token模块
		if(gate.getModule_id()==1) {
			if(StringUtils.isNoneBlank(gateParams.getToken())) {
				//根据token检测是否已经登入
				//存储token方式 验证token的有效性
				boolean check=checkTokenService.checkToken(gateParams.getUserId(), gateParams.getLoginMode(), gateParams.getMac(), gateParams.getToken());
				//JWT方式验证token
//				boolean check=JwtToken.verifyToken(gateParams, secret);
				if(!check) {
					resultView.setCode(2012);
					resultView.setMessage("token失效，请重新登入");
					Log4jUtil.info(resultView.toString());
					return resultView;
				}
			}else {
				resultView.setCode(2010);
				resultView.setMessage("参数不能为空，请检查提交的参数");
				Log4jUtil.info(resultView.toString());
				return resultView;
			}
			
		}
		try {
			request.getRequestDispatcher(gate.getInterface_url()).forward(request,response);
			return resultView;
		} catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.info(resultView.toString());
			return resultView;
		}
	}
}
