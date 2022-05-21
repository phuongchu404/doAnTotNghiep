package com.bountysneaker.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.RequestCancelOrder;
import com.bountysneaker.entities.User;
import com.bountysneaker.valueObjects.UserRequest;

public interface RequestCancelOrderService {

	List<RequestCancelOrder> findAllRequestCancelOrder() throws Exception;

	List<RequestCancelOrder> getUnreadNotify() throws Exception;

	Integer countUnreadNotify() throws Exception;

	RequestCancelOrder saveOrUpdate(RequestCancelOrder requestCancelOrder) throws Exception;

	RequestCancelOrder saveOrUpdate(RequestCancelOrder requestCancelOrder, User userLogin) throws Exception;

	public Boolean deleteById(Integer id) throws Exception;

	List<RequestCancelOrder> findAll() throws Exception;

	RequestCancelOrder findById(String id) throws Exception;

	RequestCancelOrder findByOrder(Order order) throws Exception;

	Boolean deleteById(String idRequest) throws Exception;

	Page<RequestCancelOrder> findByUserRequest(UserRequest userRequest) throws Exception;

}
