package com.douliao.model.database;

import lombok.Data;

@Data
public class Anchor_income {
	private int id;
	private int live_room_id;
	private int anchor_id;
	private double anchor_total_score;
	private double everyday_scroe;
	private String income_date;
	private String update_time;
	
}
