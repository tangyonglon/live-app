package com.douliao.model;

import lombok.Data;

@Data
public class VideoClips {
	private int id;
	private int vuser_id;
	private String video_description;
	private int support;
	private int watch_number;
	private double price;
	private int type;
	private String img_url;
	private String user_head;
	private String user_name;
	
}
