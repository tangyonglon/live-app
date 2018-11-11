package com.douliao.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.CloseChannelParam;
import com.douliao.controller.server.model.EvaluateParam;
import com.douliao.controller.server.model.LiveParam;
import com.douliao.controller.server.model.LoginChannelParam;
import com.douliao.controller.server.model.ProfitParam;
import com.douliao.controller.server.model.SearchUserInfoParam;
import com.douliao.controller.server.model.VideoClipsParam;
import com.douliao.mapper.LiveMapper;
import com.douliao.mapper.PersonDetailMapper;
import com.douliao.model.HotList;
import com.douliao.model.NewList;
import com.douliao.model.Room_info;
import com.douliao.model.TempObj;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.Follow;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Live_record;
import com.douliao.model.database.Live_room_info;
import com.douliao.model.database.Live_time;
import com.douliao.model.database.Video_List;
import com.douliao.model.database.Video_level_filter;
import com.douliao.model.response.PlayVideoInfo;
import com.douliao.model.response.TodayLive;
import com.douliao.result.ResultView;
import com.douliao.service.LiveService;
import com.douliao.util.Live_Deductions;
import com.douliao.util.Log4jUtil;
import com.douliao.util.ReadResourceConfigUtils;
import com.douliao.util.TimeFormat;

@Service
public class LiveServiceImpl implements LiveService {
	@Autowired
	private LiveMapper liveMapper;
	@Autowired
	private PersonDetailMapper personDetailMapper;

	@Override
	public int CreateRoom(int userId) {
		Room_info room_info=liveMapper.selLivePrice(userId);
		if(room_info.getLive_price()==0) {
			return 0;
		}
		Live_room_info live_room_info=new Live_room_info();
		live_room_info.setLive_user_id(userId);
		live_room_info.setRoom_price(room_info.getLive_price());
		live_room_info.setCreate_time(TimeFormat.getSimple());
		live_room_info.setLive_profit(room_info.getLive_profit());
		live_room_info.setStatus(1);
		int result=liveMapper.createRoom(live_room_info);
		//切换主播状态
		liveMapper.updateLiveStatus(userId);
		//查询是否有今天开播记录
		LiveParam liveParam=new LiveParam();
		liveParam.setUserId(userId);
		liveParam.setDate(TimeFormat.getDate());
		liveParam.setStart_time(TimeFormat.getSimple());
		Live_record Live_record=liveMapper.selTodayRecord(liveParam);
		if(Live_record==null) {
			liveMapper.insLiveRecord(liveParam);
		}
		
		if(result>0 && live_room_info.getId()!=0) {
			return live_room_info.getId();
		}else {
			return 0;
		}
		
	}

	@Override
	public ResultView<String> loginChannel(LoginChannelParam loginChannelParam) {
		ResultView<String> resultView=new ResultView<String>();
		loginChannelParam.setStartTime(TimeFormat.getSimple());
		int result=liveMapper.updateRoomInfo(loginChannelParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("加入成功");
		}else {
			resultView.setCode(2015);
			resultView.setMessage("加入失败，请检查该频道是否还存在");
		}
		return resultView;
	}
	
	
	@Transactional
	@Override
	public int closeChannel(CloseChannelParam closeChannelParam) {
		try {
			Live_room_info live_room_info=liveMapper.selById(closeChannelParam.getChannel());
			if(live_room_info!=null){
				//关闭直播间
				liveMapper.closeRoom(closeChannelParam);
				//扣钱计费  用户金币减少  主播积分增加
				try {
					System.out.println("结束时间："+live_room_info.getChat_end_time());
					if(!StringUtils.isNoneBlank(live_room_info.getChat_end_time())) {
						live_room_info.setChat_end_time(TimeFormat.getSimple());
						liveMapper.updateChatEndTime(live_room_info);
					}
					//计算聊天时长
					closeChannelParam.setChat_time(Live_Deductions.getChatTime(live_room_info.getChat_start_time(), live_room_info.getChat_end_time()));
					closeChannelParam.setGold(Live_Deductions.Deductions(live_room_info.getChat_start_time(), live_room_info.getChat_end_time(), live_room_info.getRoom_price()));
					closeChannelParam.setScore(closeChannelParam.getGold()*live_room_info.getLive_profit());
					closeChannelParam.setLive_user_id(live_room_info.getLive_user_id());
					closeChannelParam.setUser_id(live_room_info.getUser_id());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//主播积分增加
				liveMapper.updateScore(closeChannelParam);
				//主播当时积分和聊天时长更新
				closeChannelParam.setDate(TimeFormat.getDate());
				liveMapper.updateLiveRecord(closeChannelParam);
				//用户金币减少
				liveMapper.updateGold(closeChannelParam);
				return closeChannelParam.getChat_time();
			}
			return 0;
		} catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
		
	}

	@Override
	public ResultView<String> liveStatus(LiveParam liveParam) {
		ResultView<String> resultView=new ResultView<String>();
		int result=liveMapper.liveStatus(liveParam);
		if(liveParam.getStatus()==1) {
			//上播
			liveParam.setStart_time(TimeFormat.getSimple());
			liveMapper.insLiveTime(liveParam);
			liveParam.setDate(TimeFormat.getDate());
			Live_record live_record =liveMapper.selLiveRecord(liveParam);
			if(live_record==null) {
				liveMapper.insLiveRecord(liveParam);
			}
		}
		if(liveParam.getStatus()==2) {
			//下播
			Live_time live_time=liveMapper.selNewLiveTime(liveParam);
			liveParam.setDate(TimeFormat.getDate());
			liveParam.setEnd_time(TimeFormat.getSimple());
			liveParam.setTime(Live_Deductions.getChatTime(live_time.getStart_time(), liveParam.getEnd_time()));
			
			int up=liveMapper.upLiveTime(liveParam);
			if(up==0) {
				liveParam.setStart_time(TimeFormat.getZero());
				liveParam.setTime(Live_Deductions.getChatTime(liveParam.getStart_time(), liveParam.getEnd_time()));
				
				liveMapper.insLiveTime(liveParam);
			}
			//累计今天在线时间
			liveMapper.countTodayLiveTime(liveParam);
		}
		//修改用户身份
		int resultset=liveMapper.updateIdentity(liveParam);
		if(result>0 && resultset>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
		}else {
			resultView.setCode(2013);
			resultView.setMessage("你还不是主播或你已被禁播");
		}
		return resultView;
	}
	
	
	
	@Override
	public ResultView<String> updateStars(EvaluateParam evaluateParam) {
		ResultView<String> resultView=new ResultView<String>();
		int result=liveMapper.updateStars(evaluateParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("评价成功");
		}else {
			resultView.setCode(2017);
			resultView.setMessage("评价太频繁，请稍后尝试");
		}
		return resultView;
	}

	@Override
	public ResultView<String> selEverdayVideo(int userId) {
		ResultView<String> resultView=new ResultView<String>();
		Everyday_live_list everydayVideo=liveMapper.selEverdayVideo(userId);
		if(everydayVideo!=null) {
			String time=TimeFormat.getSimple();
			try {
				if(TimeFormat.sub(time, everydayVideo.getCreate_time())) {
					//大于24小时
					resultView.setCode(1000);
					resultView.setMessage("需要重新上传");
				}else {
					//小于24小时
					resultView.setCode(1003);
					resultView.setMessage("不需要重新上传");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				resultView.setCode(1004);
				resultView.setMessage("后台数据处理异常");
			}
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public ResultView<List<VideoClips>> selVideoClips(VideoClipsParam videoClipsParam) {
		ResultView<List<VideoClips>> resultView=new ResultView<List<VideoClips>>();
		int page=videoClipsParam.getPage()>0?videoClipsParam.getPage():1;
		int start=(page-1)*videoClipsParam.getNumber();
		videoClipsParam.setStart(start);
		if(!StringUtils.isNoneBlank(String.valueOf(videoClipsParam.getCountry_id()))) {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
			return resultView;
		}
		Log4jUtil.info("渠道ID："+String.valueOf(videoClipsParam.getChannel_id()));
		if(videoClipsParam.getChannel_id()==0) {
			videoClipsParam.setChannel_id(0);
		}
		if(StringUtils.isNoneBlank(String.valueOf(videoClipsParam.getChannel_id()))) {
			Video_level_filter video_level_filter=liveMapper.selVideoFilter(videoClipsParam);
			if(video_level_filter!=null) {
				videoClipsParam.setVideo_level(video_level_filter.getVideo_level().split(","));
			}
		}
		
		List<VideoClips> list=liveMapper.selVideoClips(videoClipsParam);
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
	public ResultView<List<HotList>> selHotList(VideoClipsParam videoClipsParam) {
		ResultView<List<HotList>> resultView=new ResultView<List<HotList>>();
		int page=videoClipsParam.getPage()>0?videoClipsParam.getPage():1;
		int start=(page-1)*videoClipsParam.getNumber();
		videoClipsParam.setStart(start);
		List<HotList> list=liveMapper.selHotList(videoClipsParam);
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
	public ResultView<List<NewList>> selNewList(VideoClipsParam videoClipsParam) {
		ResultView<List<NewList>> resultView=new ResultView<List<NewList>>();
		int page=videoClipsParam.getPage()>0?videoClipsParam.getPage():1;
		int start=(page-1)*videoClipsParam.getNumber();
		videoClipsParam.setStart(start);
		List<NewList> list=liveMapper.selNewList(videoClipsParam);
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
	
	@Transactional
	@Override
	public ResultView<PlayVideoInfo> playVideo(int userId, int id) {
		ResultView<PlayVideoInfo> resultView=new ResultView<PlayVideoInfo>();
		PlayVideoInfo playVideoInfo=new PlayVideoInfo();
		try {
			//先查询视频的播放地址和价格
			Video_List video_List=liveMapper.selVideoInfo(id);
			//累计播放次数
			liveMapper.updateWatchNumber(video_List);
			//计算用户收益
			double profit=liveMapper.selProfit(video_List.getUser_id());
			video_List.setProfit(profit);
			video_List.setProfitScore(video_List.getPrice()*video_List.getProfit());
			
			//执行扣钱后 返回客户端
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("price", video_List.getPrice());
			map.put("userId", userId);
			map.put("video_id", id);
			int result=liveMapper.updateUserPackage(map);
			//记录观看历史
			map.put("user_id_video_id", String.valueOf(userId)+String.valueOf(id));
			map.put("create_time", TimeFormat.getSimple());
			map.put("status", 1);
			liveMapper.insWatchHistory(map);
			//视频拥有者获得利润
			Live_app_user_info live_app_user_info=liveMapper.selUserAllInfo(video_List.getUser_id());
			live_app_user_info.setUser_score(live_app_user_info.getUser_score()+video_List.getProfitScore());
			Log4jUtil.info("积分余额："+String.valueOf(live_app_user_info.getUser_score()));
			liveMapper.updateUserScoreByVideo(live_app_user_info);
			Log4jUtil.info("累计用户的收益");
			//累计用户的收益
			liveMapper.countVideoScore(video_List);
			
			if(result>0) {
				//关注状态
				//查询是否关注
				SearchUserInfoParam searchUserInfoParam=new SearchUserInfoParam();
				searchUserInfoParam.setLoginUserId(userId);
				searchUserInfoParam.setUserId(video_List.getUser_id());
				Follow follow=personDetailMapper.isFollowUser(searchUserInfoParam);
				if(follow!=null && follow.getStatus()==1) {
					playVideoInfo.setFollowStatus(1);
				}else {
					playVideoInfo.setFollowStatus(2);
				}
				//评论总数
				int count=liveMapper.countVideoComment(id);
				playVideoInfo.setCountComment(count);
				String cdn_switch=new ReadResourceConfigUtils().getRedisConfig("/config/globalConfig.properties", "cdn_switch");
				//1 表示用CDN加速域名访问视频资源
				if("1".equals(cdn_switch)) {
					String cdnName=new ReadResourceConfigUtils().getRedisConfig("/config/globalConfig.properties", "cdnurl");
					playVideoInfo.setVideo_url(cdnName+video_List.getVideo_new_name());
				}else {
					playVideoInfo.setVideo_url(video_List.getVideo_url());
				}
				Log4jUtil.info("playUrl-----------"+playVideoInfo.getVideo_url());
				playVideoInfo.setVideo_description(video_List.getVideo_description());
				playVideoInfo.setPraise_channel(video_List.getPraise_channel());
				playVideoInfo.setComment_channel(video_List.getComment_channel());
				playVideoInfo.setGift_channel(video_List.getGift_channel());
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(playVideoInfo);
			}else {
				resultView.setCode(1007);
				resultView.setMessage("余额不足，请先充值");
			}
		} catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultView.setCode(1007);
			resultView.setMessage("余额不足，请先充值");
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> updateLiveUserStatus(int userId) {
		ResultView<String> resultView=new ResultView<String>();
		int result=liveMapper.updateLiveUserStatus(userId);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
		}else {
			resultView.setCode(2026);
			resultView.setMessage("上播失败");
		}
		return resultView;
	}
	
	public static void main(String[] args) {
		int i=1;
		double s=0.4;
		System.out.println(((double)i)*s);
	}
	
	@Override
	public ResultView<TodayLive> getRoomProfit(ProfitParam profitParam) {
		ResultView<TodayLive> resultView=new ResultView<TodayLive>();
		try {
			profitParam.setDate(TimeFormat.getDate());
			TodayLive todayLive=liveMapper.selUserInfo(profitParam);
			if(todayLive!=null) {
				Live_room_info live_room_info=liveMapper.selLiveRoomById(profitParam);
				System.err.println("聊天开始时间："+live_room_info.getChat_start_time());
				System.out.println("聊天结束时间："+live_room_info.getChat_end_time());
				int time=Live_Deductions.getChatTime(live_room_info.getChat_start_time(), live_room_info.getChat_end_time());
				System.out.println("聊天时长："+time+"分钟");
				todayLive.setRoom_chat_time(time);
				System.err.println("本次聊天时长："+todayLive.getRoom_chat_time());
				System.err.println("收益比例："+live_room_info.getLive_profit());
				System.err.println("本次聊天收益："+(double)( (todayLive.getRoom_chat_time())*(live_room_info.getLive_profit())  ) );
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
				todayLive.setUser_reward_score(Double.parseDouble(df.format(live_room_info.getUser_reward_score()+((double)todayLive.getRoom_chat_time())*live_room_info.getLive_profit())));
				profitParam.setUser_score(((double)todayLive.getToday_chat_time())*live_room_info.getLive_profit());
				liveMapper.updateUserScore(profitParam);
				List<TempObj> list=liveMapper.selRoomAllGifts(profitParam);
				todayLive.setList(list);
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(todayLive);
				System.err.println("主播收益："+todayLive.getUser_reward_score());
				//累计主播收益积分
				liveMapper.countScore(todayLive);
				
			}else {
				resultView.setCode(1000);
				resultView.setMessage("失败");
				resultView.setData(todayLive);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

	@Override
	public int insChatStartTime(int userId) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("start_time", TimeFormat.getSimple());
		liveMapper.insChatStartTime(map);
		return 0;
	}

	@Override
	public int insChatEndTime(int userId) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("end_time", TimeFormat.getSimple());
		//先查询是否有时间 有时间就不修改时间
		Live_room_info live_room_info=liveMapper.selChatEndTime(map);
		if(!StringUtils.isNoneBlank(live_room_info.getChat_end_time())) {
			liveMapper.insChatEndTime(map);
		}
		
		return 0;
	}
	
	
	
}
