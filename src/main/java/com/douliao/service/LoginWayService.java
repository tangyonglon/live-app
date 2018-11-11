package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.LoginWayParam;
import com.douliao.model.database.Login_way_info;
import com.douliao.result.ResultView;

public interface LoginWayService {
	
	ResultView<List<Login_way_info>> getLoginWay(LoginWayParam loginWayParam);
	
}
