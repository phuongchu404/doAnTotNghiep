package com.bountysneaker.service;

import java.util.List;

import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.OrderDetail;

public interface DetailOrderService {

	public List<OrderDetail> findAllByOrder(Order order) throws Exception;

	public Boolean deleteById(Integer id) throws Exception;

}
