package com.douliao.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.douliao.service.NewLiveService;
import com.douliao.service.SchedulerService;
import com.douliao.service.impl.SchedulerServiceImpl;
import com.douliao.util.Log4jUtil;
import com.douliao.util.SpringContextUtils;
import com.douliao.util.TimeFormat;


@Component
public class SchedulerController {
	
	//每日00:00:00添加主播当天积分计算记录
	//轮询扣费 主播积分增加
	//轮询心跳数据 
	
	/**
	 * 每日凌晨 给每个主播添加一条直播收费记录
	 */
	@Scheduled(cron="0 0 0 * * ?")
	public void counter() {
		Log4jUtil.info("计数器日期："+TimeFormat.getSimple());
		SchedulerService schedulerService=SpringContextUtils.getBean(SchedulerServiceImpl.class);
		//获取所有主播
		schedulerService.selAllRoom();
		
	}
	
	
	
	/**
	 * 轮询计费
	 * 一分钟轮询一次
	 */
	@Scheduled(cron="2 0/1 * * * ?")
	public void pollCharging() {
		Log4jUtil.info("扣费轮询日期："+TimeFormat.getSimple());
		SchedulerService schedulerService=SpringContextUtils.getBean(SchedulerServiceImpl.class);
		//轮询扣费
		schedulerService.selRoomChat();
	}
	
	
	/**
	 * 轮询心跳数据 
	 * 如果是用户客户端没有心跳让该用户退出直播间 停止轮询扣费
	 * 如果是主播客户端没有心跳了则让该直播间的所有用户退出直播间，停止轮询扣费，关闭该直播的直播
	 */
	@Scheduled(cron="1 0/1 * * * ?")
	public void close() {
		Log4jUtil.info("心跳轮询日期："+TimeFormat.getSimple());
		StringRedisTemplate stringRedisTemplate=SpringContextUtils.getBean(StringRedisTemplate.class);
		Set<String> keys=stringRedisTemplate.keys("live"+"*");
		for (String str : keys) {
		      System.out.println(str);
		      String value=stringRedisTemplate.opsForValue().get(str);
		      int count=Integer.parseInt(value);
		      //连续三次没有发送心跳则停止扣费 没有连续三次则累计次数
		      if(count>=3) {
		    	  String[] array=str.split("-");
		    	  NewLiveService newLiveService=SpringContextUtils.getBean(NewLiveService.class);
		    	  Map<String, Object> map=new HashMap<String,Object>();
		    	  System.out.println(array[0]+"="+array[1]+"="+array[2]+"="+array[3]);
	    		  map.put("live_room_id", array[1]);
	    		  map.put("user_id", array[2]);
	    		  System.out.println("------------");
		    	  if("1".equals(array[3])) {
		    		  //主播客户端  如果是主播客户端没有心跳了则让该直播间的所有用户退出直播间，停止轮询扣费，关闭该直播的直播
		    		  newLiveService.updateRoomStatus(map);
		    		  
		    	  }
		    	  if("2".equals(array[3])) {
		    		  //用户客户端  如果是用户客户端没有心跳让该用户退出直播间 停止轮询扣费
		    		  newLiveService.updatechat(map);
		    	  }
		      }else {
		    	  count=count+1;
		    	  stringRedisTemplate.opsForValue().set(str, String.valueOf(count));
		      }
		}
	}
	
	
}
