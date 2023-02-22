package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class AddPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(new ProductServiceImpl().getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setBuyer(new UserServiceImpl().getUser(request.getParameter("buyerId")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		request.setAttribute("purchase", purchaseService.addPurchase(purchase));
		
		return  "forward:/purchase/addPurchase.jsp";
	}
}
