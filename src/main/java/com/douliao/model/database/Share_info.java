package com.douliao.model.database;

import lombok.Data;

@Data
public class Share_info {
	private int id;
	private int user_id;
	private int share_way_id;
	private String today_time;
	private int count;
	private String update_time;
	private int version;
	private int status;
	
}
