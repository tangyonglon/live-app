package com.douliao.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.douliao.controller.server.model.RewardParam;
import com.douliao.controller.server.model.VideoGiftsParam;
import com.douliao.mapper.GiftMapper;
import com.douliao.mapper.LiveMapper;
import com.douliao.model.database.Gifts;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Live_room_gifts;
import com.douliao.model.database.Live_room_info;
import com.douliao.model.database.Video_gifts;
import com.douliao.result.ResultView;
import com.douliao.service.GiftService;
import com.douliao.util.Log4jUtil;

@Service
public class GiftServiceImpl implements GiftService{
	
	@Autowired
	private GiftMapper giftMapper;
	@Autowired
	private LiveMapper liveMapper;

	@Override
	public ResultView<List<Gifts>> findAllGifts() {
		ResultView<List<Gifts>> resultView=new ResultView<List<Gifts>>();
		List<Gifts> list=giftMapper.findAllGifts();
//		List<Gifts> list=new ArrayList<Gifts>();
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
	public double rewareGifts(RewardParam rewardParam) {
		try {
			Gifts gifts=giftMapper.selGiftsInfo(rewardParam.getGifts_id());
			rewardParam.setGifts_price(gifts.getGifts_price());
			//计算花费总金币数
			rewardParam.setTotal_gold(gifts.getGifts_price()*((double)rewardParam.getAmount()));
			//扣除用户金币
			giftMapper.updateGolds(rewardParam);
			
			//查询直播间是否已经送过该礼物
			Live_room_gifts live_room_gifts=giftMapper.selGiftsById(rewardParam);
			if(live_room_gifts!=null) {
				//累加礼物数量和总金额
				giftMapper.updateGiftsAmount(rewardParam);
			}else {
				//新增礼物
				giftMapper.insLiveRoomGifts(rewardParam);
			}
			//主播利润分成比例
			Live_room_info live_room_info=liveMapper.selById(rewardParam.getLive_room_id());
			rewardParam.setUser_score(rewardParam.getTotal_gold()*live_room_info.getLive_profit());
			//主播积分增加
			giftMapper.addScore(rewardParam);
			//累计直播间打赏积分
			giftMapper.addRoomGold(rewardParam);
			//返回用户剩余金币
			double user_package=giftMapper.selAccountGold(rewardParam);
			return user_package;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        return -1;
		}
//		throw new RuntimeException("结算出现异常，事物回滚");
	}
	
	@Transactional
	@Override
	public double rewareVideoGifts(VideoGiftsParam videoGiftsParam) {
		try {
			Gifts gifts=giftMapper.selGiftsInfo(videoGiftsParam.getGiftsId());
			videoGiftsParam.setGifts_price(gifts.getGifts_price());
			//计算花费总金币数
			videoGiftsParam.setTotal_gold(gifts.getGifts_price()*((double)videoGiftsParam.getAmount()));
			Live_app_user_info live_app_user_info=giftMapper.seluserInfo(videoGiftsParam.getUserId());
			Log4jUtil.info("用户余额："+live_app_user_info.getUser_package());
			Log4jUtil.info("此次消费金额："+videoGiftsParam.getTotal_gold());
			double money=live_app_user_info.getUser_package()-videoGiftsParam.getTotal_gold();
			Log4jUtil.info("扣除此次消费后的余额："+money);
			if(money<0) {
				//事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		        return -1;
			}else {
				live_app_user_info.setUser_package(money);
			}
			//扣除用户金币
			int result=giftMapper.updateUserGolds(live_app_user_info);
			Video_gifts video_gifts=giftMapper.selVideoGifts(videoGiftsParam);
			if(video_gifts!=null) {
				//累加礼物数量和总金额
				giftMapper.updateVideoGiftsAmount(videoGiftsParam);
			}else {
				//新增礼物
				giftMapper.insVideoGifts(videoGiftsParam);
			}
			//获取用户的利润分成
			double profit=giftMapper.selUserProfit(videoGiftsParam.getVideoUserId());
			videoGiftsParam.setUser_score(videoGiftsParam.getTotal_gold()*profit);
			//用户积分增加
			Live_app_user_info live_app_user_info2=new Live_app_user_info();
			live_app_user_info2=giftMapper.seluserInfo(videoGiftsParam.getVideoUserId());
			live_app_user_info2.setUser_score(live_app_user_info2.getUser_score()+videoGiftsParam.getUser_score());
			int resultset=giftMapper.updateUserScore(live_app_user_info2);
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("user_id", live_app_user_info2.getUser_id());
			map.put("score", videoGiftsParam.getUser_score());
			//累计用户积分
			giftMapper.countScoreByVideo(map);
			
//			if(result<=0 || resultset<=0) {
//				//事务回滚
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//		        return -1;
//			}
			
			//返回用户剩余金币
			return live_app_user_info.getUser_package();
		} catch (Exception e) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        return -1;
		}
	}
	
	
}
