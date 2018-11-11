package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.PayWayParam;
import com.douliao.model.Pay_way_info;
import com.douliao.result.ResultView;

public interface PayService {
	
	ResultView<List<Pay_way_info>> getPayWay(PayWayParam payWayParam);
	
}
