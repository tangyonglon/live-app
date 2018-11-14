package com.douliao.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.IMChannelParam;
import com.douliao.controller.server.model.LoginOutParam;
import com.douliao.controller.server.model.LoginParam;
import com.douliao.mapper.UserMapper;
import com.douliao.model.User;
import com.douliao.model.UserGold;
import com.douliao.model.database.LiveAppUser;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.UserToken;
import com.douliao.result.ResultView;
import com.douliao.service.UserService;
import com.douliao.util.CreateToken;
import com.douliao.util.JwtToken;
import com.douliao.util.Log4jUtil;
import com.douliao.util.MD5;
import com.douliao.util.NetUtil;
import com.douliao.util.TimeFormat;

@Service
public class UserServiceImpl implements UserService {
	@Value("${jwt.secret}")
	private String secret;
	@Value("${yunpian.testCode}")
	private String testCode;

	@Autowired
	private UserMapper UserMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public List<User> findAll() {
		return UserMapper.findAll();
	}

	@Override
	public ResultView<LoginParam> login(LoginParam loginParam) {
		ResultView<LoginParam> resultView=new ResultView<LoginParam>();
		if(StringUtils.isNoneBlank(loginParam.getUserAccount())) {
			//第三方登入
			//自动注册
			loginParam.setCreate_time(TimeFormat.getSimple());
			LiveAppUser liveAppUser=UserMapper.selUserInfo(loginParam);
			if(liveAppUser==null) {
				UserMapper.insUser(loginParam);
				if((StringUtils.isNoneBlank(String.valueOf(loginParam.getId())))) {
					loginParam.setUserId(loginParam.getId());
				}
				if(StringUtils.isNoneBlank(loginParam.getUser_name())) {
					loginParam.setUser_name("null");
				}
				if(StringUtils.isNoneBlank(loginParam.getUser_head())) {
					loginParam.setUser_head("null");
				}
				UserMapper.insUserInfo(loginParam);
			}else {
				loginParam.setId(liveAppUser.getId());
				List<Map<String, Object>> list=UserMapper.selUserIdIsHave(loginParam);
				if(!(list.size()>0)) {
					UserMapper.insUserInfo(loginParam);
				}
			}
		}
		
		if(!StringUtils.isNoneBlank(loginParam.getUserPhone()) && !StringUtils.isNoneBlank(loginParam.getUserAccount())) {
			resultView.setCode(2008);
			resultView.setMessage("账户不能为空或不存在该账户");
			return resultView;
		}
		//登入信息确认
		LiveAppUser liveAppUser=UserMapper.login(loginParam);
		if(liveAppUser!=null) {
			if(liveAppUser.getStatus()==2) {
				resultView.setCode(1009);
				resultView.setMessage("该用户已被冻结");
				return resultView;
			}
			loginParam.setUserId(liveAppUser.getId());
			Log4jUtil.info("code参数"+loginParam.getCode());
			if(StringUtils.isNoneBlank(loginParam.getCode())) {
				//1.手机号+验证码登入   校验验证码是否正确
				String value=redisTemplate.opsForValue().get(loginParam.getUserPhone());
				if( !(loginParam.getCode()).equals(testCode) && !(loginParam.getCode()).equals(value)) {
					resultView.setCode(2003);
					resultView.setMessage("验证码错误或已失效");
					return resultView;
				}
			}
			if(StringUtils.isNoneBlank(loginParam.getUserPassword())) {
				//2.手机号+密码登入
				if(!(MD5.GetMD5Code(loginParam.getUserPassword()).equals(liveAppUser.getUser_password()))) {
					resultView.setCode(2009);
					resultView.setMessage("登入失败，密码错误");
					return resultView;
				}
			}
			loginParam.setLoginTime(String.valueOf(new Date().getTime()));
			//MD5生成token
			loginParam.setToken(CreateToken.getLoginToken(loginParam.getMac(),loginParam.getLoginTime()));
			//JWT生成token
//			try {
//				loginParam.setToken(JwtToken.createToken(loginParam, secret));
//			} catch (Exception e) {
//				e.printStackTrace();
//				resultView.setCode(2023);
//				resultView.setMessage("生成token失败");
//				return resultView;
//			}
			if(StringUtils.isNoneBlank(loginParam.getToken())){
				//验证成功的时候生成token存入数据库和redis中
				//查询是否已经在其他设备登入了
				UserToken userToken=UserMapper.selIsOnline(loginParam);
				int result = 0;
				if(userToken!=null) {
					result=UserMapper.updateToken(loginParam);
				}else {
					result=UserMapper.insToken(loginParam);
				}
				if(result>0) {
					Map<String, Object> map=new HashMap<String,Object>();
					map.put("loginTime", loginParam.getLoginTime());
					map.put("token", loginParam.getToken());
					String key=String.valueOf(loginParam.getUserId())+"-"+loginParam.getLoginMode();
//					CreateToken.hmsetAll(key,map);
					redisTemplate.opsForHash().putAll(key, map);
					redisTemplate.expire(key, 24, TimeUnit.HOURS);
					resultView.setCode(1000);
					resultView.setMessage("登入成功");
					loginParam.setPraise_channel(liveAppUser.getPraise_channel());
					loginParam.setComment_channel(liveAppUser.getComment_channel());
					loginParam.setGift_channel(liveAppUser.getGift_channel());
					resultView.setData(loginParam);
				}else {
					resultView.setCode(1002);
					resultView.setMessage("服务器繁忙，请稍后重试");
				}
				return resultView;
			}
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
			return resultView;
		}else {
			resultView.setCode(2011);
			resultView.setMessage("不存在该账户或该账户异常");
			return resultView;
		}
	}

	@Override
	public ResultView<String> loginOut(LoginOutParam loginOutParam) {
		ResultView<String> resultView=new ResultView<String>();
		int result=UserMapper.loginOut(loginOutParam);
		if(result>0 || result==0) {
			String key=String.valueOf(loginOutParam.getUserId())+"-"+loginOutParam.getLoginMode();
			redisTemplate.delete(key);
			resultView.setCode(1000);
			resultView.setMessage("登出成功");
		}else {
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		return resultView;
	}

	@Override
	public ResultView<String> CreateIMChannel(IMChannelParam iMChannelParam) {
		ResultView<String> resultView=new ResultView<String>();
		if(StringUtils.isNoneBlank(iMChannelParam.getComment_channel()) || StringUtils.isNoneBlank(iMChannelParam.getGift_channel()) || StringUtils.isNoneBlank(iMChannelParam.getPraise_channel())) {
			int result=UserMapper.updateIMChannel(iMChannelParam);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		
		return resultView;
	}
	
	@Transactional
	@Override
	public boolean AddUserGold(String order_id) {
		try {
			UserGold userGold=UserMapper.selOrderInfo(order_id);
			//获取该用户信息
			Live_app_user_info live_app_user_info=UserMapper.getUserInfoByUserId(userGold.getUser_id());
			live_app_user_info.setUser_package(live_app_user_info.getUser_package()+Double.parseDouble(userGold.getTitle()));
			UserMapper.updateUserPackage(live_app_user_info);
			return true;
		}catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		
	}
	
	
}
