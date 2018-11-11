package com.douliao.model.database;

import lombok.Data;

@Data
public class User_tv_info {
	private int id;
	private int user_id;
	private int live_level;
	private double live_price;
	private String create_time;
	private int heat;
	private double profit;
	private int status;
}
