package com.bountysneaker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bountysneaker.entities.Product;
import com.bountysneaker.valueObjects.CategoryQuantityProduct;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	List<Product> findTop8ByOrderByCreatedDateDesc();

	List<Product> findTop8ByIsHotOrderByCreatedDateDesc(Boolean isHot);

	Product findBySeo(String seo);

	List<Product> findByStatus(Boolean status);

	Page<Product> findAll(Specification<Product> spec, Pageable pageable);

	@Query("SELECT new com.bountysneaker.valueObjects.CategoryQuantityProduct(c.name, COUNT(p.id)) FROM Category c LEFT JOIN c.products p GROUP BY c.name")
	List<CategoryQuantityProduct> getQuantityByCategory();
}
