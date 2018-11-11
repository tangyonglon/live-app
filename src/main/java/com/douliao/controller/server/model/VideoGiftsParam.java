package com.douliao.controller.server.model;

import lombok.Data;

@Data
public class VideoGiftsParam {
	private int userId;
	private int videoUserId;
	private int videoId;
	private int giftsId;
	private int amount;
	private double gifts_price;
	private double total_gold;
	private double user_score;
	
}
