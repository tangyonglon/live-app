package com.douliao.model.database;

import lombok.Data;

@Data
public class Live_time {
	private int id;
	private int live_user_id;
	private String start_time;
	private String end_time;
	private int status;
	
}
