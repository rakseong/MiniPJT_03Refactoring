package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

import oracle.sql.DATE;

public class ProductDAO {
	
	//Constructor
	public ProductDAO(){
	}
	
	//Method
	//상품 검색
	public Product findProduct(int productNo) throws Exception{
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM product where PROD_NO=?");
		pstmt.setInt(1,productNo);
		ResultSet rs = pstmt.executeQuery();
		
		Product product = new Product();
		while(rs.next()) {
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		con.close();
		
		return product;
	}
	
	//상품 리스트 반환
	public Map<String,Object> getProductList(Search search) throws Exception{
		Connection con = DBUtil.getConnection();
		System.out.println("정상동작");
		String sql = "SELECT p.*,NVL(t.tran_status_code,0) transaction_tran_code from product p, transaction t WHERE p.prod_no = t.prod_no(+) ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " AND prod_no LIKE '%" + search.getSearchKeyword()+"%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " AND prod_name LIKE '%" + search.getSearchKeyword()+"%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " AND price LIKE '%" + search.getSearchKeyword()+"%'";
			}
		}
		sql += " ORDER BY p.prod_no";
//		sql = "SELECT p.*,pc.*, NVL(t.tran_status_code,0) transaction_tran_code "
//				+ " FROM (select ROWNUM num, product.* from product "+sqlplus+") p, "
//						+ "(select COUNT(*) total from product "+sqlplus+") pc ,transaction t "
//				+ " where p.prod_no = t.prod_no(+) AND num BETWEEN "+startPage+" AND "+(startPage+search.getPageSize()-1)+" order by p.PROD_NO";

		int total = getTotalCount(sql);
		
		sql = makeCurrentPageSql(sql,search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		System.out.println("로우의 수 : "+total);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("count", total);
		
		//rs.absolute((searchVO.getPage()-1)*searchVO.getPageUnit()+1);
		ArrayList<Product> list = new ArrayList<Product>();
		
		if(total != 0) {
			while(rs.next()) {
				Product pvo = new Product();
				pvo.setProdNo(rs.getInt("PROD_NO"));
				pvo.setProdName(rs.getString("PROD_NAME"));
				pvo.setProdDetail(rs.getString("PROD_DETAIL"));
				System.out.println(rs.getString("MANUFACTURE_DAY").substring(0,4)+"-"+rs.getString("MANUFACTURE_DAY").substring(4,6)+"-"+rs.getString("MANUFACTURE_DAY").substring(6));
				pvo.setManuDate(rs.getString("MANUFACTURE_DAY").substring(0,4)+"-"+rs.getString("MANUFACTURE_DAY").substring(4,6)+"-"+rs.getString("MANUFACTURE_DAY").substring(6));
				pvo.setPrice(rs.getInt("PRICE"));
				pvo.setFileName(rs.getString("IMAGE_FILE"));
				pvo.setRegDate(rs.getDate("REG_DATE"));
				pvo.setProTranCode(rs.getString("TRANSACTION_TRAN_CODE"));
				list.add(pvo);
			}
			
		}
		map.put("list", list);
		
		con.close();
		
		return map;
	}
	
	//상품등록
	public void insertProduct(Product product) throws Exception {
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO product "
				+ " VALUES(seq_product_prod_no.nextval,?,?,"
				+ " ?,?,?,sysdate)");
		String[] manuDate = product.getManuDate().split("[-]");
		pstmt.setString(1,product.getProdName());
		pstmt.setString(2,product.getProdDetail());
		pstmt.setString(3,manuDate[0]+manuDate[1]+manuDate[2]);
		pstmt.setInt(4,product.getPrice());
		pstmt.setString(5,product.getFileName());
		pstmt.executeUpdate();
		
		con.close();
	}
	
	//상품정보 수정
	public void updateProduct(Product product) throws Exception{
		Connection con = DBUtil.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("UPDATE product SET"
				+ " PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=?"
				+ " WHERE PROD_NO = ?");
		pstmt.setString(1, product.getProdName());
		pstmt.setString(2, product.getProdDetail());
		pstmt.setString(3, product.getManuDate());
		pstmt.setInt(4, product.getPrice());
		pstmt.setString(5, product.getFileName());
		pstmt.setInt(6, product.getProdNo());
		
		pstmt.executeUpdate();
		
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
