package com.douliao.model.database;

import java.util.Date;

import lombok.Data;

@Data
public class Gifts {
	private int id;
	private String gifts_name;
	private String gifts_url;
	private double gifts_price;
	private double gifts_score;
	private Date create_time;
	private Date update_time;
	private int status;
	
	
}
