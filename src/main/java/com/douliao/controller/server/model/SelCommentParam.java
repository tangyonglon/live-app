package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class SelCommentParam {
	private int videoId;
	private int page;
	private int number;
	private int start;
	
}
