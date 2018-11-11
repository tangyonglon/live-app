package com.douliao.mapper;

import java.util.List;

import com.douliao.controller.server.model.CommentParam;
import com.douliao.controller.server.model.SelCommentParam;
import com.douliao.model.CommentInfo;
import com.douliao.model.database.Comments;

public interface CommentMapper {
	
	Comments selComment(CommentParam CommentParam);
	
	int insComments(CommentParam CommentParam);
	
	List<CommentInfo> selCommentById(SelCommentParam selCommentParam);
	
}
