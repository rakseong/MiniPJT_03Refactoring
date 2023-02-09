package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService {
	//Field
	private ProductDAO prodDAO;
	private PurchaseDAO dao;
	
	
	public PurchaseServiceImpl() throws Exception{
		prodDAO = new ProductDAO();
		dao = new PurchaseDAO();
	}

	@Override
	public Purchase addPurchase(Purchase purchase) throws Exception{
		dao.insertPurchase(purchase);
		return purchase;
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception{
		return dao.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception{
		return dao.getPurchaseList(search, userId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception{
		return null;
	}

	@Override
	public Purchase updatePurchase(Purchase purchase) throws Exception{
		dao.updatePurchase(purchase);
		return purchase;
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception{
		dao.updateTranCode(purchase);
	}

}
