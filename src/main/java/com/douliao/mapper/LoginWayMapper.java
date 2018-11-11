package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.LoginWayParam;
import com.douliao.model.database.Login_way_info;
import com.douliao.model.database.Video_level_filter;

public interface LoginWayMapper {
	
	Video_level_filter selLoginFilter(LoginWayParam loginWayParam);
	
	List<Login_way_info> selLoginWay(LoginWayParam loginWayParam);
	
}
