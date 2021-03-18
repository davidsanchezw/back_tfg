package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Task;

public interface TaskService {
	
	public Iterable<Task> findAll();
	
	public Page<Task> findAll(Pageable pageable);
	
	public Optional<Task> findById(Long id);
	
	public Task save(Task task);
	
	public void deleteById(Long id);	

}
