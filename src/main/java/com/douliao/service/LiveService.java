package com.douliao.service;

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
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.response.PlayVideoInfo;
import com.douliao.model.response.TodayLive;
import com.douliao.result.ResultView;

public interface LiveService {
	
	
	int CreateRoom(int userId);
	
	ResultView<String> loginChannel(LoginChannelParam loginChannelParam);
	
	int closeChannel(CloseChannelParam closeChannelParam);
	
	ResultView<String> liveStatus(LiveParam liveParam);
	
	ResultView<String> updateStars(EvaluateParam evaluateParam);
	
	ResultView<String> selEverdayVideo(int userId);
	
	ResultView<List<VideoClips>> selVideoClips(VideoClipsParam videoClipsParam);
	
	ResultView<List<HotList>> selHotList(VideoClipsParam videoClipsParam);
	
	ResultView<List<NewList>> selNewList(VideoClipsParam videoClipsParam);
	
	ResultView<PlayVideoInfo> playVideo(int userId,int id);
	
	ResultView<String> updateLiveUserStatus(int userId);
	
	ResultView<TodayLive> getRoomProfit(ProfitParam profitParam);
	
	int insChatStartTime(int userId);
	
	int insChatEndTime(int userId);
	
}
