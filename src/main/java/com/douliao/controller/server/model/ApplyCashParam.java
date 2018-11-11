package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class ApplyCashParam {
	private int user_id;
	private String blank_account;
	private int blank_type;
	private String total_money;
	private String create_time;
	private int status;
	
}
