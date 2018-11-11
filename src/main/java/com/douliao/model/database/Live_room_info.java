package com.douliao.model.database;

import lombok.Data;

@Data
public class Live_room_info {
	private int id;
	private int live_user_id;
	private int user_id;
	private double room_price;
	private String create_time;
	private String update_time;
	private int user_evaluate_level;
	private double user_reward_score;
	private String chat_start_time;
	private String chat_end_time;
	private double live_profit;
	private int status;
	
}
