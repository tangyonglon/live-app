package com.douliao.model.database;

import lombok.Data;

@Data
public class Goods {
	private int id;
	private String title;
	private String description;
	private String goods_url;
	private int country_id;
	private double goods_price;
	private String create_time;
	private String update_time;
	private int sort;
	private int status;
	private String total_price;
}
