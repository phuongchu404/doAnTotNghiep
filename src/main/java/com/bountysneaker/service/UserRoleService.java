package com.bountysneaker.service;

import com.bountysneaker.entities.User;
import com.bountysneaker.entities.UserRole;

public interface UserRoleService {

	UserRole saveOrUpdate(UserRole userRole, User userLogin) throws Exception;

	Boolean deleteById(Integer id) throws Exception;

	UserRole findById(Integer id) throws Exception;

}
