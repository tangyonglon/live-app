package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.IMChannelParam;
import com.douliao.controller.server.model.LoginOutParam;
import com.douliao.controller.server.model.LoginParam;
import com.douliao.model.User;
import com.douliao.model.UserGold;
import com.douliao.model.database.LiveAppUser;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.UserToken;

public interface UserMapper {
	
	List<User> findAll();
	
	LiveAppUser login(LoginParam loginParam);
	
	int updateToken(LoginParam loginParam);
	
	int insToken(LoginParam loginParam);
	
	UserToken selIsOnline(LoginParam loginParam);
	
	int loginOut(LoginOutParam loginOutParam);
	
	UserToken selTokenByUserId(UserToken userToken);
	
	int updateIMChannel(IMChannelParam iMChannelParam);
	
	UserGold selOrderInfo(String order_id);
	
	Live_app_user_info getUserInfoByUserId(int user_id);
	
	int updateUserPackage(Live_app_user_info live_app_user_info);
	
	List<Map<String, Object>> selIpList(String changeip);
	
	LiveAppUser selUserInfo(LoginParam loginParam);
	
	int insUser(LoginParam loginParam);
	
	int insUserInfo(LoginParam loginParam);
	
	List<Map<String, Object>> selUserIdIsHave(LoginParam loginParam);
}
