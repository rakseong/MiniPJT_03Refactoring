package com.model2.mvc.service.comment;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Comment;

public interface CommentService {
	
	public Comment addComment(Comment comment) throws Exception;
	
	public Comment getComment(int comNo) throws Exception;
	
	public List<Comment> getCommentByTranNo(int tranNo) throws Exception;
	
	public Map<String, Object> getCommentList(Search search,int prodNo) throws Exception;
	
	public Comment updateComment(Comment comment) throws Exception;

}
