package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.FindCharacterTypeParam;
import com.douliao.controller.server.model.SearchUserInfoParam;
import com.douliao.mapper.PersonDetailMapper;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Follow;
import com.douliao.model.database.Hobby;
import com.douliao.model.database.User_character;
import com.douliao.model.database.User_hobby_list;
import com.douliao.model.response.PersonInfo;
import com.douliao.result.ResultView;
import com.douliao.service.SearchService;
import com.douliao.util.Transform;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private PersonDetailMapper personDetailMapper;

	@Override
	public ResultView<PersonInfo> searchUserInfo(SearchUserInfoParam searchUserInfoParam) {
		ResultView<PersonInfo> resultView =new ResultView<PersonInfo>();
		PersonInfo personInfo =new PersonInfo();
		personInfo=personDetailMapper.selPersonInfo(searchUserInfoParam.getUserId());
		if(personInfo!=null) {
			//查询是否关注
			Follow follow=personDetailMapper.isFollowUser(searchUserInfoParam);
			if(follow!=null && follow.getStatus()==1) {
				personInfo.setFollowStatus(1);
			}else {
				personInfo.setFollowStatus(2);
			}
			//获取用户的兴趣爱好
			User_hobby_list user_hobby_list=personDetailMapper.selHobbyId(searchUserInfoParam.getUserId());
			if(user_hobby_list!=null) {
				int[] array=Transform.stringTransfromIntArray(user_hobby_list.getHobby_id());
				List<Hobby> hobbyList=personDetailMapper.selHobby(array);
				personInfo.setHobbyList(hobbyList);
			}
			FindCharacterTypeParam findCharacterTypeParam=new FindCharacterTypeParam();
			findCharacterTypeParam.setUserId(searchUserInfoParam.getUserId());
			for(int i=1;i<3;i++) {
				findCharacterTypeParam.setBelongId(i);
				User_character user_character=personDetailMapper.findUser_characterById(findCharacterTypeParam);
				if(user_character!=null) {
					int[] array1=Transform.stringTransfromIntArray(user_character.getCharacter_id());
					List<CharacterType> templist=personDetailMapper.selCharacterType(array1);
					if(i==1) {
						personInfo.setMyTypeList(templist);
					}
					if(i==2) {
						personInfo.setOtherTypeList(templist);
					}
				}
			}
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(personInfo);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		
		return resultView;
	}
	
	
}
