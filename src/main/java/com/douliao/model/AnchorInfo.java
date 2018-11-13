package com.douliao.model;

import java.util.List;

import com.douliao.model.database.CharacterType;

import lombok.Data;

@Data
public class AnchorInfo {
	private int user_id;
	private String user_name;
	private String user_head;
	private int live_room_id;
	private String everyday_img;
	private String im_channel;
	private List<CharacterType> list;
	private int follow_status;
	
	
}
