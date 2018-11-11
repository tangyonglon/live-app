package com.douliao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douliao.controller.server.model.CommentParam;
import com.douliao.controller.server.model.SelCommentParam;
import com.douliao.mapper.CommentMapper;
import com.douliao.model.CommentInfo;
import com.douliao.model.database.Comments;
import com.douliao.result.ResultView;
import com.douliao.service.CommentService;
import com.douliao.util.TimeFormat;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public ResultView<String> commentVideo(CommentParam commentParam) {
		ResultView<String> resultView=new ResultView<String>();
		//评论视频
		//查询是否已评论过
		Comments comments=commentMapper.selComment(commentParam);
		if(comments!=null) {
			resultView.setCode(2029);
			resultView.setMessage("您已经评论过");
			return resultView;
		}
		commentParam.setCreate_time(TimeFormat.getSimple());
		int result=commentMapper.insComments(commentParam);
		if(result>0) {
			resultView.setCode(1000);
			resultView.setMessage("成功");
			return resultView;
		}else {
			resultView.setCode(2029);
			resultView.setMessage("您已经评论过");
			return resultView;
		}
	}

	@Override
	public ResultView<List<CommentInfo>> selCommentById(SelCommentParam selCommentParam) {
		ResultView<List<CommentInfo>> resultView=new ResultView<List<CommentInfo>>();
		int page=selCommentParam.getPage()>0?selCommentParam.getPage():1;
		int start=(page-1)*selCommentParam.getNumber();
		selCommentParam.setStart(start);
		List<CommentInfo> list=commentMapper.selCommentById(selCommentParam);
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
	
	
	
}
