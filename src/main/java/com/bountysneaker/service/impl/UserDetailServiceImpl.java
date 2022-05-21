package com.bountysneaker.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bountysneaker.entities.User;
import com.bountysneaker.service.UserService;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userService.findUserByUserNameAndStatus(username, Boolean.TRUE);
		} catch (Exception e) {
			return new User();
		}
	}

}
