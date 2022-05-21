package com.bountysneaker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.CategoryQuantityProduct;
import com.bountysneaker.valueObjects.UserRequestToProduct;

public interface ProductService {

	List<Product> findTop8NewProduct() throws Exception;

	List<Product> findTop8ProductHot() throws Exception;

	Product saveOrUpdate(Product product, MultipartFile avatar, MultipartFile[] images, User userLogin)
			throws Exception;

	Product saveOrUpdate(Product product, User userLogin) throws Exception;

	Page<Product> findByUserRequestToProduct(UserRequestToProduct request) throws Exception;

	Product findBySeo(String seo) throws Exception;

	Boolean deleteById(String idProduct) throws Exception;

	List<Product> findByStatus(Boolean status) throws Exception;

	Product findById(String id) throws Exception;

	Long getTotalProduct() throws Exception;

	List<CategoryQuantityProduct> getTotalByCategory() throws Exception;
}
