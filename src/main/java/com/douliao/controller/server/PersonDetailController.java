package com.douliao.controller.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.FindCharacterTypeParam;
import com.douliao.controller.server.model.FindHobbyParam;
import com.douliao.controller.server.model.FollowListParam;
import com.douliao.controller.server.model.FollowParam;
import com.douliao.controller.server.model.InterestParam;
import com.douliao.controller.server.model.MyVideoParam;
import com.douliao.controller.server.model.PersonTypeParam;
import com.douliao.controller.server.model.UpdatePersonParam;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Hobby;
import com.douliao.model.response.FollowList;
import com.douliao.model.response.PersonInfo;
import com.douliao.model.response.VideoList;
import com.douliao.model.response.WatchHistoryModel;
import com.douliao.result.ResultView;
import com.douliao.service.PersonDetailService;
import com.douliao.util.Log4jUtil;

@RestController
public class PersonDetailController {
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private PersonDetailService personDetailService;
	
	/**
	 * 获取人性格特点数据字典
	 * @return
	 */
	@RequestMapping(value="/personDetail/findAllCharacterType",method=RequestMethod.POST)
	public ResultView<List<CharacterType>> findAllCharacterType() {
		ResultView<List<CharacterType>> resultView=personDetailService.findAllCharacterType();
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 兴趣爱好数据字典
	 * @return
	 */
	@RequestMapping(value="/personDetail/findAllUserHobby",method=RequestMethod.POST)
	public ResultView<List<Hobby>> findAllUserHobby() {
		log.info("日志输出");
		ResultView<List<Hobby>> resultView=personDetailService.findAllUserHobby();
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 根据用户ID查找用户的兴趣爱好
	 * @param findHobbyParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/findHobbyByUserId",method=RequestMethod.POST)
	public ResultView<List<Hobby>> findHobbyByUserId(FindHobbyParam findHobbyParam) {
		//根据token检测是否已经登入
		ResultView<List<Hobby>> resultView=personDetailService.findHobbyByUserId(findHobbyParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 根据用户ID查找用户喜欢的类型或自己属于的类型
	 * @param findCharacterTypeParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/findCharacterTypeByUserId",method=RequestMethod.POST)
	public ResultView<List<CharacterType>> findCharacterTypeByUserId(FindCharacterTypeParam findCharacterTypeParam) {
		//根据token检测是否已经登入
		ResultView<List<CharacterType>> resultView=personDetailService.findCharacterTypeByUserId(findCharacterTypeParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 个人详情
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/personDetail/personInfo",method=RequestMethod.POST)
	public ResultView<PersonInfo> personInfo(int userId) {
		ResultView<PersonInfo> resultView=personDetailService.selPersonInfo(userId);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 修改用户个人资料信息（用户名，性别，年龄，身高，体重，交友目的）
	 * @param updatePersonParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/updateUserInfo",method=RequestMethod.POST)
	public ResultView<String> updateUserInfo(UpdatePersonParam updatePersonParam) {
		//根据token检测是否已经登入
		ResultView<String> resultView=personDetailService.updateUserInfo(updatePersonParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 我的视频
	 * @param myVideoParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/findMyVideo",method=RequestMethod.POST)
	public ResultView<List<VideoList>> findMyVideo(MyVideoParam myVideoParam) {
		ResultView<List<VideoList>> resultView=personDetailService.findMyVideo(myVideoParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 查看历史观看视频
	 * @param myVideoParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/findWatchHistory",method=RequestMethod.POST)
	public ResultView<List<WatchHistoryModel>> findWatchHistory(MyVideoParam myVideoParam) {
		 ResultView<List<WatchHistoryModel>> resultView=personDetailService.findWatchHistory(myVideoParam);
		 Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 关注
	 * @param followParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/follow",method=RequestMethod.POST)
	public ResultView<String> follow(FollowParam followParam) {
		ResultView<String> resultView=personDetailService.addFollow(followParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 关注和被关注用户列表
	 * @param followListParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/followList",method=RequestMethod.POST)
	public ResultView<List<FollowList>> followList(FollowListParam followListParam) {
		ResultView<List<FollowList>> resultView=personDetailService.followList(followListParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	//保存我的兴趣爱好接口
	//保存我喜欢的性格特点
	//保存我的性格特点
	/**
	 * 保存我的兴趣爱好
	 * @param interestParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/saveInterest",method=RequestMethod.POST)
	public ResultView<String> saveInterest(InterestParam interestParam) {
		ResultView<String> resultView=personDetailService.saveInterest(interestParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 保存我喜欢的性格类型和我的性格类型
	 * @param PersonTypeParam
	 * @return
	 */
	@RequestMapping(value="/personDetail/saveMyType",method=RequestMethod.POST)
	public ResultView<String> saveMyType(PersonTypeParam PersonTypeParam) {
		ResultView<String> resultView=personDetailService.saveMyType(PersonTypeParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
