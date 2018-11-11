package com.douliao.model;

import lombok.Data;

@Data
public class HotList {
	private int user_id;
	private String live_url;
	private String everyday_img;
	private int heat;
	private double live_price;
	private int status;
	private String user_head;
	private String user_name;
	
}
