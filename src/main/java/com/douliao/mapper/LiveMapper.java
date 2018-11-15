package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.CloseChannelParam;
import com.douliao.controller.server.model.EvaluateParam;
import com.douliao.controller.server.model.LiveParam;
import com.douliao.controller.server.model.LoginChannelParam;
import com.douliao.controller.server.model.ProfitParam;
import com.douliao.controller.server.model.VideoClipsParam;
import com.douliao.model.HotList;
import com.douliao.model.NewList;
import com.douliao.model.Room_info;
import com.douliao.model.TempObj;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Live_record;
import com.douliao.model.database.Live_room_info;
import com.douliao.model.database.Live_time;
import com.douliao.model.database.Video_List;
import com.douliao.model.database.Video_level_filter;
import com.douliao.model.response.TodayLive;

public interface LiveMapper {
	Room_info selLivePrice(int userId);
	
	int createRoom(Live_room_info live_room_info);
	
	int updateLiveStatus(int userId);
	
	int updateRoomInfo(LoginChannelParam loginChannelParam);
	
	Live_room_info selById(int channel);
	
	int updateChatEndTime(Live_room_info live_room_info);
	
	int updateScore(CloseChannelParam closeChannelParam);
	
	int updateGold(CloseChannelParam closeChannelParam);
	
	int closeRoom(CloseChannelParam closeChannelParam);
	
	int liveStatus(LiveParam liveParam);
	
	int updateStars(EvaluateParam evaluateParam);
	
	Everyday_live_list selEverdayVideo(int userId);
	
	List<VideoClips> selVideoClips(VideoClipsParam videoClipsParam);
	
	List<VideoClips> selVideoClips2(VideoClipsParam videoClipsParam);
	
	List<HotList> selHotList(VideoClipsParam videoClipsParam);
	
	List<NewList> selNewList(VideoClipsParam videoClipsParam);
	
	Video_List selVideoInfo(int id);
	
	int updateWatchNumber(Video_List video_List);
	
	double selProfit(int user_id);
	
	Live_app_user_info selUserAllInfo(int user_id);
	
	int updateUserScoreByVideo(Live_app_user_info live_app_user_info);
	
	int countVideoScore(Video_List video_List);
	
	int updateUserPackage(Map<String, Object> map);
	
	int updateIdentity(LiveParam liveParam);
	
	int insLiveTime(LiveParam liveParam);
	
	Live_time selNewLiveTime(LiveParam liveParam);
	
	int upLiveTime(LiveParam liveParam);
	
	int updateLiveRecord(CloseChannelParam closeChannelParam);
	
	int insLiveRecord(LiveParam liveParam);
	
	Live_record selLiveRecord(LiveParam liveParam);
	
	int updateLiveUserStatus(int userId);
	
	TodayLive selUserInfo(ProfitParam profitParam);
	
	Live_room_info selLiveRoomById(ProfitParam profitParam);
	
	List<TempObj> selRoomAllGifts(ProfitParam profitParam);
	
	int insChatStartTime(Map<String, Object> map);
	
	int insChatEndTime(Map<String, Object> map);
	
	int countTodayLiveTime(LiveParam liveParam);
	
	int updateUserScore(ProfitParam profitParam);
	
	Live_room_info selChatEndTime(Map<String, Object> map);
	
	Live_record selTodayRecord(LiveParam liveParam);
	
	int insWatchHistory(Map<String, Object> map);
	
	int countVideoComment(int id);
	
	int countScore(TodayLive todayLive);
	
	Video_level_filter selVideoFilter(VideoClipsParam videoClipsParam);
	
}
