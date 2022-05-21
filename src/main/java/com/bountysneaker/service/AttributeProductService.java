package com.bountysneaker.service;

import java.util.List;

import com.bountysneaker.entities.AttributeProduct;
import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.User;

public interface AttributeProductService {

	public List<AttributeProduct> findAllByProduct(Product product) throws Exception;

	public Boolean deleteById(Integer id) throws Exception;

	AttributeProduct findById(Integer id) throws Exception;

	AttributeProduct saveOrUpdate(AttributeProduct attributeProduct, User userLogin) throws Exception;

}
