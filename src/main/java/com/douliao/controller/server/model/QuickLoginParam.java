package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class QuickLoginParam {
	private int user_id;
	private int country_id;
	private int registerWay;
	private int id;
	private String loginTime;
	private String mac;
	private int mac_type;
	private String token;
	private int loginMode;
	private String login_ip;
	private int channel_id;
	private int status;
	private String create_time;
	
}
