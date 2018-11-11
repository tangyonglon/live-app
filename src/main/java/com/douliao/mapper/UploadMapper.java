package com.douliao.mapper;

import java.util.List;
import java.util.Map;

import com.douliao.controller.server.model.AuditParam;
import com.douliao.controller.server.model.DeleteParam;
import com.douliao.controller.server.model.VideoByUserIdParam;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.User_tv_info;
import com.douliao.model.database.VideoType;
import com.douliao.model.database.Video_List;
import com.douliao.model.database.Video_level_filter;

public interface UploadMapper {
	
	int insVideo(Video_List video_List);
	
	int updateHead(Map<String, Object> map);
	
	int insEverydayVideo(Video_List video_List);
	
	Everyday_live_list isHasVideo(Video_List video_List);
	
	int updateEverydayVideo(Video_List video_List);
	
	Everyday_live_list isAudit(int userId);
	
	Everyday_live_list selOldData(AuditParam auditParam);
	
	int updateData(AuditParam auditParam);
	
	int insUserTVInfo(Video_List video_List);
	
	List<VideoType> selVideoType(int country_id);
	
	Everyday_live_list playAuditVideo(int userId);
	
	User_tv_info hasTVInfo(Video_List video_List);
	
	int updateLiveAuth(Video_List video_List);
	
	int updateVideo(DeleteParam deleteParam);
	
	List<VideoClips> selVideoByUserId(VideoByUserIdParam videoByUserIdParam);
	
	double selProfit(int userId);
	
	Video_level_filter selVideoFilter(VideoByUserIdParam videoByUserIdParam);
}
