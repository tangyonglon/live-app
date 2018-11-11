package com.douliao.controller.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douliao.controller.server.model.CommentParam;
import com.douliao.controller.server.model.SelCommentParam;
import com.douliao.model.CommentInfo;
import com.douliao.result.ResultView;
import com.douliao.service.CommentService;
import com.douliao.util.Log4jUtil;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	//评论视频接口
	//查询某个视频的评论接口
	
	/**
	 * 评论视频接口
	 * @param CommentParam
	 * @return
	 */
	@RequestMapping(value="/comment/commentVideo",method=RequestMethod.POST)
	public ResultView<String> comment(CommentParam CommentParam) {
		ResultView<String> resultView=commentService.commentVideo(CommentParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
	/**
	 * 分页查询某个视频的评论信息
	 * @param selCommentParam
	 * @return
	 */
	@RequestMapping(value="/comment/selCommentById",method=RequestMethod.POST)
	public ResultView<List<CommentInfo>> selCommentById(SelCommentParam selCommentParam){
		ResultView<List<CommentInfo>> resultView=commentService.selCommentById(selCommentParam);
		Log4jUtil.info(resultView.toString());
		return resultView;
	}
	
}
