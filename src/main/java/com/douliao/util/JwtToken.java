package com.douliao.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.douliao.controller.server.model.GateParams;
import com.douliao.controller.server.model.LoginParam;

public class JwtToken {
	
	public static void main(String[] args) throws Exception {
		String token=createToken();
		System.out.println("token="+token);
		verifyToken1(token,"secret");
		
		LoginParam loginParam=new LoginParam();
		String secret="password";
		loginParam.setUserId(11);
		loginParam.setLoginMode(1);
		String token2=createToken(loginParam,secret);
		System.out.println(token2);
		GateParams gateParams=new GateParams();
		boolean b=verifyToken(gateParams,secret);
		System.out.println(b);
		
	}
	
	public static String createToken(LoginParam loginParam,String secret) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				.withHeader(map)//header
				.withClaim("userId", String.valueOf(loginParam.getUserId()))//payload
				.withClaim("loginMode", String.valueOf(loginParam.getLoginMode()))
				.withClaim("mac", loginParam.getMac())
				.withClaim("loginTime", loginParam.getLoginTime())
				.sign(Algorithm.HMAC256(secret));//加密
		return token;
	}
	
	/**
	 * 验证
	 * @param token
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyToken(GateParams gateParams,String key){
		JWTVerifier verifier;
		try {
			verifier = JWT.require(Algorithm.HMAC256(key))
			        .build();
			 DecodedJWT jwt = verifier.verify(gateParams.getToken());
			    Map<String, Claim> claims = jwt.getClaims();
			    String userId=claims.get("userId").asString();
			    String mac=claims.get("mac").asString();
			    System.out.println(userId);
			    if((gateParams.getMac()).equals(mac)) {
			    	return true;
			    }
			    return false;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public static String createToken() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				.withHeader(map)//header
				.withClaim("name", "zwz")//payload
				.withClaim("age", "18")
				.sign(Algorithm.HMAC256("secret"));//加密
		return token;
	}
	
	public static void verifyToken1(String token,String key) throws Exception{
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
		        .build(); 
		    DecodedJWT jwt = verifier.verify(token);
		    Map<String, Claim> claims = jwt.getClaims();
		    System.out.println(claims.get("name").asString());
	}
	
}
