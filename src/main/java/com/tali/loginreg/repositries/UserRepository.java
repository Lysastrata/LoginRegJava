package com.tali.loginreg.repositries;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tali.loginreg.models.Role;
import com.tali.loginreg.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	int countByRoles(Role role);
	Long deleteById(String search);
	User findById(Long id);

}
