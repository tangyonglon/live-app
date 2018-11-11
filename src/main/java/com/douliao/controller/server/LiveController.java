package com.douliao.controller.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.CloseChannelParam;
import com.douliao.controller.server.model.EvaluateParam;
import com.douliao.controller.server.model.LiveParam;
import com.douliao.controller.server.model.LoginChannelParam;
import com.douliao.controller.server.model.ProfitParam;
import com.douliao.model.response.CreateChannel;
import com.douliao.model.response.TodayLive;
import com.douliao.result.ResultView;
import com.douliao.service.LiveService;
import com.douliao.util.Log4jUtil;

@RestController
public class LiveController {
	@Autowired
	private LiveService liveService;
	
	/**
	 * 开播前查询是否需要重新录制小视频
	 * @param userId
	 * @return 
	 */
	@RequestMapping(value="/live/selEverdayVideo",method=RequestMethod.POST)
	public ResultView<String> selEverdayVideo(int userId) {
		ResultView<String> resultView=liveService.selEverdayVideo(userId);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 开播或下播
	 * @param liveParam
	 * @return
	 */
	@RequestMapping(value="/live/liveStatus",method=RequestMethod.POST)
	public ResultView<String> liveStatus(LiveParam liveParam) {
		ResultView<String> resultView=liveService.liveStatus(liveParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 主播创建直播间并加入
	 * @param userId
	 * @param loginMode
	 * @param mac
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/live/createChannel",method=RequestMethod.POST)
	public ResultView<CreateChannel> createChannel(int userId) {
		ResultView<CreateChannel> resultView=new ResultView<CreateChannel>();
		CreateChannel createChannel=new CreateChannel();
		String channel="";  									//频道名或频道ID
		int ts = (int) System.currentTimeMillis();				//授权时间戳
		int r = (int)((Math.random()*8+1)*1000);				//随机数
		int result=liveService.CreateRoom(userId);
		if(result>0) {
			//生成创建时间  channelKey
			channel=String.valueOf(result);
			//String channelKey=CreateChannelKey.CreateKey(appID, appCertificate, channel, ts, r);
			createChannel.setUserId(userId);
			createChannel.setChannel(result);
			//createChannel.setChannelKey(channelKey);
			resultView.setCode(1000);
			resultView.setMessage("创建成功");
			resultView.setData(createChannel);
		}else {
			resultView.setCode(2014);
			resultView.setMessage("创建失败");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 用户接受邀请加入直播间
	 * @param loginChannelParam
	 * @return
	 */
	@RequestMapping(value="/live/loginChannel",method=RequestMethod.POST)
	public ResultView<String> loginChannel(LoginChannelParam loginChannelParam) {
		ResultView<String> resultView=liveService.loginChannel(loginChannelParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 关闭频道
	 * @param closeChannelParam
	 * @return
	 */
	@RequestMapping(value="/live/closeChannel",method=RequestMethod.POST)
	public ResultView<String> closeChannel(CloseChannelParam closeChannelParam) {
		ResultView<String> resultView=new ResultView<String>();
		//计算扣费
		try {
			int chat_time=liveService.closeChannel(closeChannelParam);
			resultView.setCode(1000);
			resultView.setMessage("关闭成功");
			resultView.setData(String.valueOf(chat_time));
		} catch (Exception e) {
			resultView.setCode(2025);
			resultView.setMessage("结算失败");
			e.printStackTrace();
		}
		
		return resultView;
	}
	
	
	/**
	 * 评价接口
	 * @param evaluateParam
	 */
	@RequestMapping(value="/live/userEvaluate",method=RequestMethod.POST)
	public ResultView<String> userEvaluate(EvaluateParam evaluateParam) {
		ResultView<String> resultView=liveService.updateStars(evaluateParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
	/**
	 * 直播结束切换主播状态
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/live/liveAgain",method=RequestMethod.POST)
	public ResultView<String> liveAgain(int userId) {
		//继续直播 切换主播状态
		ResultView<String> resultView=liveService.updateLiveUserStatus(userId);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 获取该直播间的收益详情
	 * @param profitParam
	 * @return
	 */
	@RequestMapping(value="/live/roomProfit",method=RequestMethod.POST)
	public ResultView<TodayLive> roomProfit(ProfitParam profitParam) {
		ResultView<TodayLive> resultView=liveService.getRoomProfit(profitParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
