
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Group;

public interface GroupService {
	
	public Iterable<Group> findAll();
	
	public Page<Group> findAll(Pageable pageable);
	
	public Optional<Group> findById(Long id);
	
	public Group save(Group group);
	
	public void deleteById(Long id);	

}
