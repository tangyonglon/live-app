package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class RewardParam {
	private int live_user_id;
	private int userId;
	private int gifts_id;
	private int live_room_id;
	private int amount;
	private double gifts_price;
	private double total_gold;
	private double user_score;

}
