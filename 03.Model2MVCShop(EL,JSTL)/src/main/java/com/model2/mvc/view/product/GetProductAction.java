package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int prodNo = Integer.parseInt(request.getParameter("productNo"));
		String menu = request.getParameter("menu");
		
		ProductService productService = new ProductServiceImpl();
		Product vo=productService.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		
		if(menu.equals("manage")) {
			return "forward:/updateProductView.do?productNo="+vo.getProdNo() +"&menu="+menu;
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
}
