package com.bountysneaker.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bountysneaker.entities.Order;
import com.bountysneaker.valueObjects.BestSaleProductVo;
import com.bountysneaker.valueObjects.OrderStatistical;
import com.bountysneaker.valueObjects.RevenueDate;
import com.bountysneaker.valueObjects.RevenueVo;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

	List<Order> findByUserIDOrderByCreatedDateDescUpdatedDateDesc(Integer userID);

	Order findBySeo(String seo);

	List<Order> findByStatusOrderByCreatedDateDescUpdatedDateDesc(Boolean status);

	Page<Order> findAll(Specification<Order> spec, Pageable pageable);

	List<Order> findByCustomerEmailAndCustomerPhoneAndCustomerNameOrderByCreatedDateDescUpdatedDateDesc(String email,
			String phone, String name);

	@Query("SELECT SUM(o.total) FROM Order o WHERE o.createdDate >= ?1 AND o.processingStatus = ?2")
	Double getRevenueOneWeek(Date startDate, Integer processStatus);

	@Query("SELECT SUM(o.total) FROM Order o WHERE o.createdDate BETWEEN ?1 AND ?2 AND o.processingStatus = ?3")
	Double getRevenueBetweenDate(Date startDate, Date endDate, Integer processStatus);

	@Query("SELECT new com.bountysneaker.valueObjects.RevenueDate(o.createdDate ,SUM(o.total)) FROM Order o WHERE o.createdDate >= ?1 "
			+ "AND o.processingStatus = ?2 " + "GROUP BY o.createdDate " + "ORDER BY o.createdDate DESC")
	List<RevenueDate> getRevenuePerDate(Date startDate, Integer processStatus);

	@Query("SELECT COUNT(o) FROM Order o WHERE o.createdDate >= ?1 AND o.processingStatus = ?2")
	Long getTotalOrderRecentMonth(Date startDate, Integer processStatus);

	@Query("SELECT COUNT(o) FROM Order o WHERE o.createdDate BETWEEN ?1 AND ?2 AND o.processingStatus = ?3")
	Long getTotalOrderBetweenDate(Date startDate, Date endDate, Integer processStatus);

	@Query("SELECT SUM(o.total) FROM Order o WHERE o.createdDate BETWEEN ?1 AND ?2 AND o.processingStatus = ?3")
	BigDecimal getRevenueDate(Date startDate, Date endDate, Integer processStatus);

	@Query("SELECT new com.bountysneaker.valueObjects.RevenueVo(o.createdDate, COUNT(o.id), "
			+ "COUNT(CASE WHEN o.processingStatus = 4 THEN o.id ELSE null END), SUM(d.quantity), "
			+ "SUM(CASE WHEN o.processingStatus = 3 THEN o.total ELSE 0 END) )"
			+ "FROM Order o JOIN o.orderDetails d JOIN d.attributeProduct a JOIN a.product "
			+ "WHERE o.createdDate BETWEEN ?1  AND ?2 GROUP BY o.createdDate")
	List<RevenueVo> getRevenue(Date startDate, Date endDate);

	@Query("SELECT new com.bountysneaker.valueObjects.BestSaleProductVo(p.avatar, p.title, SUM(d.quantity), SUM(d.price)) "
			+ "FROM Order o JOIN o.orderDetails d JOIN d.attributeProduct a JOIN a.product p JOIN p.category c "
			+ "WHERE c.id=?1 AND o.processingStatus=3 GROUP BY p.avatar, p.title")
	List<BestSaleProductVo> getBestSaleProductByCategory(Integer idCategory);

	@Query("SELECT new com.bountysneaker.valueObjects.BestSaleProductVo(p.avatar, p.title, SUM(d.quantity), SUM(d.price)) "
			+ "FROM Order o JOIN o.orderDetails d JOIN d.attributeProduct a JOIN a.product p JOIN p.category c WHERE o.processingStatus=3"
			+ "GROUP BY p.avatar, p.title")
	List<BestSaleProductVo> getAllBestSaleProduct();

	@Query("SELECT new com.bountysneaker.valueObjects.OrderStatistical(COUNT(CASE WHEN o.processingStatus = 3 THEN o.id ELSE null END),"
			+ "COUNT(CASE WHEN o.processingStatus = 4 THEN o.id ELSE null END)) FROM Order o")
	OrderStatistical getOrderStatistical();
}
