package com.douliao.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.BindParam;
import com.douliao.controller.server.model.QuickLoginParam;
import com.douliao.mapper.QuickLoginMapper;
import com.douliao.model.database.UserToken;
import com.douliao.result.ResultView;
import com.douliao.service.QuickLoginService;
import com.douliao.util.CreateToken;
import com.douliao.util.Log4jUtil;
import com.douliao.util.MD5;
import com.douliao.util.TimeFormat;

@Service
public class QuickLoginServiceImpl implements QuickLoginService{
	@Autowired
	private QuickLoginMapper quickLoginMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	@Transactional
	@Override
	public ResultView<QuickLoginParam> QuickLogin(QuickLoginParam quickLoginParam) {
		ResultView<QuickLoginParam> resultView=new ResultView<QuickLoginParam>();
		try {
			if(quickLoginParam.getUser_id()==0) {
				quickLoginParam.setCreate_time(TimeFormat.getSimple());
				//1.注册
				quickLoginMapper.insRegister(quickLoginParam);
				if(quickLoginParam.getId()>0) {
					quickLoginParam.setUser_id(quickLoginParam.getId());
					quickLoginMapper.insUserInfo(quickLoginParam);
				}
			}
			//2.登入
			quickLoginParam.setLoginTime(String.valueOf(new Date().getTime()));
			quickLoginParam.setToken(CreateToken.getLoginToken(quickLoginParam.getMac(),quickLoginParam.getLoginTime()));
			quickLoginParam.setLoginMode(4);
			quickLoginParam.setStatus(1);
			//检测是否已登入
			UserToken userToken=quickLoginMapper.selIsOnline(quickLoginParam);
			int result = 0;
			if(userToken!=null) {
				result=quickLoginMapper.updateToken(quickLoginParam);
			}else {
				result=quickLoginMapper.insToken(quickLoginParam);
			}
			if(result>0) {
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("loginTime", quickLoginParam.getLoginTime());
				map.put("token", quickLoginParam.getToken());
				String key=String.valueOf(quickLoginParam.getUser_id())+"-"+quickLoginParam.getLoginMode();
//				CreateToken.hmsetAll(key,map);
				redisTemplate.opsForHash().putAll(key, map);
				resultView.setCode(1000);
				resultView.setMessage("登入成功");
				resultView.setData(quickLoginParam);
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		}catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
	        return resultView;
		}
		
		return resultView;
		
	}


	@Override
	public ResultView<String> bindAccount(BindParam bindParam) {
		ResultView<String> resultView=new ResultView<String>();
		try {
			//1.验证手机验证码是否正确
			if(StringUtils.isNoneBlank(bindParam.getAuthCode())) {
				String value = null;
				String key=bindParam.getPhoneCode()+bindParam.getUser_phone();
				Log4jUtil.info("键值对："+key);
				if(redisTemplate.hasKey(key)) {
					value=redisTemplate.opsForValue().get(key);
					if( !(bindParam.getAuthCode()).equals(value)) {
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
			if(StringUtils.isNoneBlank(bindParam.getPassword())) {
				String password=MD5.GetMD5Code(bindParam.getPassword());
				bindParam.setPassword(password);
			}
			int result=quickLoginMapper.updateAccount(bindParam);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		} catch (Exception e) {
			resultView.setCode(2005);
			resultView.setMessage("注册失败,该手机号已注册");
		}
		
		return resultView;
	}

}
