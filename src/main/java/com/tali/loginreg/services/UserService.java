package com.tali.loginreg.services;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tali.loginreg.models.Role;
import com.tali.loginreg.models.User;
import com.tali.loginreg.repositries.RoleRepository;
import com.tali.loginreg.repositries.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public int findCountOfAdmins() {
    		Role role = roleRepository.findRoleById((long) 2);
    		return  userRepository.countByRoles(role);
    }
    public void changeUserToAdminRole(Long id) {
    		User user = userRepository.findById(id);
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    


	public List<User> allUsers() {
		return (List<User>) userRepository.findAll();
	}
	public void destroyUser(Long id) {
		userRepository.delete(id);
		
	} 
}