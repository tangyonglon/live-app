package com.douliao.model.database;

import lombok.Data;

@Data
public class Country {
	private int id;
	private String country_name;
	private String country_code;
	private String phone_code;
	private int city_id;
	private String create_time;
	private double live_profit;
	private int language_id;
	private String language_code;
	private String currency_type;
	private int status;
	
}
