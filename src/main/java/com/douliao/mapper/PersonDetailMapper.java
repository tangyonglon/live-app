package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.FindCharacterTypeParam;
import com.douliao.controller.server.model.FindHobbyParam;
import com.douliao.controller.server.model.FollowListParam;
import com.douliao.controller.server.model.FollowParam;
import com.douliao.controller.server.model.InterestParam;
import com.douliao.controller.server.model.MyVideoParam;
import com.douliao.controller.server.model.PersonTypeParam;
import com.douliao.controller.server.model.SearchUserInfoParam;
import com.douliao.controller.server.model.UpdatePersonParam;
import com.douliao.model.Anchor;
import com.douliao.model.FindCharacterTypeByUserId;
import com.douliao.model.database.All_live_room_info;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Follow;
import com.douliao.model.database.Hobby;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.User_character;
import com.douliao.model.database.User_hobby_list;
import com.douliao.model.response.FollowList;
import com.douliao.model.response.PersonInfo;
import com.douliao.model.response.VideoList;
import com.douliao.model.response.WatchHistoryModel;

public interface PersonDetailMapper {
	List<CharacterType> findAllCharacterType();
	
	List<Hobby> findAllUserHobby();
	
	User_hobby_list selHobbyId(int userId);
	
	List<Hobby> selHobby(int[] array);
	
	User_character findUser_characterById(FindCharacterTypeParam findCharacterTypeParam);
	
	List<CharacterType> selCharacterType(int[] array);
	
	Anchor selAnchor(SearchUserInfoParam searchUserInfoParam);
	
	All_live_room_info selIsLive(SearchUserInfoParam searchUserInfoParam);
	
	int updateUserInfo(UpdatePersonParam updatePersonParam);
	
	List<VideoList> findMyVideo(MyVideoParam myVideoParam);
	
	List<WatchHistoryModel> findWatchHistory(MyVideoParam myVideoParam);
	
	Follow isFollow(FollowParam followParam);
	
	int upFollowStatus(FollowParam followParam);
	
	int insFollow(FollowParam followParam);
	
	PersonInfo selPersonInfo(int userId);
	
	User_hobby_list selInterestByUserId(InterestParam interestParam);
	
	int saveInterest(InterestParam interestParam);
	
	int insInterest(InterestParam interestParam);
	
	int saveMyType(PersonTypeParam personTypeParam);
	
	User_character selTypeByUserId(PersonTypeParam personTypeParam);
	
	int insMyType(PersonTypeParam personTypeParam);
	
	Live_app_user_info selUserInfo(FollowParam followParam);
	
	int updatefans(Live_app_user_info live_app_user_info);
	
	List<FollowList> selFollowList(FollowListParam followListParam);
	
	Follow isFollowUser(SearchUserInfoParam searchUserInfoParam);
	
}
