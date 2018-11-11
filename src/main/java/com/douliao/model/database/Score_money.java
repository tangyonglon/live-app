package com.douliao.model.database;

import lombok.Data;

@Data
public class Score_money {
	private int id;
	private double score;
	private int country_id;
	private double money;
	private String create_time;
	private int status;
}
