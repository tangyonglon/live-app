package com.douliao.model.database;

import lombok.Data;

@Data
public class Live_record {
	private int id;
	private int live_user_id;
	private String live_date;
	private int today_online_time;
	private int today_chat_time;
	private double today_score;
	private int today_evaluate_leve;
	private String create_time;
	private String update_time;
	
}
