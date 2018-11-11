package com.douliao.model.database;

import lombok.Data;

@Data
public class Video_level_filter {
	private int id;
	private int country_id;
	private int channel_id;
	private String video_level;
	private String update_time;
	private String pay_way_id;
	private String login_way_id;
	private int status;
	
}
