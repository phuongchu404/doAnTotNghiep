package com.bountysneaker.service;

import java.util.List;

import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.ProductImage;

public interface ProductImageService {

	List<ProductImage> findByProduct(Product product);

	public Boolean deleteById(Integer id) throws Exception;

}
