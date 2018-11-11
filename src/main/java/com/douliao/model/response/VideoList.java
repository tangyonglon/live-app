package com.douliao.model.response;

import lombok.Data;

@Data
public class VideoList {
	private int id;
	private int user_id;
	private String video_title;
	private String video_description;
	private String video_url;
	private int support;
	private int watch_number;
	private String create_time;
	private String video_type;
	private String video_new_name;
	private double price;
	private int type;
	private int video_level;
	private String img_url;
	private int status;
	
}
