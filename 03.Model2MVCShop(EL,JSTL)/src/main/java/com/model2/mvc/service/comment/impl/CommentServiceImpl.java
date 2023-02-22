package com.model2.mvc.service.comment.impl;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.dao.CommentDAO;
import com.model2.mvc.service.domain.Comment;

public class CommentServiceImpl implements CommentService {
	
	private CommentDAO commentDAO;

	public CommentServiceImpl() {
		commentDAO = new CommentDAO();
	}

	@Override
	public Comment addComment(Comment comment) throws Exception {
		commentDAO.insertComment(comment);
		return comment;
	}

	@Override
	public Comment getComment(int ComNo) throws Exception {
		return commentDAO.findComment(ComNo);
	}

	@Override
	public Map<String, Object> getCommentList(Search search, int prodNo) throws Exception {
		return commentDAO.getCommentList(search, prodNo);
	}

	@Override
	public Comment updateComment(Comment comment) throws Exception {
		commentDAO.updateCommentDetail(comment);
		return comment;
	}
	
	public List<Comment> getCommentByTranNo(int tranNo) throws Exception{
		return commentDAO.findCommentByTranNo(tranNo);
	}

}
