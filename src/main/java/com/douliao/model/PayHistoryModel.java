package com.douliao.model;

import lombok.Data;

@Data
public class PayHistoryModel {
	private int user_id;
	private String order_id;
	private String end_time;
	private int goods_id;
	private String title;
	
}
