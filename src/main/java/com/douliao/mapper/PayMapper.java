package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.PayWayParam;
import com.douliao.model.Pay_way_info;
import com.douliao.model.database.Video_level_filter;

public interface PayMapper {
	
	Video_level_filter selPayFilter(PayWayParam payWayParam);
	
	List<Pay_way_info> selPayWay(PayWayParam payWayParam);
	
}
