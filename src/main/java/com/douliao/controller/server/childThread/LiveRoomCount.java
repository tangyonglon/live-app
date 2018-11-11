package com.douliao.controller.server.childThread;

import java.util.List;

import com.douliao.model.CountryInfo;
import com.douliao.result.ResultView;
import com.douliao.service.RegisterService;
import com.douliao.util.SpringContextUtils;

public class LiveRoomCount {
	
	public void countTotal() {
		new Thread() {
			public void run() {
				RegisterService registerService=SpringContextUtils.getBean(RegisterService.class);
				int language_id=1;
				//结算直播间的总金额       主播当天的总金额 该直播间赚取的积分 该直播间的时长 当日聊天时长
				
				ResultView<List<CountryInfo>> resultView=registerService.findAllCountry(language_id);
				System.out.println("子线程结果："+resultView.toString());
				
			}
		}.start();
	}
}
