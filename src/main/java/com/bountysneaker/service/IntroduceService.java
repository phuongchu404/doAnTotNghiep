package com.bountysneaker.service;

import java.util.List;

import com.bountysneaker.entities.Introduce;
import com.bountysneaker.entities.User;

public interface IntroduceService {

	List<Introduce> findAll() throws Exception;

	Boolean deleteById(Integer id) throws Exception;

	Introduce saveOrUpdate(Introduce introduce, User userLogin) throws Exception;

	Introduce findById(Integer id) throws Exception;

}
