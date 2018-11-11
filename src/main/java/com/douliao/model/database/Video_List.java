package com.douliao.model.database;

import lombok.Data;

@Data
public class Video_List {
	private int id;
	private int user_id;
	private String video_title;
	private String video_description;
	private String video_url;
	private String save_url;
	private int support;
	private int watch_number;
	private String create_time;
	private int status;
	private String video_type;
	private String video_size;
	private String video_old_name;
	private String video_new_name;
	private String img_url;
	private double price;
	private int type;
	private int video_level;
	private int count;
	private int video_tag;
	private int version;
	private double profit;
	private String praise_channel;
	private String comment_channel;
	private String gift_channel;
	private double profitScore;
	private int country_id;
	private int live_room_id;
	
}
