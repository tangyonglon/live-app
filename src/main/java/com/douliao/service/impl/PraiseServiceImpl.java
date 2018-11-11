package com.douliao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douliao.controller.server.model.PraiseParam;
import com.douliao.mapper.PraiseMapper;
import com.douliao.model.database.Live_app_user_info;
import com.douliao.model.database.Video_List;
import com.douliao.result.ResultView;
import com.douliao.service.PraiseService;

@Service
public class PraiseServiceImpl implements PraiseService {
//	@Autowired
//	private StringRedisTemplate redisTemplate;
	@Autowired
	private PraiseMapper praiseMapper;
	
	@Override
	public ResultView<String> praiseVideo(PraiseParam praiseParam) {
		ResultView<String> resultView=new ResultView<String>();
		Video_List video_List=praiseMapper.selVideoList(praiseParam);
		if(video_List!=null) {
			video_List.setSupport(video_List.getSupport()+1);
			int result=praiseMapper.countPrise(video_List);
			praiseMapper.insPraise(praiseParam);
			if(result>0) {
				resultView.setCode(1000);
				resultView.setMessage("成功");
			}else{
				resultView.setCode(2030);
				resultView.setMessage("点赞失败，系统繁忙，请稍后重试");
			}
		}else {
			resultView.setCode(1001);
			resultView.setMessage("暂无该数据");
		}
		
		
		return resultView;
	}
	
	/**
	 * 高并发写法 暂不考虑
	 * @param redisTemplate
	 * @param praiseParam
	 */
	public synchronized void setPraise(StringRedisTemplate redisTemplate,PraiseParam praiseParam) {
		String key="countvideo-"+praiseParam.getVideoId();
		boolean b=redisTemplate.hasKey(key);
		if(b) {
			//存在这个键
			if(praiseParam.getStatus()==1) {
				//点赞
				//1.点赞人数计数器
				redisTemplate.opsForHash().increment(key,"count", 1);
				//2.点赞用户ID
				int userId=praiseParam.getUserId();
			}
			if(praiseParam.getStatus()==2) {
				//取消赞
				//1.点赞人数计数器
				//2.点赞用户ID
				
			}
		}
	}
	
}
