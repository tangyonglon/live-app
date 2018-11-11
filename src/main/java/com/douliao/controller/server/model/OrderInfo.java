package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class OrderInfo {
	private int id;
	private int user_id;
	private String order_id;
	private int goods_id;
	private int pay_type;
	private String create_time;
	private String pay_total;
	private int channel_id;
	private int status;
	
}
