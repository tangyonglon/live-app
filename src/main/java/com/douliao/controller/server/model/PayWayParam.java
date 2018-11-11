package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class PayWayParam {
	private int country_id;
	private int channel_id;
	private String[] pay_way;
	
}
