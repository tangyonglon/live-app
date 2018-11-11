package com.douliao.controller.server.childThread;

import com.douliao.service.LiveService;
import com.douliao.util.SpringContextUtils;

public class ChannelUserJoined {
	
	public static void getStartChatTime(int userId) {
		new Thread() {
			public void run() {
				LiveService liveService=SpringContextUtils.getBean(LiveService.class);
				liveService.insChatStartTime(userId);
			}
		}.start();
	}
	
	
	public static void getEndChatTime(int userId) {
		new Thread() {
			public void run() {
				LiveService liveService=SpringContextUtils.getBean(LiveService.class);
				liveService.insChatEndTime(userId);
			}
		}.start();
	}
}
