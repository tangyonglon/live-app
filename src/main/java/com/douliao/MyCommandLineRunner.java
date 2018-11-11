package com.douliao;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.douliao.controller.server.agora.SignalUtils;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;

import io.agora.signal.Signal.LoginSession;

@Component
@Order(value=1)
public class MyCommandLineRunner implements ApplicationRunner {
//	@Value("${agora.appID}")
//	private String appid;
//	@Value("${agora.channel}")
//	private String channel;
//	@Value("@{agora.account}")
//	private String account;
	
	/**
	 * 项目启动连接声网信令并且创建频道
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		System.out.println("项目启动连接声网信令并且创建频道");
//		ReadResourceConfigUtils readResourceConfigUtils=new ReadResourceConfigUtils();
//		String appID=readResourceConfigUtils.getRedisConfig("/config/agora.properties","appID");
//		String account=readResourceConfigUtils.getRedisConfig("/config/agora.properties","account");
//		String channel=readResourceConfigUtils.getRedisConfig("/config/agora.properties","channel");
//		SignalUtils signalUtils=new SignalUtils();
//		LoginSession loginSession=signalUtils.login(appID,account);
//		signalUtils.channelJoin(loginSession, channel);
	}
	
	/**
	 * 重新登入信令
	 * @throws InterruptedException 
	 */
	public void loginAgain() throws InterruptedException {
//		Log4jUtil.info("异常退出信令频道，睡眠5秒后重新登入");
//		//睡眠10秒
//		Thread.sleep(2000);
//		ReadResourceConfigUtils readResourceConfigUtils=new ReadResourceConfigUtils();
//		String appID=readResourceConfigUtils.getRedisConfig("/config/agora.properties","appID");
//		String account=readResourceConfigUtils.getRedisConfig("/config/agora.properties","account");
//		String channel=readResourceConfigUtils.getRedisConfig("/config/agora.properties","channel");
//		SignalUtils signalUtils=new SignalUtils();
//		LoginSession loginSession=signalUtils.login(appID,account);
//		Thread.sleep(10000);
//		signalUtils.channelJoin(loginSession, channel);
	}

}
