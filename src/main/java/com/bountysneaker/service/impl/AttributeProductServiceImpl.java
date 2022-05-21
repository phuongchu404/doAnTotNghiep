package com.bountysneaker.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bountysneaker.entities.AttributeProduct;
import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.User;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.AttributeProductRepository;
import com.bountysneaker.service.AttributeProductService;

@Service
@Transactional
public class AttributeProductServiceImpl implements AttributeProductService {

	@Autowired
	private AttributeProductRepository attrRepository;

	@Override
	public List<AttributeProduct> findAllByProduct(Product product) throws Exception {
		if (product == null) {
			return new ArrayList<AttributeProduct>();
		}
		return attrRepository.findByProduct(product);
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		attrRepository.deleteById(id);
		return true;
	}

	@Override
	public AttributeProduct findById(Integer id) throws Exception {
		return attrRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found attribute product has id: " + id));
	}

	@Override
	public AttributeProduct saveOrUpdate(AttributeProduct attributeProduct, User userLogin) throws Exception {
		Integer idUserLogin = userLogin != null ? userLogin.getId() : null;
		if (attributeProduct.getId() != null) {
			AttributeProduct oldAttr = attrRepository.findById(attributeProduct.getId())
					.orElseThrow(() -> new EntityNotFoundCustomException(
							"Not found attribute product has id: " + attributeProduct.getId()));
			attributeProduct.setCreatedBy(oldAttr.getCreatedBy());
			attributeProduct.setCreatedDate(oldAttr.getCreatedDate());
		}
		attributeProduct.setUpdatedBy(idUserLogin);
		attributeProduct.setUpdatedDate(Calendar.getInstance().getTime());
		return attrRepository.save(attributeProduct);
	}

}
