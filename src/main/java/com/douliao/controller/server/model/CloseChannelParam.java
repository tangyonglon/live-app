package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class CloseChannelParam {
	
	private int channel;
	private String endTime;
	private double gold;
	private double score;
	private int live_user_id;
	private int user_id;
	private String Date;
	private int chat_time;
	
}
