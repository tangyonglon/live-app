package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class BeginLiveParam {
	private int live_user_id;
	private int live_room_id;
	private int live_room_type;
	private String start_time;
	private int status;
	private double live_price;
	private String im_channel;
	
}
