package com.douliao.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.PayWayParam;
import com.douliao.mapper.PayMapper;
import com.douliao.model.Pay_way_info;
import com.douliao.model.database.Video_level_filter;
import com.douliao.result.ResultView;
import com.douliao.service.PayService;
import com.douliao.util.Log4jUtil;

@Service
public class PayServiceImpl implements PayService{
	@Autowired
	private PayMapper payMapper;
	
	@Override
	public ResultView<List<Pay_way_info>> getPayWay(PayWayParam payWayParam) {
		ResultView<List<Pay_way_info>> resultView=new ResultView<List<Pay_way_info>>();
		Log4jUtil.info("渠道ID："+String.valueOf(payWayParam.getChannel_id()));
		if(payWayParam.getChannel_id()==0) {
			payWayParam.setChannel_id(0);
		}
		if(StringUtils.isNoneBlank(String.valueOf(payWayParam.getChannel_id()))) {
			Video_level_filter video_level_filter=payMapper.selPayFilter(payWayParam);
			if(video_level_filter.getPay_way_id()!=null) {
				payWayParam.setPay_way(video_level_filter.getPay_way_id().split(","));
			}
		}
		List<Pay_way_info> list=payMapper.selPayWay(payWayParam);
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
