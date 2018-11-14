package com.douliao.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.douliao.mapper.UserMapper;
import com.douliao.model.database.UserToken;
import com.douliao.service.CheckTokenService;
import com.douliao.util.CreateToken;
import com.douliao.util.IpUtil;
import com.douliao.util.Log4jUtil;
import com.douliao.util.NetUtil;

@Service
public class CheckTokenServiceImpl implements CheckTokenService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	/**
	 * 验证token是否相同
	 * @param userId
	 * @param loginMode
	 * @param mac
	 * @param token
	 * @return
	 */
	public boolean checkToken(int userId,int loginMode,String mac,String token) {
		String loginTime=null;
//		Map<Object, Object> map=CreateToken.hmgetAll(String.valueOf(userId)+"-"+String.valueOf(loginMode));
		Map<Object, Object> map=redisTemplate.opsForHash().entries(String.valueOf(userId)+"-"+String.valueOf(loginMode));
		if(map.isEmpty() && map.size()==0) {
			//缓存中没有数据 则去数据库中取
			UserToken userToken=new UserToken();
			userToken.setUser_id(userId);
			userToken.setLogin_mode(loginMode);
			userToken.setMac(mac);
			userToken.setToken(token);
			userToken.setStatus(1);
			Map<String, Object> map2=new HashMap<String,Object>();
			try {
				userToken=userMapper.selTokenByUserId(userToken);
				Log4jUtil.info("时间："+userToken.getLogin_time());
				if(StringUtils.isNoneBlank(userToken.getLogin_time())) {
					map2.put("loginTime", userToken.getLogin_time());
					map2.put("token", userToken.getToken());
				}else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			
			
			String key=String.valueOf(userToken.getUser_id())+"-"+String.valueOf(userToken.getLogin_mode());
			redisTemplate.opsForHash().putAll(key, map2);
			redisTemplate.expire(key, 24, TimeUnit.HOURS);
//			CreateToken.hmsetAll(key,map2);
			loginTime=userToken.getLogin_time();
		}else {
			loginTime=(String) map.get("loginTime");
		}
		if(token.equals(CreateToken.getLoginToken(mac, loginTime))) {
			//token相同
			return true;
		}
		return false;
	}
	
	
	/**
	 * 过滤中国ip
	 * @param request
	 * @return
	 */
	public boolean blackIp(HttpServletRequest request) {
		String ip=NetUtil.getRemortIP(request);
		Log4jUtil.info("获取到的ip："+ip);
		String changeip=ip.replaceAll("\\.", "-");
		//changeip=changeip.replaceFirst("-", ".");
		changeip=StringUtils.substringBefore(changeip, "-");
		changeip=changeip+".";
		System.out.println(changeip);
		List<Map<String, Object>> list=userMapper.selIpList(changeip);
		String ipSection="";
		boolean result=false;
		for(int i=0;i<list.size();i++) {
			ipSection=String.valueOf(list.get(i).get("begin"))+"-"+String.valueOf(list.get(i).get("end"));
			Log4jUtil.info("ip段:"+ipSection);
			result=IpUtil.ipExistsInRange(ip, ipSection);
			if(result) {
				//存在则属于中国ip
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		String ip="183.17.233.236";
		String changeip=ip.replaceAll("\\.", "-");
		//changeip=changeip.replaceFirst("-", ".");
		changeip=StringUtils.substringBefore(changeip, "-");
		changeip=changeip+".";
		System.out.println("changeip"+changeip);
		String ipSection="183.0.0.0"+"-"+"183.71.255.255";
		boolean result=IpUtil.ipExistsInRange(ip, ipSection);
		System.out.println("结果："+result);
		
	}
	
}
