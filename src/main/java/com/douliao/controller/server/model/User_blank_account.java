package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class User_blank_account {
	private int id;
	private int user_id;
	private String blank_account;
	private int blank_type;
	private int country_id;
	private String expire_time;
	private String csc;
	private String create_time;
	private String update_time;
	private int status;
	
}
