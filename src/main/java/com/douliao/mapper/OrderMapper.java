package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.CreateOrderParam;
import com.douliao.controller.server.model.PayHistoryParam;
import com.douliao.model.CreateOrderModel;
import com.douliao.model.PayHistoryModel;

public interface OrderMapper {
	
	CreateOrderModel selGoods_price(CreateOrderParam createOrderParam);
	
	int insOrder(CreateOrderParam createOrderParam);
	
	int updateOrder(Map<String, Object> map);
	
	List<PayHistoryModel> payHistory(PayHistoryParam payHistoryParam);
	
}
