package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int prodNo = Integer.parseInt(request.getParameter("productNo"));
		String menu = request.getParameter("menu");
		
		Search search = new Search();
		int currentPage = 1;
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		CommentService commentService = new CommentServiceImpl();
		Map<String, Object> map = commentService.getCommentList(search,prodNo);

		ProductService productService = new ProductServiceImpl();
		Product vo = productService.getProduct(prodNo);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(),
				pageUnit,pageSize);
		
		
		request.setAttribute("resultpage", resultPage);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("vo", vo);
		
		
		if(menu.equals("manage")) {
			return "forward:/updateProductView.do?productNo="+vo.getProdNo() +"&menu="+menu;
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
}
