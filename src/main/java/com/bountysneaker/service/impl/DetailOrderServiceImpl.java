package com.bountysneaker.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.OrderDetail;
import com.bountysneaker.repository.DetailOrderRepository;
import com.bountysneaker.service.DetailOrderService;

@Service
@Transactional
public class DetailOrderServiceImpl implements DetailOrderService {

	@Autowired
	private DetailOrderRepository detailOrderRepository;

	@Override
	public List<OrderDetail> findAllByOrder(Order order) throws Exception {
		if (order == null || order.getId() == null) {
			return new ArrayList<OrderDetail>();
		}
		return findAllByOrder(order);
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		detailOrderRepository.deleteById(id);
		return true;
	}
}
