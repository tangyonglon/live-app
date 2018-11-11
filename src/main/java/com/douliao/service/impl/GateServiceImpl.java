package com.douliao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.GateParams;
import com.douliao.mapper.GateMapper;
import com.douliao.model.database.Gate;
import com.douliao.service.GateService;

@Service
public class GateServiceImpl implements GateService{
	@Autowired
	private GateMapper gateMapper;

	@Override
	public Gate selInterface(GateParams gateParams) {
		return gateMapper.selInterface(gateParams);
	}
	
}
