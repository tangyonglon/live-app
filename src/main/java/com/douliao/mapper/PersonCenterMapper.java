package com.douliao.mapper;

import com.douliao.model.RoomInfo;
import com.douliao.model.response.PersonCenterModel;

public interface PersonCenterMapper {
	
	PersonCenterModel selPersonInfo(int userId);
	
	RoomInfo selLiveRoom(int userId);
	
}
