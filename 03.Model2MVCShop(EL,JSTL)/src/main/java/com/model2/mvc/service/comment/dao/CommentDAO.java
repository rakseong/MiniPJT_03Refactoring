package com.model2.mvc.service.comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.user.dao.UserDao;

public class CommentDAO {
	
	public void insertComment(Comment comment) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO comments "
				+ " VALUES(seq_comment_comment_no.nextval,"
				+ " ?,?,sysdate,?,?,?)");
		pstmt.setInt(1,comment.getPurchase().getTranNo());
		pstmt.setString(2,comment.getCommentDetail());
		pstmt.setInt(3,comment.getProdGrade());
		pstmt.setInt(4,comment.getProdNo());
		pstmt.setString(5,comment.getUserId());
		
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public Comment findComment(int ComNo) throws Exception {
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM comments WHERE COMMENT_ID=?");
		pstmt.setInt(1, ComNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		Comment comment = new Comment();
		
		while(rs.next()) {
			comment.setCommentId(ComNo);
			comment.setCommentDetail(rs.getString("COMMENT_DETAIL"));
			comment.setCommentRegDate(rs.getDate("COMMENT_REG_DATE"));
			comment.setProdGrade(rs.getInt("PROD_GRADE"));
			comment.setPurchase(new PurchaseDAO().findPurchase(rs.getInt("TRAN_NO")));
			comment.setProdNo(rs.getInt("PROD_NO"));
			comment.setUserId(rs.getString("USER_ID"));
		}
		con.close();
		return comment;
	}
	
	public void updateCommentDetail(Comment comment) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("UPDATE comments SET COMMENT_DETAIL=?,PROD_GRADE=?  WHERE COMMENT_ID = ?");
		pstmt.setString(1,comment.getCommentDetail());
		pstmt.setInt(2, comment.getProdGrade());
		pstmt.setInt(3, comment.getCommentId());
		pstmt.executeUpdate();
		
		con.close();
	}
	
	
	public Map<String , Object> getCommentList(Search search, int prodNo) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "SELECT *  FROM  comments WHERE PROD_NO="+prodNo+" ORDER BY COMMENT_ID";
		
		System.out.println("CommentDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("CommentDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Comment> list = new ArrayList<Comment>();
		
		while(rs.next()){
			Comment comment = new Comment();
			comment.setCommentId(rs.getInt("COMMENT_ID"));
			comment.setCommentDetail(rs.getString("COMMENT_DETAIL"));
			comment.setCommentRegDate(rs.getDate("COMMENT_REG_DATE"));
			comment.setProdGrade(rs.getInt("PROD_GRADE"));
			comment.setPurchase(new PurchaseDAO().findPurchase(rs.getInt("TRAN_NO")));
			comment.setProdNo(rs.getInt("PROD_NO"));
			comment.setUserId(rs.getString("USER_ID"));
			
			list.add(comment);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	//전체 record 개수 조회
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		//게시판 current page에 맞는 record만 조회
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			return sql;
		}
	
}
