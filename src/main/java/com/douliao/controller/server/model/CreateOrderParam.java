package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class CreateOrderParam {
	private int userId;
	private String order_id;
	private int goods_id;
	private int pay_type;
	private String create_time;
	private int status;
	private double goods_price;
	private String currency_code;
	private int channel_id;
	
}
