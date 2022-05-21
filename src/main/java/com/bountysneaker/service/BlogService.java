package com.bountysneaker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bountysneaker.entities.Blog;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.UserRequest;

public interface BlogService {

	Page<Blog> getListBlogByFilter(UserRequest userRequest) throws Exception;

	List<Blog> getRecentPost() throws Exception;

	List<Blog> findByStatus(Boolean status) throws Exception;

	Blog findById(String idBlog) throws Exception;

	Blog saveOrUpdate(Blog blog, MultipartFile avatar, User userLogin) throws Exception;

	Boolean deleteById(Integer id) throws Exception;

	Blog findBySeo(String seo) throws Exception;

}
