package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class PraiseParam {
	private int userId;
	private int videoId;
	private String user_id_video_id;
	private int status;
}
