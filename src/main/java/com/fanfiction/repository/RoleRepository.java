package com.fanfiction.repository;

import java.util.Optional;

import com.fanfiction.models.ERole;
import com.fanfiction.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
