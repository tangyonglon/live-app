package com.douliao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.AnchorInfoParam;
import com.douliao.controller.server.model.BeginLiveParam;
import com.douliao.controller.server.model.CheckAudit;
import com.douliao.controller.server.model.EndLiveParam;
import com.douliao.controller.server.model.EntryRoomParam;
import com.douliao.controller.server.model.GiveGiftParam;
import com.douliao.controller.server.model.LeaveRoomParam;
import com.douliao.controller.server.model.SwitchRoomParam;
import com.douliao.mapper.NewLiveMapper;
import com.douliao.model.AnchorInfo;
import com.douliao.model.database.All_live_room;
import com.douliao.model.database.All_live_room_chat;
import com.douliao.model.database.Anchor_income;
import com.douliao.model.database.CharacterType;
import com.douliao.model.database.Gifts;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.User_character;
import com.douliao.model.database.Video_List;
import com.douliao.result.ResultView;
import com.douliao.service.NewLiveService;
import com.douliao.util.CreateOrder;
import com.douliao.util.Live_Deductions;
import com.douliao.util.TimeFormat;
import com.douliao.util.Transform;

@Service
public class NewLiveServiceImpl implements NewLiveService{
	@Autowired
	private NewLiveMapper newLiveMapper;

	@Override
	public ResultView<All_live_room> isAudit(CheckAudit checkAudit) {
		ResultView<All_live_room> resultView=new ResultView<All_live_room>();
		All_live_room all_live_room=newLiveMapper.isAudit(checkAudit);
		if(all_live_room!=null) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(all_live_room);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		
		return resultView;
	}

	@Override
	public int uploadLiveVideo(Video_List video_List) {
		try {
			//1.查询是否已经上传过视频  上传过则更新  没上传过则添加
			All_live_room all_live_room=newLiveMapper.selLiveVideo(video_List);
			int result;
			if(all_live_room!=null) {
				//更新
				result=newLiveMapper.updateLiveVideo(video_List);
			}else {
				//添加
				//生成直播间号和直播价格
				video_List.setLive_room_id(Integer.parseInt(CreateOrder.createLiveRoomId()));
				result=newLiveMapper.insLiveVideo(video_List);
			}
			return result;
		} catch (Exception e) {
			return 0;
		}
		
	}

	@Override
	public ResultView<BeginLiveParam> beginLive(BeginLiveParam beginLiveParam) {
		ResultView<BeginLiveParam> resultView=new ResultView<BeginLiveParam>();
		beginLiveParam.setStart_time(TimeFormat.getSimple());
		beginLiveParam.setStatus(1);
		//查询是否是主播
		Live_app_user_info live_app_user_info=newLiveMapper.isAnchor(beginLiveParam);
		if(live_app_user_info.getUser_authentication()==1) {
			resultView.setCode(1017);
			resultView.setMessage("用户未认证");
			return resultView;
		}
		if(beginLiveParam.getIm_channel()!=null && !"".equals(beginLiveParam.getIm_channel())) {
			//存入该直播间对应IM的频道地址
			newLiveMapper.updateIMChannel(beginLiveParam);
		}
		
		double live_price=newLiveMapper.selLivePrice(beginLiveParam);
		beginLiveParam.setLive_price(live_price);
		//是否已经开播 不能重复开播
		List<Map<String, Object>> list=newLiveMapper.isBeginLive(beginLiveParam);
		if(list.size()>0) {
			resultView.setCode(2034);
			resultView.setMessage("您已开播，不能重复开播");
		}else {
			int result=newLiveMapper.beginLive(beginLiveParam);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
				resultView.setData(beginLiveParam);
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> entryRoom(EntryRoomParam entryRoomParam) {
		ResultView<String> resultView=new ResultView<String>();
		entryRoomParam.setStart_time(TimeFormat.getSimple());
		entryRoomParam.setStatus(1);
		//查询该用户是否进过直播间
		All_live_room_chat all_live_room_chat=newLiveMapper.selRoomRecord(entryRoomParam);
		int result;
		if(all_live_room_chat!=null) {
			//更新
			result=newLiveMapper.updateRoomRecord(entryRoomParam);
			
		}else {
			//添加
			result=newLiveMapper.insRoomRecord(entryRoomParam);
		}
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
		}else {
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		
		return resultView;
	}

	@Override
	public ResultView<All_live_room_chat> leaveRoom(LeaveRoomParam leaveRoomParam) {
		ResultView<All_live_room_chat> resultView=new ResultView<All_live_room_chat>();
		try {
			All_live_room_chat all_live_room_chat=newLiveMapper.selleaveRoom(leaveRoomParam);
			all_live_room_chat.setEnd_time(TimeFormat.getSimple());
			all_live_room_chat.setStatus(2);
			if(all_live_room_chat!=null) {
				//计算时间 单位（分钟）
				int time=Live_Deductions.getChatTime(all_live_room_chat.getStart_time(), TimeFormat.getSimple());
				all_live_room_chat.setChat_time(time);
				all_live_room_chat.setTotal_gold(all_live_room_chat.getLive_price()*(double)time);
				int result=newLiveMapper.updateleaveRoom(all_live_room_chat);
				if(result>0) {
					resultView.setCode(1000);
					resultView.setMessage("成功");
					if(leaveRoomParam.getLive_room_type()==1) {
						resultView.setData(all_live_room_chat);
					}
				}else {
					resultView.setCode(1002);
					resultView.setMessage("服务器繁忙，请稍后重试");
				}
			}
		} catch (Exception e) {
			resultView.setCode(1016);
			resultView.setMessage("该用户没有进入任何直播间记录");
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> switchRoom(SwitchRoomParam switchRoomParam) {
		ResultView<String> resultView=new ResultView<String>();
		try {
			int result=newLiveMapper.switchRoom(switchRoomParam);
			newLiveMapper.updateRoomChat(switchRoomParam);
			if(result>0 ) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else {
				resultView.setCode(1002);
				resultView.setMessage("服务器繁忙，请稍后重试");
			}
		} catch (Exception e) {
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		
		return resultView;
	}

	@Override
	public ResultView<String> endLive(EndLiveParam endLiveParam) {
		ResultView<String> resultView=new ResultView<String>();
		endLiveParam.setEnd_time(TimeFormat.getSimple());
		endLiveParam.setStatus(2);
		int result=newLiveMapper.endLive(endLiveParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
		}else {
			resultView.setCode(2033);
			resultView.setMessage("因网络原因已经下播");
		}
		return resultView;
	}
	
	@Transactional
	@Override
	public double giveGift(GiveGiftParam giveGiftParam) {
		try {
			//赠送礼物
			//1.个人金币扣除
			//2.个人消费记录表记录
			//3.主播积分增加（每日凌晨增加主播的积分）
			//4.累计今日主播收益
			//5.礼物记录
			Gifts gifts=newLiveMapper.selGiftsInfo(giveGiftParam);
			giveGiftParam.setCreate_time(TimeFormat.getDate());
			giveGiftParam.setGifts_price(gifts.getGifts_price());
			giveGiftParam.setTotal_gold(giveGiftParam.getGifts_price()*(double)giveGiftParam.getAmount());
			
			Live_app_user_info live_app_user_info= newLiveMapper.selUserPackage(giveGiftParam);
			if(live_app_user_info.getUser_package()-giveGiftParam.getTotal_gold()>0) {
				newLiveMapper.reduceGold(giveGiftParam);
			}else {
				//余额不足
				return 0;
			}
			
			newLiveMapper.userConsumRecord(giveGiftParam);
			giveGiftParam.setIncome_date(TimeFormat.getDate());
			Anchor_income anchor_income=newLiveMapper.selanchorIncome(giveGiftParam);
			if(anchor_income!=null) {
				//查询这个直播间的利润比例
				All_live_room all_live_room=newLiveMapper.selLiveProfit(giveGiftParam);
				giveGiftParam.setLive_user_id(all_live_room.getUser_id());
				giveGiftParam.setScore(giveGiftParam.getTotal_gold()*all_live_room.getLive_profit());
				newLiveMapper.insUserIncome(giveGiftParam);
				newLiveMapper.insRoomGifts(giveGiftParam);
			}
			return live_app_user_info.getUser_package()-giveGiftParam.getTotal_gold();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        return -1;
		}
	}

	@Override
	public ResultView<String> updateRoomStatus(Map<String, Object> map) {
		ResultView<String> resultView=new ResultView<String>();
		try {
			map.put("end_time",TimeFormat.getSimple());
			map.put("status",2);
			newLiveMapper.updateRoomStatus(map);
			newLiveMapper.updateRoomChatStatus(map);
			resultView.setCode(1000);
			resultView.setMessage("成功");
		} catch (Exception e) {
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		
		return resultView;
	}

	@Override
	public void updatechat(Map<String, Object> map) {
		try {
			map.put("end_time",TimeFormat.getSimple());
			map.put("status",2);
			newLiveMapper.updateRoomChatStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ResultView<List<Map<String, Object>>> showLiveList() {
		ResultView<List<Map<String, Object>>> resultView=new ResultView<List<Map<String, Object>>>();
		List<Map<String, Object>> list=newLiveMapper.showLiveList();
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

	@Override
	public ResultView<AnchorInfo> anchorInfo(AnchorInfoParam anchorInfoParam) {
		ResultView<AnchorInfo> resultView=new ResultView<AnchorInfo>();
		AnchorInfo anchorInfo= newLiveMapper.anchorInfo(anchorInfoParam);
		//获取用户的标签
		User_character user_character=newLiveMapper.selCharacterTypeByUserId(anchorInfoParam);
		if(user_character!=null) {
			int[] array=Transform.stringTransfromIntArray(user_character.getCharacter_id());
			List<CharacterType> list=newLiveMapper.selCharacterType(array);
			if(list.size()>0) {
				anchorInfo.setList(list);
			}
		}
		if(anchorInfo!=null) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(anchorInfo);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无数据");
		}
		return resultView;
	}

}
