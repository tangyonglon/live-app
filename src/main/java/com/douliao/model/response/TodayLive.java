package com.douliao.model.response;

import java.util.List;

import com.douliao.model.TempObj;

import lombok.Data;

@Data
public class TodayLive {
	private int user_id;
	private String user_name;
	private String user_head;
	private int today_chat_time;
	private double user_reward_score;
	private int room_chat_time;
	private List<TempObj> list;
	
}
