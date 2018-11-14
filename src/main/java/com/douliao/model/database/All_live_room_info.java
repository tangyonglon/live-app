package com.douliao.model.database;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class All_live_room_info {
	private int id;
	private int live_room_id;
	private int live_room_type;
	private double live_price;
	private int live_user_id;
	private int user_id;
	private String start_time;
	private String end_time;
	private double score;
	private int status;
	private List<Map<String, Object>> list;
	private int time;
	
}
