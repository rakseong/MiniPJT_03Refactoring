package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;

public class PurchaseDAO{
	//Method
	public Purchase findPurchase(int tranNo) throws Exception {
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM transaction where TRAN_NO=?");
		pstmt.setInt(1, tranNo);
		ResultSet rs = pstmt.executeQuery();
		
		Purchase purchase = new Purchase();
		
		while(rs.next()) {
			purchase.setTranNo(tranNo);
			purchase.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(new UserDao().findUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));   
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
		}
		con.close();
		
		return purchase;
	}
	
	public Map<String, Object> getPurchaseList(Search search, String userId)throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM transaction WHERE buyer_id='"+userId+"'";
		int total = getTotalCount(sql);
		
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();

		
		System.out.println("로우의 수 : "+total);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("count", total);
		
		//rs.absolute((searchVO.getPage()-1)*searchVO.getPageUnit()+1);
		ArrayList<Purchase> list = new ArrayList<Purchase>();
		
		if(total != 0) {
			while(rs.next()) {
				Purchase pvo = new Purchase();
				pvo.setTranNo(rs.getInt("TRAN_NO"));
				pvo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("PROD_NO")));
				pvo.setBuyer(new UserDao().findUser(rs.getString("BUYER_ID")));
				pvo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				pvo.setReceiverName(rs.getString("RECEIVER_NAME"));   
				pvo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				pvo.setDivyAddr(rs.getString("DEMAILADDR"));
				pvo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				pvo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				pvo.setOrderDate(rs.getDate("ORDER_DATA"));
				pvo.setDivyDate(rs.getString("DLVY_DATE"));
				list.add(pvo);
			}
		}
		map.put("list", list);
		
		con.close();
		return map;
	}
	
	//??넌 뭐하는 친구니
	public Map<String, Object> getSaleList(Purchase purchase)throws Exception{
		Connection con = DBUtil.getConnection();
		
		
		con.close();
		return null;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO transaction"
				+ " VALUES(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,TO_DATE(?,'yyyy/mm/dd'))");
		pstmt.setInt(1,purchase.getPurchaseProd().getProdNo());
		pstmt.setString(2,purchase.getBuyer().getUserId());
		pstmt.setString(3,purchase.getPaymentOption());
		pstmt.setString(4,purchase.getReceiverName());
		pstmt.setString(5,purchase.getReceiverPhone());
		pstmt.setString(6,purchase.getDivyAddr());
		pstmt.setString(7,purchase.getDivyRequest());
		pstmt.setString(8,purchase.getTranCode());
		pstmt.setString(9,purchase.getDivyDate());
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public void updatePurchase(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("UPDATE transaction SET "
				+ "PAYMENT_OPTION =?,RECEIVER_NAME =?, "
				+ "RECEIVER_PHONE =?,DEMAILADDR =?,DLVY_REQUEST =?, "
				+ "DLVY_DATE =TO_DATE(?,'yyyy/mm/dd') WHERE TRAN_NO=?");
		pstmt.setString(1,purchase.getPaymentOption());
		pstmt.setString(2,purchase.getReceiverName());
		pstmt.setString(3,purchase.getReceiverPhone());
		pstmt.setString(4,purchase.getDivyAddr());
		pstmt.setString(5,purchase.getDivyRequest());
		pstmt.setString(6,purchase.getDivyDate());
		pstmt.setInt(7,purchase.getTranNo());
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("UPDATE transaction SET "
				+ "TRAN_STATUS_CODE =? WHERE TRAN_NO=?");
		pstmt.setString(1,purchase.getTranCode());
		pstmt.setInt(2, purchase.getTranNo());	
		pstmt.executeUpdate();	
		con.close();
	}
	
	public void updateTranCodeByProd(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		System.out.println(purchase.getTranNo());
		System.out.println(purchase.getPurchaseProd().getProdNo());
		PreparedStatement pstmt = con.prepareStatement("UPDATE transaction SET TRAN_STATUS_CODE =? "
				+ " WHERE prod_no=?");
		pstmt.setString(1, purchase.getTranCode());
		pstmt.setInt(2,purchase.getPurchaseProd().getProdNo());
		int i = pstmt.executeUpdate();	
		if(i==1)
			System.out.println("동작완료");
		con.close();
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
