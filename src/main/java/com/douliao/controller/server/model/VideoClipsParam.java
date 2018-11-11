package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class VideoClipsParam {
	private int type;
	private int page;
	private int number;
	private int start;
	private int video_tag;
	private int country_id;
	private int channel_id;
	private String[] video_level;
	
}
