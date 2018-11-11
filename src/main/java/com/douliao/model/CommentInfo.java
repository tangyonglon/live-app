package com.douliao.model;

import lombok.Data;

@Data
public class CommentInfo {
	private int id;
	private int video_id;
	private int user_id;
	private String comment_content;
	private String create_time;
	private String user_name;
	private String user_head;
	
}
