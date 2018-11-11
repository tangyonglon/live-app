package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.LoginWayParam;
import com.douliao.mapper.LoginWayMapper;
import com.douliao.model.database.Login_way_info;
import com.douliao.model.database.Video_level_filter;
import com.douliao.result.ResultView;
import com.douliao.service.LoginWayService;

@Service
public class LoginWayServiceImpl implements LoginWayService{
	@Autowired
	private LoginWayMapper loginWayMapper;

	@Override
	public ResultView<List<Login_way_info>> getLoginWay(LoginWayParam loginWayParam) {
		ResultView<List<Login_way_info>> resultView=new ResultView<List<Login_way_info>>();
		Video_level_filter video_level_filter=loginWayMapper.selLoginFilter(loginWayParam);
		if(video_level_filter.getLogin_way_id()!=null) {
			loginWayParam.setLogin_way_id(video_level_filter.getLogin_way_id().split(","));
		}
		List<Login_way_info> list=loginWayMapper.selLoginWay(loginWayParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		
		return resultView;
	}


}
