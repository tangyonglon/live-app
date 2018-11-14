package com.douliao.model;

import lombok.Data;

@Data
public class Anchor {
	private int id;
	private int live_room_id;
	private int user_id;
	private String create_time;
	private String update_time;
	private double live_profit;
	private String live_url;
	private String everyday_img;
	private String video_name;
	private double anchor_total_score;
	private String im_channel;
	private int status;
	private int live_status;
	
}
