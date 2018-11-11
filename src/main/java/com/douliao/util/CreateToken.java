package com.douliao.util;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class CreateToken {
	@Autowired
	private StringRedisTemplate redisTemplate;
//	@Autowired
//	private RedisTemplate<String,Object> redisTemplate;
	
	public static String getLoginToken(String mac,String loginTime) {
		String token=null;
		//生成token规则
		//md5(mac+时间戳)
		if(StringUtils.isNoneBlank(mac) && StringUtils.isNoneBlank(loginTime)) {
			token=MD5.GetMD5Code(mac+loginTime);
		}
		return token;
	}
	
	public static Boolean hmsetAll(String key,Map<String, Object> map) {
		CreateToken createToken=new CreateToken();
		return createToken.hmset(key,map);
	}
	
	public Boolean hmset(String key,Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Map<Object, Object> hmgetAll(String key){
		CreateToken createToken=new CreateToken();
		return createToken.hmget(key);
	}
	
	public Map<Object, Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}
}
