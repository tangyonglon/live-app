package com.douliao.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.FindCharacterTypeParam;
import com.douliao.controller.server.model.FindHobbyParam;
import com.douliao.controller.server.model.FollowListParam;
import com.douliao.controller.server.model.FollowParam;
import com.douliao.controller.server.model.InterestParam;
import com.douliao.controller.server.model.MyVideoParam;
import com.douliao.controller.server.model.PersonTypeParam;
import com.douliao.controller.server.model.UpdatePersonParam;
import com.douliao.mapper.PersonDetailMapper;
import com.douliao.model.FindCharacterTypeByUserId;
import com.douliao.model.FindHobbyByUserId;
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
import com.douliao.result.ResultView;
import com.douliao.service.PersonDetailService;
import com.douliao.util.TimeFormat;
import com.douliao.util.Transform;

@Service
public class PersonDetailServiceImpl implements PersonDetailService{
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private PersonDetailMapper personDetailMapper;

	@Override
	public ResultView<List<CharacterType>> findAllCharacterType() {
		ResultView<List<CharacterType>> resultView=new ResultView<List<CharacterType>>();
		List<CharacterType> list=personDetailMapper.findAllCharacterType();
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<List<Hobby>> findAllUserHobby() {
		log.info("service层日志输出");
		ResultView<List<Hobby>> resultView=new ResultView<List<Hobby>>();
		List<Hobby> list=personDetailMapper.findAllUserHobby();
		log.info(list.toString());
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		log.info(resultView.toString());
		log.info("service层日志输出结束");
		return resultView;
	}
	
	@Override
	public ResultView<List<Hobby>> findHobbyByUserId(FindHobbyParam findHobbyParam) {
		ResultView<List<Hobby>> resultView=new ResultView<List<Hobby>>();
		User_hobby_list user_hobby_list=personDetailMapper.selHobbyId(findHobbyParam.getUserId());
		if(user_hobby_list!=null) {
			int[] array=Transform.stringTransfromIntArray(user_hobby_list.getHobby_id());
			List<Hobby> list=personDetailMapper.selHobby(array);
			if(list.size()>0) {
				resultView.setCode(1000);
				resultView.setMessage("获取成功");
				resultView.setData(list);
			}else {
				resultView.setCode(1001);
				resultView.setMessage("暂无数据");
			}
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		
		return resultView;
	}

	@Override
	public ResultView<List<CharacterType>> findCharacterTypeByUserId(
			FindCharacterTypeParam findCharacterTypeParam) {
		ResultView<List<CharacterType>> resultView=new ResultView<List<CharacterType>>();
		User_character user_character=personDetailMapper.findUser_characterById(findCharacterTypeParam);
		if(user_character!=null) {
			int[] array=Transform.stringTransfromIntArray(user_character.getCharacter_id());
			List<CharacterType> list=personDetailMapper.selCharacterType(array);
			if(list.size()>0) {
				resultView.setCode(1000);
				resultView.setMessage("获取成功");
				resultView.setData(list);
			}else {
				resultView.setCode(1001);
				resultView.setMessage("暂无数据");
			}
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> updateUserInfo(UpdatePersonParam updatePersonParam) {
		ResultView<String> resultView=new ResultView<String>();
		int result=personDetailMapper.updateUserInfo(updatePersonParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("修改成功");
		}else {
			resultView.setCode(2019);
			resultView.setMessage("修改个人资料失败");
		}
		return resultView;
	}

	@Override
	public ResultView<List<VideoList>> findMyVideo(MyVideoParam myVideoParam) {
		ResultView<List<VideoList>> resultView=new ResultView<List<VideoList>>();
		int page=myVideoParam.getPage()>0?myVideoParam.getPage():1;
		int number=myVideoParam.getNumber()>0?myVideoParam.getNumber():10;
		int start=(page-1)*myVideoParam.getNumber();
		myVideoParam.setStart(start);
		myVideoParam.setNumber(number);
		
		List<VideoList> list=personDetailMapper.findMyVideo(myVideoParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<List<WatchHistoryModel>> findWatchHistory(MyVideoParam myVideoParam) {
		ResultView<List<WatchHistoryModel>> resultView=new ResultView<List<WatchHistoryModel>>();
		int page=myVideoParam.getPage()>0?myVideoParam.getPage():1;
		int number=myVideoParam.getNumber()>0?myVideoParam.getNumber():10;
		int start=(page-1)*myVideoParam.getNumber();
		myVideoParam.setStart(start);
		myVideoParam.setNumber(number);
		
		List<WatchHistoryModel> list=personDetailMapper.findWatchHistory(myVideoParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("获取成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<String> addFollow(FollowParam followParam) {
		ResultView<String> resultView=new ResultView<String>();
		Follow follow=personDetailMapper.isFollow(followParam);
		int result;
		if(follow!=null) {
			result=personDetailMapper.upFollowStatus(followParam);
		}else {
			followParam.setCreate_time(TimeFormat.getSimple());
			result=personDetailMapper.insFollow(followParam);
		}
		Live_app_user_info live_app_user_info=personDetailMapper.selUserInfo(followParam);
		if(followParam.getStatus()==1) {
			live_app_user_info.setFans_number(live_app_user_info.getFans_number()+1);
		}
		if(followParam.getStatus()==2) {
			live_app_user_info.setFans_number(live_app_user_info.getFans_number()-1);
		}
		//主播粉丝数量改变
		int resultset=personDetailMapper.updatefans(live_app_user_info);
		if(result>0 && resultset>0) {
			resultView.setCode(1000);
			resultView.setMessage("关注成功");
		}else {
			resultView.setCode(1005);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		return resultView;
	}

	@Override
	public ResultView<PersonInfo> selPersonInfo(int userId) {
		ResultView<PersonInfo> resultView=new ResultView<PersonInfo>();
		PersonInfo personInfo=personDetailMapper.selPersonInfo(userId);
		if(personInfo!=null) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(personInfo);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<String> saveInterest(InterestParam interestParam) {
		ResultView<String> resultView=new ResultView<String>();
		User_hobby_list user_hobby_list =personDetailMapper.selInterestByUserId(interestParam);
		int result=0;
		if(user_hobby_list!=null) {
			result=personDetailMapper.saveInterest(interestParam);
		}else {
			result=personDetailMapper.insInterest(interestParam);
		}
		
		//result不管何值都是保存成功
		resultView.setCode(1000);
		resultView.setMessage("成功");
		return resultView;
	}

	@Override
	public ResultView<String> saveMyType(PersonTypeParam personTypeParam) {
		ResultView<String> resultView=new ResultView<String>();
		User_character user_character=personDetailMapper.selTypeByUserId(personTypeParam);
		int result=0;
		if(user_character!=null) {
			result=personDetailMapper.saveMyType(personTypeParam);
		}else {
			result=personDetailMapper.insMyType(personTypeParam);
		}
		//result不管何值都是保存成功
		resultView.setCode(1000);
		resultView.setMessage("成功");
		return resultView;
	}

	@Override
	public ResultView<List<FollowList>> followList(FollowListParam followListParam) {
		ResultView<List<FollowList>> resultView=new ResultView<List<FollowList>>();
		int page=followListParam.getPage()>0?followListParam.getPage():1;
		int start=(page-1)*followListParam.getNumber();
		followListParam.setStart(start);
		followListParam.setStatus(1);
		List<FollowList> list=personDetailMapper.selFollowList(followListParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}
	
}
