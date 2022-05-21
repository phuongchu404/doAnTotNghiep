package com.bountysneaker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bountysneaker.entities.Category;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.UserRequest;

public interface CategoryService {

	List<Category> getCategorySlider() throws Exception;

	Page<Category> findAllByUserRequest(UserRequest userRequest) throws Exception;

	Category saveOrUpdate(Category category, MultipartFile avatar, User userLogin) throws Exception;

	Category findBySeo(String seo) throws Exception;

	Boolean deleteById(Integer id) throws Exception;

	List<Category> findByStatus(Boolean status) throws Exception;

	Category findById(Integer id) throws Exception;

	List<Category> findAllByStatus(Boolean status) throws Exception;

}
