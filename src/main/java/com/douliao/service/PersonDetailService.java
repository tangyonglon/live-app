package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.FindCharacterTypeParam;
import com.douliao.controller.server.model.FindHobbyParam;
import com.douliao.controller.server.model.FollowListParam;
import com.douliao.controller.server.model.FollowParam;
import com.douliao.controller.server.model.InterestParam;
import com.douliao.controller.server.model.MyVideoParam;
import com.douliao.controller.server.model.PersonTypeParam;
import com.douliao.controller.server.model.UpdatePersonParam;
import com.douliao.model.FindCharacterTypeByUserId;
import com.douliao.model.FindHobbyByUserId;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Hobby;
import com.douliao.model.response.FollowList;
import com.douliao.model.response.PersonInfo;
import com.douliao.model.response.VideoList;
import com.douliao.model.response.WatchHistoryModel;
import com.douliao.result.ResultView;

public interface PersonDetailService {
	
	ResultView<List<CharacterType>> findAllCharacterType();
	
	ResultView<List<Hobby>> findAllUserHobby();
	
	ResultView<List<Hobby>> findHobbyByUserId(FindHobbyParam findHobbyParam);
	
	ResultView<List<CharacterType>> findCharacterTypeByUserId(FindCharacterTypeParam findCharacterTypeParam);
	
	ResultView<String> updateUserInfo(UpdatePersonParam updatePersonParam);
	
	ResultView<List<VideoList>> findMyVideo(MyVideoParam myVideoParam);
	
	ResultView<List<WatchHistoryModel>> findWatchHistory(MyVideoParam myVideoParam);
	
	ResultView<String> addFollow(FollowParam followParam);
	
	ResultView<PersonInfo> selPersonInfo(int userId);
	
	ResultView<String> saveInterest(InterestParam interestParam);
	
	ResultView<String> saveMyType(PersonTypeParam personTypeParam);
	
	ResultView<List<FollowList>> followList(FollowListParam followListParam);
}
