package com.bountysneaker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bountysneaker.entities.CategoryBlog;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.UserRequest;

public interface CategoryBlogService {

	List<CategoryBlog> getCategorySlider() throws Exception;

	Page<CategoryBlog> findAllByUserRequest(UserRequest userRequest) throws Exception;

	Optional<CategoryBlog> findById(String id) throws Exception;

	Boolean deleteById(Integer idCategory) throws Exception;

	public CategoryBlog saveOrUpdate(CategoryBlog category, MultipartFile avatar, User userLogin) throws Exception;

	public List<CategoryBlog> findByStatus(Boolean status) throws Exception;

	public CategoryBlog findBySeo(String seo) throws Exception;
}
