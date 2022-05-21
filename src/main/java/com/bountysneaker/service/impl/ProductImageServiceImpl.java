package com.bountysneaker.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.entities.Product;
import com.bountysneaker.entities.ProductImage;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.ProductImageRepository;
import com.bountysneaker.service.ProductImageService;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageRepository imageRepository;

	@Autowired
	private GlobalConfig globalConfig;

	public List<ProductImage> findByProduct(Product product) {
		return imageRepository.findByProduct(product);
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		ProductImage productImage = imageRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found image has id: " + id));
		new File(globalConfig.getUploadRootPath() + productImage.getPath() + productImage.getTitle()).delete();
		imageRepository.deleteById(id);
		return true;
	}
}
