package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {
	//Field
	private ProductDAO productDao;
	
	//Constructor
	public ProductServiceImpl() {
		productDao = new ProductDAO();
	}

	//Method
	@Override
	public Product addProduct(Product product) throws Exception{
		productDao.insertProduct(product);
		return product;
	}

	@Override
	public Product getProduct(int productNo) throws Exception{
		return productDao.findProduct(productNo);
	}

	@Override
	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDao.getProductList(search);
	}

	@Override
	public Product updateProduct(Product product) throws Exception{
		productDao.updateProduct(product);
		return product;
	}

}
