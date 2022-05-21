package com.bountysneaker.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.bountysneaker.entities.AttributeProduct;
import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.OrderDetail;
import com.bountysneaker.entities.Product;
import com.bountysneaker.valueObjects.CustomerOrder;

@Component
public class OrderDetailSpecification {

	public Specification<OrderDetail> findByCustomerOrder(CustomerOrder customerOrder, Product product) {
		return (root, query, builder) -> {
			Join<OrderDetail, Order> joinOrderDetail = root.join("order");
			Join<OrderDetail, AttributeProduct> joinAttrDetail = root.join("attributeProduct");
			Join<AttributeProduct, Product> joinProduct = joinAttrDetail.join("product");
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(joinOrderDetail.get("customerName"), customerOrder.getName()));
			predicates.add(builder.equal(joinOrderDetail.get("customerPhone"), customerOrder.getPhone()));
			predicates.add(builder.equal(joinOrderDetail.get("customerEmail"), customerOrder.getEmail()));
			if (customerOrder.getAddress() != null) {
				predicates.add(builder.equal(joinOrderDetail.get("customerAddress"), customerOrder.getAddress()));
			}
			predicates.add(builder.equal(joinProduct.get("id"), product.getId()));
			return builder.and(predicates.toArray(new Predicate[] {}));
		};
	}

}
