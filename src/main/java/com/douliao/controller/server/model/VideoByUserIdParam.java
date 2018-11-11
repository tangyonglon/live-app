package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class VideoByUserIdParam {
	private int userId;
	private int page;
	private int number;
	private int start;
	private int login_user_id;
	private int channel_id;
	private String[] video_level;
	
}
