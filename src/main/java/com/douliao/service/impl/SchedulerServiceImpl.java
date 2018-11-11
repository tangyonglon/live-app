package com.douliao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.mapper.SchedulerMapper;
import com.douliao.service.SchedulerService;
import com.douliao.util.TimeFormat;

@Service
public class SchedulerServiceImpl implements SchedulerService{
	@Autowired
	private SchedulerMapper schedulerMapper;

	@Override
	public void selRoomChat() {
		//获取需要扣费的所有用户
		List<Map<String, Object>> list=schedulerMapper.selRoomChat();
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				//赛选出余额不足的用户
				double user_package=(double) list.get(i).get("user_package");
				double live_price=(double) list.get(i).get("live_price");
				double live_profit=(double) list.get(i).get("live_profit");
				String user_id=String.valueOf(list.get(i).get("user_id"));
				String channelName=String.valueOf(list.get(i).get("live_room_id"));
				list.get(i).put("score", live_price*live_profit);
				list.get(i).put("date", TimeFormat.getDate());
				if(user_package-live_price<0) {
					
//					//通过IM发送余额不足的信息
//					PubnubUtil pubnubUtil=new PubnubUtil();
//					JsonObject position = new JsonObject();
//					position.addProperty("user_id", user_id);
//					position.addProperty("user_package", user_package);
//					position.addProperty("message", "余额不足");
//					//发布消息
//					pubnubUtil.publish(position, channelName);
					
					//发送完之后剔除该数据
					list.remove(i);
				}else {
					list.get(i).put("user_package", user_package-live_price);
				}
			}
			if(list.size()>0) {
				System.out.println(list.toString());
				//所有需要扣费的用户进行扣费
				schedulerMapper.updatePackage(list);
				//主播积分增加
				schedulerMapper.updateScore(list);
				//直播间总积分累计
				schedulerMapper.updateTotalScore(list);
				
			}
		}
	}

	@Override
	public void selAllRoom() {
		List<Map<String, Object>> list=schedulerMapper.selAllRoom();
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				list.get(i).put("date", TimeFormat.getDate());
			}
			schedulerMapper.insIncome(list);
		}
		
	}
	
	
	
	
}
