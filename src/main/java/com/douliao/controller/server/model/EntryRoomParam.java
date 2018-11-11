package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class EntryRoomParam {
	private int user_id;
	private int live_room_id;
	private int live_room_type;
	private double chat_price;
	private String start_time;
	private int status;
	
}
