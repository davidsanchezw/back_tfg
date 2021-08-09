package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.User;

public interface UserService {
	
	public Iterable<User> findAll();
	
	public Page<User> findAll(Pageable pageable);
	
	public Optional<User> findById(Long id);	
	
	public User save(User user);
	
	public void deleteById(Long id);	
	
	public Iterable<User> findByGroup(Group group);
	
	public Optional<User> findByEmail(String email);

	public Iterable<User> findByGroupsAndTypeUser(Group group, int typeUser);

	
}
