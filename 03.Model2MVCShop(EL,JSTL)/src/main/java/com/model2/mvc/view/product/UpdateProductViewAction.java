package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) 
														throws Exception{
		int prodNo = Integer.parseInt(request.getParameter("productNo"));
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		request.setAttribute("product", product);
		request.setAttribute("menu", request.getParameter("menu"));
		
		return "forward:/product/updateProduct.jsp";
	}
	
}
