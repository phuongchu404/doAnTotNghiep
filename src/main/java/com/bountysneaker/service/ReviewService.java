package com.bountysneaker.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.Review;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.UserRequestReview;

public interface ReviewService {

	List<Review> findByProduct(Product product) throws Exception;

	public Boolean deleteById(String id) throws Exception;

	Review saveOrUpdate(Review review, User userLogin) throws Exception;

	Page<Review> findAll(UserRequestReview userRequestReview) throws Exception;
	
	Review findById(String id) throws Exception;
}
