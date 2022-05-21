package com.bountysneaker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bountysneaker.entities.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>, JpaSpecificationExecutor<Blog> {

	List<Blog> findTop5ByOrderByCreatedDateDescUpdatedDateDesc();

	Blog findBySeo(String seo);

	List<Blog> findByStatus(Boolean status);

	Page<Blog> findAll(Specification<Blog> spec, Pageable pageable);

}
