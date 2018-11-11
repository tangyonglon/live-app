package com.douliao.model.database;

import lombok.Data;

@Data
public class Follow {
	private int id;
	private int user_id;
	private int fans_user_id;
	private String create_time;
	private int status;
	
}