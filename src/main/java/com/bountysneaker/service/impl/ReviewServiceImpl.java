package com.bountysneaker.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.entities.OrderDetail;
import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.Review;
import com.bountysneaker.entities.User;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.OrderDetailRepository;
import com.bountysneaker.repository.ReviewRepository;
import com.bountysneaker.repository.UserRepository;
import com.bountysneaker.service.ReviewService;
import com.bountysneaker.specification.OrderDetailSpecification;
import com.bountysneaker.specification.ReviewSpecification;
import com.bountysneaker.utils.Validate;
import com.bountysneaker.valueObjects.CustomerOrder;
import com.bountysneaker.valueObjects.UserRequestReview;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderDetailSpecification orderDetailSpecification;

	@Autowired
	private ReviewSpecification reviewSpecification;

	@Autowired
	private GlobalConfig globalConfig;

	@Override
	public List<Review> findByProduct(Product product) throws Exception {
		if (product == null) {
			return new ArrayList<Review>();
		}
		return reviewRepository.findByProduct(product);
	}

	@Override
	public Boolean deleteById(String id) throws Exception {
		if (!Validate.isNumber(id)) {
			return false;
		}
		reviewRepository.deleteById(Integer.parseInt(id));
		return true;
	}

	@Override
	public Review saveOrUpdate(Review review, User userLogin) throws Exception {
		CustomerOrder customerOrder = null;
		if (userLogin != null) {
			customerOrder = new CustomerOrder(userLogin.getFullname(), userLogin.getAddress(), userLogin.getPhone(),
					userLogin.getEmail());
			if (review.getId() == null) {
				review.setCreatedBy(userLogin.getId());
				review.setCreatedDate(Calendar.getInstance().getTime());
				review.setCustomerAddress(userLogin.getAddress());
				review.setCustomerName(userLogin.getFullname());
				review.setCustomerEmail(userLogin.getEmail());
				review.setCustomerPhone(userLogin.getPhone());
				review.setStatus(false);
			} else {
				Review oldReview = reviewRepository.findById(review.getId())
						.orElseThrow(() -> new EntityNotFoundCustomException("Not found review"));
				review.setCreatedBy(oldReview.getCreatedBy());
				review.setCreatedDate(oldReview.getCreatedDate());
			}
			review.setUpdatedBy(userLogin.getId());
			if (!userLogin.getUserRoles().get(0).getRoleName().equals("ADMIN_S")) {
				review.setUser(userLogin);
			} else {
				review.setUser(userRepository.findByEmail(review.getCustomerEmail()));
			}

		} else {
			review.setCreatedDate(Calendar.getInstance().getTime());
			customerOrder = new CustomerOrder(review.getCustomerName(), review.getCustomerAddress(),
					review.getCustomerPhone(), review.getCustomerEmail());
		}
		List<OrderDetail> orderDetails = orderDetailRepository
				.findAll(orderDetailSpecification.findByCustomerOrder(customerOrder, review.getProduct()));
		if (orderDetails.size() == 0) {
			return null;
		}
		if (orderDetails.get(0).getOrder().getUserID() != null && orderDetails.get(0).getOrder().getUserID() != 0
				&& userLogin == null) {
			review.setUser(userRepository.findById(orderDetails.get(0).getOrder().getUserID())
					.orElseThrow(() -> new EntityNotFoundCustomException("Not found User")));
		}
		review.setUpdatedDate(Calendar.getInstance().getTime());
		return reviewRepository.save(review);
	}

	@Override
	public Page<Review> findAll(UserRequestReview req) throws Exception {
		Integer statusVal = null;
		if (Validate.isNumber(req.getStatus())) {
			statusVal = Integer.parseInt(req.getStatus());
		}
		if (req.getCurrentPage() == null) {
			req.setCurrentPage(globalConfig.getInitPage());
		}
		Pageable pageable = PageRequest.of(req.getCurrentPage() - 1, req.getSizeOfPage(),
				Sort.by("createdDate", "updatedDate").descending());
		return reviewRepository.findAll(reviewSpecification.findAll(req.getKeySearch(), statusVal), pageable);
	}

	@Override
	public Review findById(String id) throws Exception {
		if (!Validate.isNumber(id)) {
			return null;
		}
		return reviewRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found review"));
	}
}
