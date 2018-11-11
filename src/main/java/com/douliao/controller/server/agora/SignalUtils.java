package com.douliao.controller.server.agora;


import java.util.List;

import com.douliao.MyCommandLineRunner;
import com.douliao.controller.server.childThread.ChannelUserJoined;
import com.douliao.util.Transform;

import io.agora.signal.Signal;
import io.agora.signal.Signal.LoginSession;
import io.agora.signal.Signal.LoginSession.Channel;

public class SignalUtils {
	
	
	/**
	 * 登入信令
	 * @param appid
	 */
	public LoginSession login(String appid,String account) {
		Signal signal=new Signal(appid);
		//开启打印日志调试
//		signal.setDoLog(true);
		
		LoginSession loginSession=signal.login(account, "_no_need_token", new Signal.LoginCallback() {
            @Override
            public void onLoginSuccess(final Signal.LoginSession session, int uid) {
            	super.onLoginSuccess(session, uid);
            	System.out.println("登入成功回调，用户ID为："+uid);
            }
            
            @Override
            public void onLoginFailed(LoginSession session, int ecode) {
            	super.onLoginFailed(session, ecode);
            	System.out.println("登入失败回调，状态码为："+ecode);
            	MyCommandLineRunner myCommandLineRunner=new MyCommandLineRunner();
                try {
					myCommandLineRunner.loginAgain();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }


            @Override
            public void onLogout(Signal.LoginSession session, int ecode) {
                System.out.println("登出回调");
                MyCommandLineRunner myCommandLineRunner=new MyCommandLineRunner();
                try {
					myCommandLineRunner.loginAgain();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }

            @Override
            public void onMessageInstantReceive(Signal.LoginSession session, String account, int uid, String msg) {
               System.out.println("用户："+account+",ID:"+uid+",收到的消息MSG:"+msg);
            }
        });
		
		return loginSession;
	}
	
	/**
	 * 加入频道
	 * @param loginSession
	 * @param channelName
	 * @return
	 */
	public Channel channelJoin(LoginSession loginSession,String channelName) {
		Channel channel = loginSession.channelJoin(channelName, new Signal.ChannelCallback() {
            @Override
            public void onChannelJoined(Signal.LoginSession session, Signal.LoginSession.Channel channel) {
                System.out.println("自己加入频道："+channelName);
            }

            @Override
            public void onChannelUserList(Signal.LoginSession session, Signal.LoginSession.Channel channel, List<String> users, List<Integer> uids) {
            	System.out.println("加入频道的用户列表："+users.toString());
            	System.out.println("加入频道的用户ID列表："+uids.toString());
            }


            @Override
            public void onMessageChannelReceive(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid, String msg) {
                System.out.println("收到频道内信息");
            }

            @Override
            public void onChannelUserJoined(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid) {
                System.out.println(account+":"+uid+"加入频道");
                //某个用户加入频道时获取服务器时间
                ChannelUserJoined.getStartChatTime(Integer.parseInt(account.substring(0, account.length()-1)));
            }

            @Override
            public void onChannelUserLeaved(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid) {
                System.out.println("离开频道的账户："+account+"用户ID："+uid);
                if(Transform.stringIsInt(account)) {
                	ChannelUserJoined.getEndChatTime(Integer.parseInt(account.substring(0, account.length()-1)));
                }
                
            }

            @Override
            public void onChannelLeaved(Signal.LoginSession session, Signal.LoginSession.Channel channel, int ecode) {
                System.out.println("自己离开频道"+ecode);
            }

        });
        System.out.println("获取频道："+channel);
        return channel;
	}
	
	
}
