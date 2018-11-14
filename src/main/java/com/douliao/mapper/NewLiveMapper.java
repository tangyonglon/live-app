package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.AnchorBalanceParam;
import com.douliao.controller.server.model.AnchorInfoParam;
import com.douliao.controller.server.model.BeginLiveParam;
import com.douliao.controller.server.model.CheckAudit;
import com.douliao.controller.server.model.CheckLiveRoomParam;
import com.douliao.controller.server.model.EndLiveParam;
import com.douliao.controller.server.model.EntryRoomParam;
import com.douliao.controller.server.model.GiveGiftParam;
import com.douliao.controller.server.model.LeaveRoomParam;
import com.douliao.controller.server.model.ShowLiveParam;
import com.douliao.controller.server.model.SwitchRoomParam;
import com.douliao.model.AnchorInfo;
import com.douliao.model.database.All_live_room;
import com.douliao.model.database.All_live_room_chat;
import com.douliao.model.database.All_live_room_info;
import com.douliao.model.database.Anchor_income;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Gifts;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.User_character;
import com.douliao.model.database.Video_List;

public interface NewLiveMapper {
	
	All_live_room isAudit(CheckAudit checkAudit);
	
	All_live_room selLiveVideo(Video_List video_List);
	
	int updateLiveVideo(Video_List video_List);
	
	int insLiveVideo(Video_List video_List);
	
	Live_app_user_info isAnchor(BeginLiveParam beginLiveParam);
	
	double selLivePrice(BeginLiveParam beginLiveParam);
	
	List<Map<String, Object>> isBeginLive(BeginLiveParam beginLiveParam);
	
	int beginLive(BeginLiveParam beginLiveParam);
	
	All_live_room_chat selRoomRecord(EntryRoomParam entryRoomParam);
	
	int updateRoomRecord(EntryRoomParam entryRoomParam);
	
	int insRoomRecord(EntryRoomParam entryRoomParam);
	
	All_live_room_chat selleaveRoom(LeaveRoomParam leaveRoomParam);
	
	int updateleaveRoom(All_live_room_chat all_live_room_chat);
	
	int switchRoom(SwitchRoomParam switchRoomParam);
	
	int updateRoomChat(SwitchRoomParam switchRoomParam);
	
	int endLive(EndLiveParam endLiveParam);
	
	Gifts selGiftsInfo(GiveGiftParam giveGiftParam);
	
	Live_app_user_info selUserPackage(GiveGiftParam giveGiftParam);
	
	int reduceGold(GiveGiftParam giveGiftParam);
	
	int userConsumRecord(GiveGiftParam giveGiftParam);
	
	Anchor_income selanchorIncome(GiveGiftParam giveGiftParam);
	
	All_live_room selLiveProfit(GiveGiftParam giveGiftParam);
	
	int insUserIncome(GiveGiftParam giveGiftParam);
	
	int insRoomGifts(GiveGiftParam giveGiftParam);
	
	int updateTotalScore(GiveGiftParam giveGiftParam);
	
	int updateRoomStatus(Map<String, Object> map);
	
	int updateRoomChatStatus(Map<String, Object> map);
	
	List<Map<String, Object>> showLiveList(ShowLiveParam showLiveParam);
	
	AnchorInfo anchorInfo(AnchorInfoParam anchorInfoParam);
	
	User_character selCharacterTypeByUserId(AnchorInfoParam anchorInfoParam);
	
	List<CharacterType> selCharacterType(int[] array);
	
	List<Map<String, Object>> selFollow(AnchorInfoParam anchorInfoParam);
	
	All_live_room_info selLiveInfo(AnchorBalanceParam anchorBalanceParam);
	
	List<Map<String, Object>> selReceiveGift(All_live_room_info all_live_room_info);
	
}
