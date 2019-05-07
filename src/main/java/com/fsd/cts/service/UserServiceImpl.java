package com.fsd.cts.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsd.cts.model.User;
import com.fsd.cts.repository.RoleRepository;
import com.fsd.cts.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public void updateUserDetails(User user) {
    	User tempUser = userRepository.findByUsername(user.getUsername());
    	if(null != tempUser) {
    		tempUser.setRoles(new HashSet<>(roleRepository.findAll()));
    		userRepository.delete(tempUser);
    		this.save(user);
    	}else {
    		this.save(user);
    	}
    }
}
