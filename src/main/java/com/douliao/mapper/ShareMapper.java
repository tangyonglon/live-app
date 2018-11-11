package com.douliao.mapper;

import com.douliao.controller.server.model.ShareInfoParam;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Share_info;

public interface ShareMapper {
	
	Share_info selByUserId(ShareInfoParam shareInfoParam);
	
	Live_app_user_info selUserInfo(ShareInfoParam shareInfoParam);
	
	int updateUserInfo(Live_app_user_info live_app_user_info);
	
	int updateShareInfo(Share_info share_info);
	
	int insShareInfo(Share_info share_info);
	
}
