package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class CommentParam {
	private int userId;
	private int videoId;
	private String content;
	private String create_time;
	
}
