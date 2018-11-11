package com.douliao.model.database;

import lombok.Data;

@Data
public class User_apply_cash {
	private int id;
	private int user_id;
	private String blank_account;
	private int blank_type;
	private String total_money;
	private String create_time;
	private String end_time;
	private int status;
	
}
