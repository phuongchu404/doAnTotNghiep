package com.bountysneaker.controller.manager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.controller.BaseController;
import com.bountysneaker.dto.MappingModel;
import com.bountysneaker.entities.AttributeProduct;
import com.bountysneaker.entities.Order;
import com.bountysneaker.entities.OrderDetail;
import com.bountysneaker.service.AttributeProductService;
import com.bountysneaker.service.OrderService;
import com.bountysneaker.utils.ConvertUtils;

@Controller
@RequestMapping("/bounty-sneaker/")
public class ManagerOrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AttributeProductService attrService;

	@Autowired
	private MappingModel mappingModel;

	@Autowired
	private GlobalConfig globalConfig;

	@GetMapping("admin/order.html")
	public String index() {
		return "manager/order/order";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("admin/list-order")
	public ResponseEntity<JSONObject> getNewOrder(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		Integer status = ConvertUtils.convertStringToInt(request.getParameter("status"), 1);
		Integer currentPage = ConvertUtils.convertStringToInt(request.getParameter("page"), globalConfig.getInitPage());
		Page<Order> page = orderService.findAllByUserRequest(status, currentPage, globalConfig.getSizeManagePage());
		List<Order> ordersFromDb = page.getContent();
		List<JSONObject> ordersJSON = new ArrayList<JSONObject>();
		for (Order item : ordersFromDb) {
			ordersJSON.add(mappingModel.mappingModel(item));
		}
		result.put("currentPage", page.getNumber() + 1);
		result.put("totalPage", page.getTotalPages());
		result.put("listOrder", ordersJSON);
		return ResponseEntity.ok(result);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("admin/detail-order")
	public ResponseEntity<JSONObject> getDetailOrder(@RequestParam(value = "idOrder", required = false) String idOrder)
			throws Exception {
		JSONObject result = new JSONObject();
		Order order = orderService.findById(idOrder);
		JSONObject orderJson = mappingModel.mappingModel(order);
		result.put("order", orderJson);
		return ResponseEntity.ok(result);
	}

	@PostMapping("admin/status-order")
	public ResponseEntity<Boolean> changeStatus(HttpServletRequest request) throws Exception {
		String idOrder = request.getParameter("idOrder");
		Integer status;
		try {
			status = Integer.parseInt(request.getParameter("status"));
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
		Order order = orderService.findById(idOrder);
		if (order == null) {
			return ResponseEntity.ok(null);
		}
		if (status == 4 || status == 0) {
			List<OrderDetail> orderDetails = order.getOrderDetails();
			AttributeProduct attributeProduct = null;
			for (OrderDetail orderDetail : orderDetails) {
				attributeProduct = orderDetail.getAttributeProduct();
				attributeProduct.setAmount(status == 4 ? attributeProduct.getAmount() + orderDetail.getQuantity()
						: attributeProduct.getAmount() - orderDetail.getQuantity());
				attrService.saveOrUpdate(attributeProduct, getUserLogined());
			}
		}
		order.setProcessingStatus(status);
		orderService.saveOrUpdate(order, getUserLogined());
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
