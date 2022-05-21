package com.bountysneaker.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.entities.Contact;
import com.bountysneaker.entities.User;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.ContactRepository;
import com.bountysneaker.service.ContactService;
import com.bountysneaker.specification.ContactSpecification;
import com.bountysneaker.utils.Validate;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private ContactSpecification contactSpecification;

	@Override
	public Contact saveOrUpdate(Contact contact, User userEdit) throws Exception {
		if (contact.getId() != null) {
			Contact oldContact = contactRepository.findById(contact.getId())
					.orElseThrow(() -> new EntityNotFoundCustomException("Not found contact"));
			contact.setCreatedBy(oldContact.getCreatedBy());
			contact.setCreatedDate(oldContact.getCreatedDate());
		} else {
			contact.setCreatedBy(userEdit != null ? userEdit.getId() : null);
			contact.setCreatedDate(Calendar.getInstance().getTime());
		}
		contact.setUpdatedBy(userEdit != null ? userEdit.getId() : null);
		contact.setUpdatedDate(Calendar.getInstance().getTime());
		return contactRepository.save(contact);
	}

	@Override
	public Page<Contact> findAll(String keySearch, String currentPage, Integer sizeOfPage) {
		Pageable pageable = PageRequest.of(
				(Validate.isNumber(currentPage) ? Integer.parseInt(currentPage) : globalConfig.getInitPage()) - 1,
				sizeOfPage, Sort.by("createdDate", "updatedDate").descending());
		return contactRepository.findAll(contactSpecification.finAll(keySearch), pageable);
	}

	@Override
	public Contact findById(String id) throws Exception {
		if (!Validate.isNumber(id)) {
			return null;
		}
		return contactRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found contact"));
	}

	@Override
	public Boolean delete(String id) throws Exception {
		if (!Validate.isNumber(id)) {
			return false;
		}
		contactRepository.deleteById(Integer.parseInt(id));
		return true;
	}

}
