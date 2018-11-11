package com.douliao.model.database;

import lombok.Data;

@Data
public class UserToken {
	private int id;
	private int user_id;
	private String token;
	private String login_time;
	private String update_time;
	private String mac;
	private int mac_type;
	private String phone_type;
	private int login_mode;
	private int status;
	
}
