package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class GiveGiftParam {
	private int user_id;
	private int gifts_id;
	private int live_room_id;
	private int amount;
	private double gifts_price;
	private double total_gold;
	private String create_time;
	private int consum_type;
	private String income_date;
	private int live_user_id;
	private double score;
	
}
