package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.RewardParam;
import com.douliao.controller.server.model.VideoGiftsParam;
import com.douliao.model.database.Gifts;
import com.douliao.result.ResultView;
import com.douliao.service.GiftService;
import com.douliao.util.Log4jUtil;

@RestController
public class GiftController {
	
	@Autowired
	private GiftService GiftService;
	
	/**
	 * 获取所有礼物
	 * @return
	 */
	@RequestMapping(value="/gift/findAllgifts",method=RequestMethod.POST)
	public ResultView<List<Gifts>> findAllgifts() {
		ResultView<List<Gifts>> resultView=GiftService.findAllGifts();
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 一对一直播赠送礼物（无须考虑高并发情况，乐观锁机制）
	 * @param rewardParam
	 * @return
	 */
	@RequestMapping(value="/gift/reward",method=RequestMethod.POST)
	public ResultView<Double> reware(RewardParam rewardParam) {
		ResultView<Double> resultView=new ResultView<Double>();
		try {
			double user_package=GiftService.rewareGifts(rewardParam);
			if(user_package>0) {
				resultView.setCode(1000);
				resultView.setMessage("赠送成功");
				resultView.setData(user_package);
			}else {
				resultView.setCode(2025);
				resultView.setMessage("余额不足，礼物赠送失败");
			}
		} catch (Exception e) {
			resultView.setCode(2025);
			resultView.setMessage("余额不足，礼物赠送失败");
			e.printStackTrace();
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 观看视频赠送礼物
	 * @param videoGiftsParam
	 */
	@RequestMapping(value="/gift/videoGifts",method=RequestMethod.POST)
	public ResultView<Double> videoGifts(VideoGiftsParam videoGiftsParam) {
		ResultView<Double> resultView=new ResultView<Double>();
		double user_package=GiftService.rewareVideoGifts(videoGiftsParam);
		Log4jUtil.info("返回用户剩余金额"+user_package);
		if(user_package>0) {
			resultView.setCode(1000);
			resultView.setMessage("赠送成功");
			resultView.setData(user_package);
		}else {
			resultView.setCode(2025);
			resultView.setMessage("余额不足，礼物赠送失败");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
}
