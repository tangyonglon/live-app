package com.douliao.model.database;

import lombok.Data;

@Data
public class Comments {
	private int id;
	private int comment_type;
	private int video_id;
	private int user_id;
	private String comment_content;
	private int status;
	
}
