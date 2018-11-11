package com.douliao.service;

import java.util.List;

import com.douliao.controller.server.model.CommentParam;
import com.douliao.controller.server.model.SelCommentParam;
import com.douliao.model.CommentInfo;
import com.douliao.result.ResultView;

public interface CommentService {
	
	ResultView<String> commentVideo(CommentParam CommentParam);
	
	ResultView<List<CommentInfo>> selCommentById(SelCommentParam selCommentParam);
}
