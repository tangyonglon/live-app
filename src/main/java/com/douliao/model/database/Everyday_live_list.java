package com.douliao.model.database;

import lombok.Data;

@Data
public class Everyday_live_list {
	private int id;
	private int user_id;
	private String live_url;
	private String save_url;
	private String create_time;
	private String everyday_img;
	private String video_type;
	private String video_size;
	private String video_name;
	private String update_time;
	private int status;
	
}
