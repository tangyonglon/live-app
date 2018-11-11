package com.douliao.model.database;

import lombok.Data;

@Data
public class LiveAppUser {
	private int id;
	private String phone_code;
	private String user_phone;
	private String user_account;
	private String user_password;
	private String user_create_time;
	private String user_update_time;
	private int country_id;
	private String frozen_cause;
	private int status;
	private String praise_channel;
	private String comment_channel;
	private String gift_channel;
	
}
