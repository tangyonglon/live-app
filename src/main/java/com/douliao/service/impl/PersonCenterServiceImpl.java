package com.douliao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.mapper.PersonCenterMapper;
import com.douliao.model.RoomInfo;
import com.douliao.model.response.PersonCenterModel;
import com.douliao.service.PersonCenterService;

@Service
public class PersonCenterServiceImpl implements PersonCenterService{
	@Autowired
	private PersonCenterMapper personCenterMapper;

	@Override
	public PersonCenterModel selPersonInfo(int userId) {
		PersonCenterModel personCenterModel=personCenterMapper.selPersonInfo(userId);
		if(personCenterModel.getUser_authentication()==2) {
			RoomInfo RoomInfo=personCenterMapper.selLiveRoom(userId);
			if(RoomInfo!=null) {
				personCenterModel.setLive_room_id(RoomInfo.getLive_room_id());
				personCenterModel.setIm_channel(RoomInfo.getIm_channel());
			}
		}
		return personCenterModel;
	}
	
	
}
