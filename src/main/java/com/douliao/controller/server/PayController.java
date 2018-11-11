package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.PayWayParam;
import com.douliao.model.Pay_way_info;
import com.douliao.result.ResultView;
import com.douliao.service.PayService;

@RestController
public class PayController {
	@Autowired
	private PayService payService;
	
	@RequestMapping(value="/pay/payWay",method=RequestMethod.POST)
	public ResultView<List<Pay_way_info>> getPayWay(PayWayParam payWayParam) {
		//根据国家ID和渠道ID获取支付方式
		ResultView<List<Pay_way_info>> result=payService.getPayWay(payWayParam);
		return result;
	}
	
}
