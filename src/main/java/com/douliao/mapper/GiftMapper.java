package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.RewardParam;
import com.douliao.controller.server.model.VideoGiftsParam;
import com.douliao.model.database.Gifts;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Live_room_gifts;
import com.douliao.model.database.Video_gifts;

public interface GiftMapper {
	
	List<Gifts> findAllGifts();
	
	Gifts selGiftsInfo(int gifts_id);
	
	int updateGolds(RewardParam rewardParam);
	
	Live_room_gifts selGiftsById(RewardParam rewardParam);
	
	int updateGiftsAmount(RewardParam rewardParam);
	
	int insLiveRoomGifts(RewardParam rewardParam);
	
	int addScore(RewardParam rewardParam);
	
	int addRoomGold(RewardParam rewardParam);
	
	double selAccountGold(RewardParam rewardParam);
	
	Live_app_user_info seluserInfo(int userId);
	
	int updateUserGolds(Live_app_user_info live_app_user_info);
	
	Video_gifts selVideoGifts(VideoGiftsParam videoGiftsParam);
	
	int insVideoGifts(VideoGiftsParam videoGiftsParam);
	
	int updateVideoGiftsAmount(VideoGiftsParam videoGiftsParam);
	
	double selUserProfit(int userId);
	
	int updateUserScore(Live_app_user_info live_app_user_info2);
	
	int countScoreByVideo(Map<String, Object> map);
}
