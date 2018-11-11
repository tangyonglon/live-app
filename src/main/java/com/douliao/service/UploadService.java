package com.douliao.service;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.AuditParam;
import com.douliao.controller.server.model.DeleteParam;
import com.douliao.controller.server.model.VideoByUserIdParam;
import com.douliao.model.Result;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.User_tv_info;
import com.douliao.model.database.VideoType;
import com.douliao.model.database.Video_List;
import com.douliao.result.ResultView;

public interface UploadService {
	
	int insVideo(Video_List video_List);
	
	int updateHead(Map<String, Object> map);
	
	Everyday_live_list isHasVideo(Video_List video_List);
	
	int insEverydayVideo(Video_List video_List);
	
	int updateEverydayVideo(Video_List video_List);
	
	Everyday_live_list isAudit(int userId);
	
	Everyday_live_list selOldData(AuditParam auditParam);
	
	int updateData(AuditParam auditParam);
	
	int insUserTVInfo(Video_List video_List);
	
	ResultView<List<VideoType>> selVideoType(int country_id);
	
	Everyday_live_list playAuditVideo(int userId);
	
	User_tv_info hasTVInfo(Video_List video_List);
	
	int updateLiveAuth(Video_List video_List);
	
	ResultView<String> deleteVideo(DeleteParam deleteParam);
	
	ResultView<List<VideoClips>> selVideoByUserId(VideoByUserIdParam videoByUserIdParam);
	
	double selProfit(int userId);
}
