package com.douliao.model.response;

import java.util.List;

import com.douliao.model.Anchor;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Hobby;

import lombok.Data;

@Data
public class PersonInfo {
	private int user_id;
	private String user_head;
	private String user_name;
	private int user_sex;
	private int user_age;
	private double user_weight;
	private double user_height;
	private String make_friends_goal;
	private int live_level;
	private double live_price;
	private int followStatus;
	private List<Hobby> hobbyList;
	private List<CharacterType> myTypeList;
	private List<CharacterType> otherTypeList;
	private Anchor anchor;
	
}
