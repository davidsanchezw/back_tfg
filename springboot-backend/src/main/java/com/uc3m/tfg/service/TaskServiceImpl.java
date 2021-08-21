package com.uc3m.tfg.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findAll(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Optional<Task> findByComment(Comment comment) {
		return taskRepository.findByCommentsOrderByIdDesc(comment);
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public void deleteById(Long id) {
		taskRepository.deleteById(id);
	}
	
	@Override
	public Iterable<Task> findByGroup(Group group){
		return taskRepository.findByGroupOrderByIdDesc(group);
	}


}
