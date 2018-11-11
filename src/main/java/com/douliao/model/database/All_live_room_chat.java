package com.douliao.model.database;

import lombok.Data;

@Data
public class All_live_room_chat {
	private int id;
	private int live_room_id;
	private int live_room_type;
	private int user_id;
	private int chat_time;
	private double total_gold;
	private String start_time;
	private String end_time;
	private String update_time;
	private int status;
	private double live_price;
	
}
