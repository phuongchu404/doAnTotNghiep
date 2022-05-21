package com.bountysneaker.controller.user;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bountysneaker.controller.BaseController;
import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.RequestCancelOrder;
import com.bountysneaker.service.OrderService;
import com.bountysneaker.service.RequestCancelOrderService;

@Controller
@RequestMapping("/bounty-sneaker/")
public class RequestCancelOrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private RequestCancelOrderService requestCancelOrderService;

	@GetMapping("request-cancel-order")
	public ResponseEntity<Boolean> requestCancelOrder(@RequestParam("idOrder") String idOrder,
			@RequestParam("reason") String reason) throws Exception {
		Order saleOrder = orderService.findById(idOrder);
		RequestCancelOrder requestCancelOrder = new RequestCancelOrder();
		requestCancelOrder.setCreatedBy(saleOrder.getUserID());
		requestCancelOrder.setCustomerName(saleOrder.getCustomerName());
		requestCancelOrder.setEmail(saleOrder.getCustomerEmail());
		requestCancelOrder.setRequestType("hủy đơn hàng");
		requestCancelOrder.setMessage("Khách hàng gửi yêu cầu hủy đơn hàng có mã " + saleOrder.getCode());
		requestCancelOrder.setCreatedDate(Calendar.getInstance().getTime());
		requestCancelOrder.setStatus(false);
		requestCancelOrder.setOrder(saleOrder);
		requestCancelOrder.setReason(reason);
		requestCancelOrder.setProcessingStatus(false);
		requestCancelOrderService.saveOrUpdate(requestCancelOrder);
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
