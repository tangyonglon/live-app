package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class FollowListParam {
	private int userId;
	/**
	 * type 1.我关注的人  2.关注我的人
	 */
	private int type;
	private int status;
	private int page;
	private int number;
	private int start;
}
