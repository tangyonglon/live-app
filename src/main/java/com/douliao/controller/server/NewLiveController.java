package com.douliao.controller.server;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.AnchorInfoParam;
import com.douliao.controller.server.model.BeginLiveParam;
import com.douliao.controller.server.model.CheckAudit;
import com.douliao.controller.server.model.CheckClientParam;
import com.douliao.controller.server.model.CheckLiveRoomParam;
import com.douliao.controller.server.model.EndLiveParam;
import com.douliao.controller.server.model.EntryRoomParam;
import com.douliao.controller.server.model.GiveGiftParam;
import com.douliao.controller.server.model.LeaveRoomParam;
import com.douliao.controller.server.model.SwitchRoomParam;
import com.douliao.model.AnchorInfo;
import com.douliao.model.Result;
import com.douliao.model.database.All_live_room;
import com.douliao.model.database.All_live_room_chat;
import com.douliao.result.ResultView;
import com.douliao.service.NewLiveService;

@RestController
public class NewLiveController {
	@Autowired
	private NewLiveService newLiveService; 
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Value("${web.file}")
	private String filePath;
	@Value("${web.showFile}")
	private String showFilePath;
//	@Value("${web.ffmpegPath}")
//	private String ffmpegPath;
	
	//上传视频的时候给用户创建直播间号
	//1.上传视频时创建直播间号
	//2.开播 
		//a.选择开播一对一收费直播，暂时不开直播频道，用户呼叫在创建直播频道
		//b.选择开播一对多
	
	//显示主播列表接口
	/**
	 * 显示主播列表接口
	 * @return
	 */
	@RequestMapping(value="/newLive/showLiveList",method=RequestMethod.POST)
	public ResultView<List<Map<String, Object>>> showLiveList() {
		ResultView<List<Map<String, Object>>> resultView=newLiveService.showLiveList();
		return resultView;
	}
	
	
	//主播信息显示
	/**
	 * 主播信息显示
	 * @param anchorInfoParam
	 * @return
	 */
	@RequestMapping(value="/newLive/anchorInfo",method=RequestMethod.POST)
	public ResultView<AnchorInfo> anchorInfo(AnchorInfoParam anchorInfoParam) {
		ResultView<AnchorInfo> resultView=newLiveService.anchorInfo(anchorInfoParam);
		return resultView;
	}
	
	
	
	/**
	 * 查询是否可以提交申请成为主播
	 * @param checkAudit
	 * @return
	 */
	@RequestMapping(value="/newLive/isAudit",method=RequestMethod.POST)
	public ResultView<All_live_room> isAudit(CheckAudit checkAudit,HttpServletRequest request) {
		checkAudit.setUser_id(Integer.parseInt(request.getParameter("userId")));
		return newLiveService.isAudit(checkAudit);
	}
	
	
	//开播接口
	//下播接口  要统计出今天的收益情况
	//用户进入直播间
	//用户离开直播间
	//结束本次直播
	//赠送礼物接口
	//用户端本次直播花费
	//主播端本次直播收益
	//主播端心跳检测
	//客户端心跳检测
	//轮询扣费
	//每日定00:00:00定时更新用户积分数据
	//轮询心跳数据 关闭没有心跳的直播间
	
	
	/**
	 * 开播
	 * @param beginLiveParam
	 * @return
	 */
	@RequestMapping(value="/newLive/beginLive",method=RequestMethod.POST)
	public ResultView<BeginLiveParam> beginLive(BeginLiveParam beginLiveParam,HttpServletRequest request) {
		beginLiveParam.setLive_user_id(Integer.parseInt(request.getParameter("userId")));
		return newLiveService.beginLive(beginLiveParam);
	}
	
	/**
	 * 下播
	 * @param endLiveParam
	 * @return
	 */
	@RequestMapping(value="/newLive/endLive",method=RequestMethod.POST)
	public ResultView<String> endLive(EndLiveParam endLiveParam,HttpServletRequest request) {
		endLiveParam.setLive_user_id(Integer.parseInt(request.getParameter("userId")));
		ResultView<String> resultView=newLiveService.endLive(endLiveParam);
		return resultView;
	}
	
	
	/**
	 * 一对多切换直播间类型
	 * @param switchRoomParam
	 */
	@RequestMapping(value="/newLive/switchRoom",method=RequestMethod.POST)
	public ResultView<String> switchRoom(SwitchRoomParam switchRoomParam) {
		ResultView<String> resultView=newLiveService.switchRoom(switchRoomParam);
		return resultView;
	}
	
	
	
	//一对一 一对多  赠送了礼物记录到个人消费记录中 主播收益记录到主播间收益中
	//下播接口 要统计出今天的收益情况
	//直播间的礼物收益表  用户赠送礼物记录收益
	//直播间的聊天收益表 轮询扣费记录收益
	/**
	 * 用户进入直播间
	 * @param entryRoomParam
	 * @return
	 */
	@RequestMapping(value="/newLive/entryRoom",method=RequestMethod.POST)
	public ResultView<String> entryRoom(EntryRoomParam entryRoomParam,HttpServletRequest request) {
		entryRoomParam.setUser_id(Integer.parseInt(request.getParameter("userId")));
		//用户进入直播间 
		//1.如果是扣费的
		ResultView<String> resultView=newLiveService.entryRoom(entryRoomParam);
		return resultView;
	}
	
	/**
	 * 用户离开直播间
	 * @param leaveRoomParam
	 * @return
	 */
	@RequestMapping(value="/newLive/leaveRoom",method=RequestMethod.POST)
	public ResultView<All_live_room_chat> leaveRoom(LeaveRoomParam leaveRoomParam,HttpServletRequest request) {
		leaveRoomParam.setUser_id(Integer.parseInt(request.getParameter("userId")));
		ResultView<All_live_room_chat> resultView=newLiveService.leaveRoom(leaveRoomParam);
		return resultView;
	}
	
	/**
	 * 赠送礼物接口
	 * @param giveGiftParam
	 * @return
	 */
	@RequestMapping(value="/newLive/giveGift",method=RequestMethod.POST)
	public ResultView<Double> giveGift(GiveGiftParam giveGiftParam,HttpServletRequest request) {
		giveGiftParam.setUser_id(Integer.parseInt(request.getParameter("userId")));
		ResultView<Double> resultView=new ResultView<Double>();
		double d=newLiveService.giveGift(giveGiftParam);
		if(d>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(d);
		}else if(d==0) {
			resultView.setCode(1007);
			resultView.setMessage("余额不足，请先充值");
		}else {
			resultView.setCode(1002);
			resultView.setMessage("服务器繁忙，请稍后重试");
		}
		return resultView;
	}
	
	/**
	 * 主播端心跳检测接口
	 * @param checkLiveRoomParam
	 */
	@RequestMapping(value="/newLive/checkLiveRoom",method=RequestMethod.POST)
	public ResultView<String> checkLiveRoom(CheckLiveRoomParam checkLiveRoomParam,HttpServletRequest request) {
		checkLiveRoomParam.setLive_user_id(Integer.parseInt(request.getParameter("userId")));
		ResultView<String> resultView=new ResultView<String>();
		//主播端心跳检测  连续收到三次没有收到心跳则关闭直播间的扣费
		//收到心跳则表示正常
		//一分钟发送一次心跳检测 如果累计三次前再次收到心跳则清0 重新累计
		//redis缓存记录 用户id+直播间id+客户端ID（主播客户端ID为1，用户客户端ID为2）作为key 累计次数作为值
		String key="live-"+checkLiveRoomParam.getLive_room_id()+"-"+checkLiveRoomParam.getLive_user_id()+"-1";
		redisTemplate.opsForValue().set(key, "0");
		//成功
		resultView.setCode(1000);
		resultView.setMessage("成功");
		
		
		return resultView;
	}
	
	/**
	 * 客户端心跳检测
	 * @param checkClientParam
	 * @return
	 */
	@RequestMapping(value="/newLive/checkClient",method=RequestMethod.POST)
	public ResultView<String> checkClient(CheckClientParam checkClientParam,HttpServletRequest request) {
		checkClientParam.setUser_id(Integer.parseInt(request.getParameter("userId")));
		ResultView<String> resultView=new ResultView<String>();
		//redis缓存记录 用户id+直播间id+客户端ID（主播客户端ID为1，用户客户端ID为2）作为key 累计次数作为值
		String key="live-"+checkClientParam.getLive_room_id()+"-"+checkClientParam.getUser_id()+"-2";
		redisTemplate.opsForValue().set(key, "0");
		resultView.setCode(1000);
		resultView.setMessage("成功");
		
		return resultView;
	}
	
	public static void main(String[] args) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("key", "value");
		list.add(map);
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String dateString = formatter.format(date);
		 
		 System.out.println(dateString);
	}
	
	
}
