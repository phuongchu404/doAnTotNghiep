package com.bountysneaker.service;

import org.springframework.data.domain.Page;

import com.bountysneaker.entities.Contact;
import com.bountysneaker.entities.User;

public interface ContactService {

	Contact saveOrUpdate(Contact contact, User userEdit) throws Exception;

	Page<Contact> findAll(String keySearch, String currentPage, Integer sizeOfPage);

	Contact findById(String id) throws Exception;

	Boolean delete(String id) throws Exception;
}
