package com.model2.mvc.service.domain;

import java.sql.Date;

public class Comment {
	//Filed
	private Purchase purchase;
	private int commentId;
	private String commentDetail;
	private Date commentRegDate;
	private int prodGrade;
	private int prodNo;
	private String userId;
	
	//Constructor
	public Comment() {
	}
	
	//Method
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public Date getCommentRegDate() {
		return commentRegDate;
	}

	public void setCommentRegDate(Date commentRegDate) {
		this.commentRegDate = commentRegDate;
	}

	public int getProdGrade() {
		return prodGrade;
	}

	public void setProdGrade(int prodGrade) {
		this.prodGrade = prodGrade;
	}


	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Comment [purchase=" + purchase + ", commentId=" + commentId + ", commentDetail=" + commentDetail
				+ ", commentRegDate=" + commentRegDate + ", prodGrade=" + prodGrade + ", prodNo=" + prodNo + ", userId="
				+ userId + "]";
	}

}
