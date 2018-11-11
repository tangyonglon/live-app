package com.douliao.model.database;

import lombok.Data;

@Data
public class VideoType {
	private int id;
	private String video_type;
	private int country_id;
	private int status;
	
}
