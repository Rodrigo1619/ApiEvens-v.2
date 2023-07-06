package com.grupo4.topTrend.repositories;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Role;

public interface RoleRepository
	extends JpaRepository<Role, UUID> {
	Role findOneByCode(UUID code);
	//List<Role> findByUsersCode(UUID code);
	Role findByRole(String roleName);
	Set<Role> findByUsersCode(UUID code);
}
