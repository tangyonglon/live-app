package com.douliao.service;

import java.util.Map;

import com.douliao.controller.server.model.OrderInfo;

public interface CodaPayService {
	
	void insOrder(OrderInfo orderInfo);
	
	int updateOrder(Map<String, Object> map);
	
}
