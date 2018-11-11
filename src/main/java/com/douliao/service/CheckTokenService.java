package com.douliao.service;

import javax.servlet.http.HttpServletRequest;

public interface CheckTokenService {
	
	boolean checkToken(int userId,int loginMode,String mac,String token);
	
	boolean blackIp(HttpServletRequest request);
	
}
