package com.douliao.model.database;

import lombok.Data;

@Data
public class All_live_room {
	private int id;
	private int live_room_id;
	private int user_id;
	private int live_price;
	private String create_time;
	private String update_time;
	private double live_profit;
	private String live_url;
	private String everyday_img;
	private String video_name;
	private int status;
	
}
