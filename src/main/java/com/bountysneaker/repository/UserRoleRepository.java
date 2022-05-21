package com.bountysneaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bountysneaker.entities.RequestCancelOrder;
import com.bountysneaker.entities.UserRole;

@Repository
public interface UserRoleRepository
		extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<RequestCancelOrder> {

	List<UserRole> findByStatus(Boolean status);
}
