package com.bountysneaker.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.slugify.Slugify;
import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.entities.Blog;
import com.bountysneaker.entities.User;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.BlogRepository;
import com.bountysneaker.service.BlogService;
import com.bountysneaker.specification.BlogSpecification;
import com.bountysneaker.utils.Constants;
import com.bountysneaker.utils.Validate;
import com.bountysneaker.valueObjects.UserRequest;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private BlogSpecification blogSprecification;

	@Override
	public Blog saveOrUpdate(Blog blog, MultipartFile avatar, User userLogin) throws Exception {
		Integer idUserLogin = userLogin != null ? userLogin.getId() : null;
		blog.setSeo(new Slugify().slugify(blog.getName()));
		blog.setUpdatedDate(Calendar.getInstance().getTime());
		blog.setUpdatedBy(idUserLogin);
		if (blog.getId() != null) {
			Blog oldBlog = blogRepository.getOne(blog.getId());
			blog.setCreatedDate(oldBlog.getCreatedDate());
			blog.setCreatedBy(oldBlog.getCreatedBy());
			if (!Validate.isEmptyUploadFile(avatar)) {
				new File(globalConfig.getUploadRootPath() + oldBlog.getAvatar()).delete();
				avatar.transferTo(new File(globalConfig.getUploadRootPath() + "blog/" + avatar.getOriginalFilename()));
				blog.setAvatar("blog/" + avatar.getOriginalFilename());
			} else {
				blog.setAvatar(oldBlog.getAvatar());
			}
			blog.setStatus(oldBlog.getStatus());
			blog.setIsHot(oldBlog.getIsHot());
		} else {
			blog.setCreatedDate(Calendar.getInstance().getTime());
			blog.setCreatedBy(idUserLogin);
			if (!Validate.isEmptyUploadFile(avatar)) {
				avatar.transferTo(new File(globalConfig.getUploadRootPath() + "blog/" + avatar.getOriginalFilename()));
				blog.setAvatar("blog/" + avatar.getOriginalFilename());
			}
			blog.setStatus(true);
		}
		return blogRepository.save(blog);
	}

	@Override
	public Page<Blog> getListBlogByFilter(UserRequest userRequest) throws Exception {
		if (userRequest.getKeySearch() == null) {
			userRequest.setKeySearch(Constants.STR_EMPTY);
		}
		Pageable pageable = PageRequest.of(userRequest.getCurrentPage() - 1, userRequest.getSizeOfPage(),
				Sort.by("createdDate", "updatedDate").descending());
		return blogRepository.findAll(blogSprecification.findBlog(userRequest), pageable);
	}

	@Override
	public List<Blog> getRecentPost() throws Exception {
		return blogRepository.findTop5ByOrderByCreatedDateDescUpdatedDateDesc();
	}

	@Override
	public Blog findBySeo(String seo) throws Exception {
		if (seo == null || seo.trim() == Constants.STR_EMPTY) {
			return null;
		}
		return blogRepository.findBySeo(seo);
	}

	@Override
	public List<Blog> findByStatus(Boolean status) throws Exception {
		if (status == null) {
			return blogRepository.findAll();
		}
		return blogRepository.findByStatus(status);
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found blog has id: " + id));
		new File(globalConfig.getUploadRootPath() + blog.getAvatar()).delete();
		blogRepository.deleteById(id);
		return true;
	}

	@Override
	public Blog findById(String idBlog) throws Exception {
		if (!Validate.isNumber(idBlog)) {
			return null;
		}
		return blogRepository.findById(Integer.parseInt(idBlog))
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found blog"));
	}

}
