package com.douliao.service;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.AnchorInfoParam;
import com.douliao.controller.server.model.BeginLiveParam;
import com.douliao.controller.server.model.CheckAudit;
import com.douliao.controller.server.model.CheckLiveRoomParam;
import com.douliao.controller.server.model.EndLiveParam;
import com.douliao.controller.server.model.EntryRoomParam;
import com.douliao.controller.server.model.GiveGiftParam;
import com.douliao.controller.server.model.LeaveRoomParam;
import com.douliao.controller.server.model.SwitchRoomParam;
import com.douliao.model.AnchorInfo;
import com.douliao.model.database.All_live_room;
import com.douliao.model.database.All_live_room_chat;
import com.douliao.model.database.Video_List;
import com.douliao.result.ResultView;

public interface NewLiveService {
	
	ResultView<List<Map<String, Object>>> showLiveList();
	
	ResultView<All_live_room> isAudit(CheckAudit checkAudit);
	
	int uploadLiveVideo(Video_List video_List);
	
	ResultView<BeginLiveParam> beginLive(BeginLiveParam beginLiveParam);
	
	ResultView<String> entryRoom(EntryRoomParam entryRoomParam);
	
	ResultView<All_live_room_chat> leaveRoom(LeaveRoomParam leaveRoomParam);
	
	ResultView<String> switchRoom(SwitchRoomParam switchRoomParam);
	
	ResultView<String> endLive(EndLiveParam endLiveParam);
	
	double giveGift(GiveGiftParam giveGiftParam);
	
	ResultView<String> updateRoomStatus(Map<String, Object> map);
	
	void updatechat(Map<String, Object> map);
	
	ResultView<AnchorInfo> anchorInfo(AnchorInfoParam anchorInfoParam);
	
}
