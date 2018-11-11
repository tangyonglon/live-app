package com.douliao.controller.server;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.childThread.LiveRoomCount;
import com.douliao.controller.server.model.FindPasswordParam;
import com.douliao.controller.server.model.RegisterParam;
import com.douliao.controller.server.model.UpdatePasswordParam;
import com.douliao.model.CountryInfo;
import com.douliao.model.database.Country;
import com.douliao.result.ResultView;
import com.douliao.service.RegisterService;
import com.douliao.util.Log4jUtil;
import com.douliao.util.MD5;
import com.douliao.util.RequestCheck;
import com.douliao.util.huaxin.HttpHuaxin;
import com.douliao.util.yunpian.YunpianUtil;


@RestController
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Value("${huaxin.account}")
	private String account;
	
	@Value("${huaxin.password}")
	private String password;
	
	@Value("${huaxin.url}")
	private String url;
	
	@Value("${web.languageId}")
	private int languageId;
	
	@Value("${yunpian.yUrl}")
	private String yUrl;
	
	@Value("${yunpian.apikey}")
	private String apikey;
	
	@Value("${yunpian.template}")
	private String template;
	
	@Value("${yunpian.testCode}")
	private String testCode;
	
	/**
	 * 发送手机验证码接口
	 * @param phone
	 * @throws Exception 
	 */
	@RequestMapping(value="/register/sendAuthCode",method=RequestMethod.POST)
	public ResultView<String> sendAuthCode(String phone,HttpServletRequest request) throws Exception {
		ResultView<String> resultView=new ResultView<String>();
		//检查请求频率
//		if(!(RequestCheck.requestFrequencyCheck(request))) {
//			resultView.setCode(2000);
//			resultView.setMessage("请求频率过高，无效请求");
//			return resultView;
//		}
		//发送验证码
		int code=1234;
		if(StringUtils.isNoneBlank(phone)) {
			if(stringRedisTemplate.hasKey(phone)) {
				code=Integer.parseInt(stringRedisTemplate.opsForValue().get(phone));
			}else {
				//生成六位随机数作为验证码
				code=(int)((Math.random()*6+1)*100000);
				stringRedisTemplate.opsForValue().set(phone, String.valueOf(code), 70, TimeUnit.SECONDS);
			}
			String text=template+":"+String.valueOf(code);
			//发送验证码
//			boolean b=HttpHuaxin.send(url, text, account, password, phone);
			boolean b=YunpianUtil.singleSend(apikey, text, phone, yUrl);
//			boolean b=true;
			if(b) {
				//发送成功 
				resultView.setCode(1000);
				resultView.setMessage("发送验证码成功");
			}else {
				//发送失败
				resultView.setCode(2001);
				resultView.setMessage("发送验证码失败");
			}
		}else {
			resultView.setCode(2002);
			resultView.setMessage("手机号不能为空");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
		
	}
	
	
	/**
	 * 获取所有国家和号码编号
	 * @return
	 */
	@RequestMapping(value="/register/findAllCountry",method=RequestMethod.POST)
	public ResultView<List<CountryInfo>> findAllCountry(Integer language_id) {
		if(language_id==null || language_id==0) {
			language_id=languageId;
		}
		ResultView<List<CountryInfo>> resultView=registerService.findAllCountry(language_id);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
	/**
	 * 注册接口
	 * @param registerParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/register/insRegister",method=RequestMethod.POST)
	public ResultView<RegisterParam> insRegister(RegisterParam registerParam,HttpServletRequest request) {
		ResultView<RegisterParam> resultView = new ResultView<RegisterParam>();
		//检查请求频率
		if(!(RequestCheck.requestFrequencyCheck(request))) {
			resultView.setCode(2000);
			resultView.setMessage("请求频率过高，无效请求");
			return resultView;
		}
		if(StringUtils.isNoneBlank(registerParam.getUserPassword())) {
			String password=MD5.GetMD5Code(registerParam.getUserPassword());
			registerParam.setUserPassword(password);
		}
		if(!(registerParam.getAuthCode()).equals(testCode) ) {
			//1.验证手机验证码是否正确
			if(StringUtils.isNoneBlank(registerParam.getAuthCode())) {
				String value = null;
				String key=registerParam.getPhoneCode()+registerParam.getUserPhone();
				Log4jUtil.info("键值对："+key);
				if(stringRedisTemplate.hasKey(key)) {
					value=stringRedisTemplate.opsForValue().get(key);
					if( !(registerParam.getAuthCode()).equals(value)) {
						resultView.setCode(2003);
						resultView.setMessage("验证码错误或已失效");
						Log4jUtil.info(resultView.toString());
						return resultView;
					}
				}else {
					resultView.setCode(2003);
					resultView.setMessage("验证码错误或已失效");
					Log4jUtil.info(resultView.toString());
					return resultView;
				}
			}else {
				resultView.setCode(2006);
				resultView.setMessage("验证码不能为空");
				Log4jUtil.info(resultView.toString());
				return resultView;
			}
		}
		
		//2.数据存储
		if(StringUtils.isNoneBlank(registerParam.getPhoneCode()) && StringUtils.isNoneBlank(registerParam.getUserPhone()) && registerParam.getCountry_id()!=0 ) {
			resultView=registerService.insRegister(registerParam,resultView);
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 找回密码
	 * @param findPasswordParam
	 */
	@ResponseBody
	@RequestMapping(value="/register/findPassword",method=RequestMethod.POST)
	public ResultView<String> findPassword(FindPasswordParam findPasswordParam) {
		ResultView<String> resultView=new ResultView<String>();
		if(!(findPasswordParam.getCheckCode()).equals(testCode)) {
			//验证手机验证码是否正确
			if(StringUtils.isNoneBlank(findPasswordParam.getCheckCode())) {
				String value=stringRedisTemplate.opsForValue().get(findPasswordParam.getUserPhone());
				if(  !(findPasswordParam.getCheckCode()).equals(value)) {
					resultView.setCode(2003);
					resultView.setMessage("验证码错误或已失效");
					Log4jUtil.info(resultView.toString());
					return resultView;
				}else {
					resultView.setCode(1000);
					resultView.setMessage("成功");
					Log4jUtil.info(resultView.toString());
					return resultView;
				}
			}else {
				resultView.setCode(20006);
				resultView.setMessage("验证码不能为空");
				Log4jUtil.info(resultView.toString());
				return resultView;
			}
		}else {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			Log4jUtil.info(resultView.toString());
			return resultView;
		}
		
	}
	
	/**
	 * 根据手机号设置新密码
	 */
	@ResponseBody
	@RequestMapping(value="/register/updatePassword",method=RequestMethod.POST)
	public ResultView<String> cupdatePassword(UpdatePasswordParam updatePasswordParam) {
		ResultView<String> resultView=new ResultView<String>();
		if(StringUtils.isNoneBlank(updatePasswordParam.getPassword()) && StringUtils.isNoneBlank(updatePasswordParam.getConfirmPassword())) {
			if((updatePasswordParam.getPassword()).equals(updatePasswordParam.getConfirmPassword())) {
				String password=MD5.GetMD5Code(updatePasswordParam.getPassword());
				updatePasswordParam.setPassword(password);
				//修改密码
				int result=registerService.updatePassword(updatePasswordParam);
				if(result>0) {
					resultView.setCode(1000);
					resultView.setMessage("修改成功");
					Log4jUtil.info(resultView.toString());
					return resultView;
				}
			}
		}
		resultView.setCode(2007);
		resultView.setMessage("修改新密码失败");
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
}
