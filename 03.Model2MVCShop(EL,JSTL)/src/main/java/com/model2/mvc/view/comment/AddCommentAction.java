package com.model2.mvc.view.comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddCommentAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		Comment comment = new Comment();
		
		comment.setProdGrade(Integer.parseInt(request.getParameter("prodGrade")));
		comment.setCommentDetail(request.getParameter("commentDetail"));
		comment.setPurchase(new PurchaseServiceImpl().getPurchase(Integer.parseInt(request.getParameter("tranNo"))));
		comment.setProdNo(prodNo);
		comment.setUserId(comment.getPurchase().getBuyer().getUserId());
		
		CommentService commentService = new CommentServiceImpl();
		commentService.addComment(comment);
		
		request.setAttribute("comment", comment);
		
		return "forward:/getProduct.do?productNo="+prodNo+"&menu=search";
	}
}
