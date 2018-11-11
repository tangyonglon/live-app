package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class CheckLiveRoomParam {
	private int live_room_id;
	private int live_user_id;
	private String end_time;
	private int status;
	
}
