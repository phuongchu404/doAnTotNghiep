package com.bountysneaker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bountysneaker.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

	List<Category> findTop5ByStatus(Boolean status);

	Category findBySeo(String seo);

	List<Category> findByStatus(Boolean status);

	Page<Category> findAll(Specification<Category> spec, Pageable pageable);

	List<Category> findAllByStatus(Boolean status);

}
