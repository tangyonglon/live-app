package com.douliao.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.OrderInfo;
import com.douliao.mapper.CodaPayMapper;
import com.douliao.service.CodaPayService;

@Service
public class CodaPayServiceImpl implements CodaPayService{
	@Autowired
	private CodaPayMapper codaPayMapper;

	@Override
	public void insOrder(OrderInfo orderInfo) {
		codaPayMapper.insOrder(orderInfo);
	}

	@Override
	public int updateOrder(Map<String, Object> map) {
		return codaPayMapper.updateOrder(map);
	}
	
	
	
}
