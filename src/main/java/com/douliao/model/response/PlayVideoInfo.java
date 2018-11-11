package com.douliao.model.response;

import lombok.Data;

@Data
public class PlayVideoInfo {
	private String video_url;
	private int followStatus;
	private int countComment;
	private String video_description;
	private String praise_channel;
	private String comment_channel;
	private String gift_channel;
	
}
