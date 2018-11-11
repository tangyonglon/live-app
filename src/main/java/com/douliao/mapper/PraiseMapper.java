package com.douliao.mapper;

import com.douliao.controller.server.model.PraiseParam;
import com.douliao.model.database.Video_List;

public interface PraiseMapper {
	Video_List selVideoList(PraiseParam praiseParam);
	
	int countPrise(Video_List video_List);
	
	int insPraise(PraiseParam praiseParam);
}
