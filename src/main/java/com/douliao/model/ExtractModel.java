package com.douliao.model;

import java.util.List;

import com.douliao.model.database.Score_money;

import lombok.Data;

@Data
public class ExtractModel {
	private int user_id;
	private double user_score;
	private double total_score;
	private double live_score;
	private double video_score;
	private List<Score_money> list;

}
