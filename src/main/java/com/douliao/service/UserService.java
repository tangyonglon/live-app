package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.IMChannelParam;
import com.douliao.controller.server.model.LoginOutParam;
import com.douliao.controller.server.model.LoginParam;
import com.douliao.model.User;
import com.douliao.model.UserGold;
import com.douliao.result.ResultView;

public interface UserService {
	List<User> findAll();
	
	ResultView<LoginParam> login(LoginParam loginParam);
	
	
	ResultView<String> loginOut(LoginOutParam loginOutParam);
	
	ResultView<String> CreateIMChannel(IMChannelParam iMChannelParam);
	
	boolean AddUserGold(String order_id);
}
