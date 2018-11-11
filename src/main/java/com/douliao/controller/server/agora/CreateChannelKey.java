package com.douliao.controller.server.agora;

import static org.junit.Assert.assertEquals;

public class CreateChannelKey {
	
	
	public static String CreateKey(String appID,String appCertificate,String channel,int ts,int r) {
		String channelKey=null;
		try {
			channelKey=DynamicKey.generate(appID, appCertificate, channel, ts, r);
			System.out.println(channelKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channelKey;
	}
	public static void main(String[] args) {
		String appID   = "970ca35de60c44645bbae8a215061b33";
        String appCertificate      = "5cfd2fd1755d40ecb72977518be15d3b";
        String channel  = "7d72365eb983485397e3e3f9d460bdda";
        int ts = 1446455472;
        int r = 58964981;
        int uid = 999;
        int expiredTs = 1446455471;
        try {
			String result = DynamicKey.generate(appID, appCertificate, channel, ts, r);
			System.out.println(result);
			String expected = "870588aad271ff47094eb622617e89d6b5b5a615970ca35de60c44645bbae8a215061b3314464554720383bbf5";
			assertEquals(expected, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
