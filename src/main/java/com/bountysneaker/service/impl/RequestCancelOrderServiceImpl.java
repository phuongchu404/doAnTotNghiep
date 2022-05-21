package com.bountysneaker.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.RequestCancelOrder;
import com.bountysneaker.entities.User;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.RequestCancelOrderRepository;
import com.bountysneaker.service.RequestCancelOrderService;
import com.bountysneaker.utils.Validate;
import com.bountysneaker.valueObjects.UserRequest;

@Service
@Transactional
public class RequestCancelOrderServiceImpl implements RequestCancelOrderService {

	@Autowired
	private RequestCancelOrderRepository requestRepository;

	public List<RequestCancelOrder> findAllRequestCancelOrder() throws Exception {
		return requestRepository.findAllByOrderByCreatedDateDesc();
	}

	public List<RequestCancelOrder> getUnreadNotify() throws Exception {
		return requestRepository.findByStatus(false);
	}

	public Integer countUnreadNotify() throws Exception {
		return requestRepository.countByStatus(false);
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		requestRepository.deleteById(id);
		return true;
	}

	@Override
	public RequestCancelOrder saveOrUpdate(RequestCancelOrder requestCancelOrder) throws Exception {
		return requestRepository.save(requestCancelOrder);
	}

	@Override
	public RequestCancelOrder saveOrUpdate(RequestCancelOrder requestCancelOrder, User userLogin) throws Exception {
		Integer idUserLogin = userLogin != null ? userLogin.getId() : null;
		if (requestCancelOrder.getId() != null) {
			RequestCancelOrder oldRequest = requestRepository.findById(requestCancelOrder.getId())
					.orElseThrow(() -> new EntityNotFoundCustomException("Not found request cancel order"));
			requestCancelOrder.setCreatedBy(oldRequest.getCreatedBy());
			requestCancelOrder.setCreatedDate(oldRequest.getCreatedDate());
		} else {
			requestCancelOrder.setCreatedBy(idUserLogin);
			requestCancelOrder.setCreatedDate(Calendar.getInstance().getTime());
		}
		requestCancelOrder.setUpdatedBy(idUserLogin);
		requestCancelOrder.setUpdatedDate(Calendar.getInstance().getTime());
		return requestRepository.save(requestCancelOrder);
	}

	@Override
	public List<RequestCancelOrder> findAll() throws Exception {
		return requestRepository.findAll();
	}

	@Override
	public RequestCancelOrder findById(String id) throws Exception {
		if (!Validate.isNumber(id)) {
			return new RequestCancelOrder();
		}
		return requestRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found request cancel order"));
	}

	@Override
	public RequestCancelOrder findByOrder(Order order) throws Exception {
		if (order == null || order.getId() == null) {
			return null;
		}
		return requestRepository.findByOrder(order);
	}

	@Override
	public Boolean deleteById(String idRequest) throws Exception {
		if (!Validate.isNumber(idRequest)) {
			return false;
		}
		requestRepository.deleteById(Integer.parseInt(idRequest));
		return true;
	}

	@Override
	public Page<RequestCancelOrder> findByUserRequest(UserRequest userRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
