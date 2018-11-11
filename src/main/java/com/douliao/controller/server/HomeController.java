package com.douliao.controller.server;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.VideoClipsParam;
import com.douliao.model.HotList;
import com.douliao.model.NewList;
import com.douliao.model.VideoClips;
import com.douliao.model.response.PlayVideoInfo;
import com.douliao.result.ResultView;
import com.douliao.service.LiveService;
import com.douliao.util.Log4jUtil;

@RestController
public class HomeController {
	@Autowired
	private LiveService liveService;
	
	/**
	 * 新人列表
	 * @param videoClipsParam
	 * @return
	 */
	@RequestMapping(value="/home/newVideo",method=RequestMethod.POST)
	public ResultView<List<NewList>> newVideo(VideoClipsParam videoClipsParam) {
		//热门主播 关注数排序
		ResultView<List<NewList>> resultView=liveService.selNewList(videoClipsParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 热门列表
	 * @param videoClipsParam
	 * @return
	 */
	@RequestMapping(value="/home/hotVideo",method=RequestMethod.POST)
	public ResultView<List<HotList>> hotVideo(VideoClipsParam videoClipsParam) {
		//热门主播 关注数排序
		ResultView<List<HotList>> resultView=liveService.selHotList(videoClipsParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
	/**
	 * 显示视频
	 * @param videoClipsParam
	 * @return
	 */
	@RequestMapping(value="/home/videoClips",method=RequestMethod.POST)
	public ResultView<List<VideoClips>> videoClips(VideoClipsParam videoClipsParam) {
		ResultView<List<VideoClips>> resultView=liveService.selVideoClips(videoClipsParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 播放小视频
	 * @param userId
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/home/playVideo",method=RequestMethod.POST)
	public ResultView<PlayVideoInfo> playVideo(int userId,int id) {
		ResultView<PlayVideoInfo> resultView=new ResultView<PlayVideoInfo>();
		if(StringUtils.isNoneBlank(String.valueOf(userId)) && StringUtils.isNoneBlank(String.valueOf(id))) {
			resultView=liveService.playVideo(userId,id);
		}else {
			resultView.setCode(2010);
			resultView.setMessage("请检查提交的参数");
		}
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	
}
