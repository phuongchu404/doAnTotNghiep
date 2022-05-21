package com.bountysneaker.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.entities.Role;
import com.bountysneaker.entities.User;
import com.bountysneaker.entities.UserRole;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.repository.RoleRepository;
import com.bountysneaker.repository.UserRepository;
import com.bountysneaker.service.UserService;
import com.bountysneaker.specification.UserSpecification;
import com.bountysneaker.utils.Validate;
import com.bountysneaker.valueObjects.AdminRequest;

@Service
@Transactional 
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private UserSpecification userSpecification;

	@Override
	public User findUserByUserName(String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? new User() : user;
	}

	@Override
	public User saveOrUpdate(User user, MultipartFile avatar, User userLogin, Boolean typeAccount) throws Exception {
		Integer idUserLogin = userLogin != null ? user.getId() : null;
		user.setUpdatedDate(Calendar.getInstance().getTime());
		user.setUpdatedBy(idUserLogin);
		if (user.getId() != null) {
			User oldUser = userRepository.findById(user.getId())
					.orElseThrow(() -> new EntityNotFoundCustomException("Not found user has id: " + user.getId()));
			if (!Validate.isEmptyUploadFile(avatar)) {
				new File(globalConfig.getUploadRootPath() + oldUser.getAvatar()).delete();
				avatar.transferTo(new File(globalConfig.getUploadRootPath() + "user/" + avatar.getOriginalFilename()));
				user.setAvatar("user/" + avatar.getOriginalFilename());
			} else {
				user.setAvatar(oldUser.getAvatar());
			}
			user.setStatus(oldUser.getStatus());
		} else {
			user.setCreatedDate(Calendar.getInstance().getTime());
			user.setCreatedBy(idUserLogin);
			user.setStatus(true);
			user.setPassword(new BCryptPasswordEncoder().encode((user.getPassword())));
			if (!Validate.isEmptyUploadFile(avatar)) {
				avatar.transferTo(new File(globalConfig.getUploadRootPath() + "user/" + avatar.getOriginalFilename()));
				user.setAvatar("user/" + avatar.getOriginalFilename());
			}
			UserRole userRole = null;
			if (typeAccount) {
				List<Role> roles = roleRepository.findAll();
				for (Role role : roles) {
					if (!role.getCode().equalsIgnoreCase("G") && !role.getCode().equalsIgnoreCase("VS")) {
						userRole = new UserRole("ADMIN_S", false, false, true, false, role, user);
						userRole.setStatus(true);
						user.addUserRole(userRole);
					}
				}
			} else {
				Role role = roleRepository.findByCode("G");
				userRole = new UserRole("GUEST", false, false, false, false, role, user);
				user.addUserRole(userRole);
			}
		}
		return userRepository.save(user);
	}

	@Override
	public Page<User> findAllByAdminRequest(AdminRequest adminRequest) throws Exception {
		Pageable pageable = PageRequest.of(adminRequest.getCurrentPage() - 1, adminRequest.getTotalPage(),
				Sort.by("createdDate", "updatedDate").descending());
		return userRepository.findAll(userSpecification.findByRole(adminRequest), pageable);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Boolean changePassword(String newPassword, String oldPassword, User user) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deleteById(Integer id) throws Exception {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found user has id: " + id));
		new File(globalConfig.getUploadRootPath() + user.getAvatar()).delete();
		userRepository.delete(user);
		return true;
	}

	@Override
	public List<User> findAll() throws Exception {
		return userRepository.findAll();
	}

	@Override
	public User findById(Integer id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundCustomException("Not found user"));
	}

	@Override
	public User findByUserNameAndEmail(String userName, String email) throws Exception {
		if (Validate.isNullOrEmptyString(userName) || Validate.isNullOrEmptyString(email)) {
			return null;
		}
		return userRepository.findByUsernameAndEmail(userName, email);
	}

	@Override
	public User findUserByUserNameAndStatus(String username, Boolean status) throws Exception {
		User user = userRepository.findByUsernameAndStatus(username, status);
		return user == null ? new User() : user;
	}

}
