package com.model2.mvc.view.comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;

public class UpdateCommentViewAction extends Action {

	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		CommentService commentService = new CommentServiceImpl();
		Comment comment = commentService.getCommentByTranNo(tranNo);
		request.setAttribute("comment", comment);
		
		return "forward:/comment/updateComment.jsp";
	}

}
