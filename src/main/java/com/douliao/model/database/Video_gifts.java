package com.douliao.model.database;

import lombok.Data;

@Data
public class Video_gifts {
	private int id;
	private int video_id;
	private int user_id;
	private int gifts_id;
	private int amount;
	private double gifts_price;
	private double total_gold;
	private String update_time;
	
}	
