package com.douliao.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.AuditParam;
import com.douliao.controller.server.model.DeleteParam;
import com.douliao.controller.server.model.VideoByUserIdParam;
import com.douliao.mapper.UploadMapper;
import com.douliao.model.VideoClips;
import com.douliao.model.database.Everyday_live_list;
import com.douliao.model.database.User_tv_info;
import com.douliao.model.database.VideoType;
import com.douliao.model.database.Video_List;
import com.douliao.model.database.Video_level_filter;
import com.douliao.result.ResultView;
import com.douliao.service.UploadService;
import com.douliao.util.Log4jUtil;

@Service
public class UploadServiceImpl implements UploadService{
	@Autowired
	private UploadMapper uploadMapper;

	@Override
	public int insVideo(Video_List video_List) {
		return uploadMapper.insVideo(video_List);
	}

	@Override
	public int updateHead(Map<String, Object> map) {
		return uploadMapper.updateHead(map);
	}

	@Override
	public Everyday_live_list isHasVideo(Video_List video_List) {
		return uploadMapper.isHasVideo(video_List);
	}

	@Override
	public int insEverydayVideo(Video_List video_List) {
		return uploadMapper.insEverydayVideo(video_List);
	}

	@Override
	public int updateEverydayVideo(Video_List video_List) {
		return uploadMapper.updateEverydayVideo(video_List);
	}

	@Override
	public Everyday_live_list isAudit(int userId) {
		return uploadMapper.isAudit(userId);
	}

	@Override
	public Everyday_live_list selOldData(AuditParam auditParam) {
		return uploadMapper.selOldData(auditParam);
	}

	@Override
	public int updateData(AuditParam auditParam) {
		return uploadMapper.updateData(auditParam);
	}

	@Override
	public int insUserTVInfo(Video_List video_List) {
		return uploadMapper.insUserTVInfo(video_List);
	}

	@Override
	public ResultView<List<VideoType>> selVideoType(int country_id) {
		ResultView<List<VideoType>> resultView=new ResultView<List<VideoType>>();
		List<VideoType> list=uploadMapper.selVideoType(country_id);
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
	public Everyday_live_list playAuditVideo(int userId) {
		return uploadMapper.playAuditVideo(userId);
	}

	@Override
	public User_tv_info hasTVInfo(Video_List video_List) {
		return uploadMapper.hasTVInfo(video_List);
	}

	@Override
	public int updateLiveAuth(Video_List video_List) {
		return uploadMapper.updateLiveAuth(video_List);
	}

	@Override
	public ResultView<String> deleteVideo(DeleteParam deleteParam) {
		ResultView<String> resultView=new ResultView<String>();
		int result=uploadMapper.updateVideo(deleteParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
		}else {
			resultView.setCode(2028);
			resultView.setMessage("删除失败");
		}
		return resultView;
	}

	@Override
	public ResultView<List<VideoClips>> selVideoByUserId(VideoByUserIdParam videoByUserIdParam) {
		ResultView<List<VideoClips>> resultView=new ResultView<List<VideoClips>>();
		int page=videoByUserIdParam.getPage()>0?videoByUserIdParam.getPage():1;
		int start=(page-1)*videoByUserIdParam.getNumber();
		videoByUserIdParam.setStart(start);
		Log4jUtil.info("渠道ID："+String.valueOf(videoByUserIdParam.getChannel_id()));
		if(videoByUserIdParam.getChannel_id()==0) {
			videoByUserIdParam.setChannel_id(0);
		}
		if(StringUtils.isNoneBlank(String.valueOf(videoByUserIdParam.getChannel_id()))) {
			Video_level_filter video_level_filter=uploadMapper.selVideoFilter(videoByUserIdParam);
			if(video_level_filter!=null) {
				videoByUserIdParam.setVideo_level(video_level_filter.getVideo_level().split(","));
			}
		}
		
		List<VideoClips> list=uploadMapper.selVideoByUserId(videoByUserIdParam);
		if(list.size()>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			resultView.setData(list);
		}else {
			resultView.setCode(1001);
			resultView.setMessage("失败");
		}
		return resultView;
	}

	@Override
	public double selProfit(int userId) {
		return uploadMapper.selProfit(userId);
	}
	
	
}
