package com.douliao.mapper;

import java.util.Map;

import com.douliao.controller.server.model.OrderInfo;

public interface CodaPayMapper {
	
	void insOrder(OrderInfo orderInfo);
	
	int updateOrder(Map<String, Object> map);
	
}
