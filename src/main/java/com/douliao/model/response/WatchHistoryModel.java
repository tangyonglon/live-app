package com.douliao.model.response;

import lombok.Data;

@Data
public class WatchHistoryModel {
	private int user_id;
	private int video_id;
	private String video_description;
	private int support;
	private int watch_number;
	private String img_url;
	private String user_name;
	private String user_head;
	
}
