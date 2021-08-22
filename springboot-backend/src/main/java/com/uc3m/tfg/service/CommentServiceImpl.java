package com.uc3m.tfg.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}
	
	@Override
	public Iterable<Comment> findByTaskAndUser(Task task, User user) {
		return commentRepository.findByTaskAndUser(task, user);
	}

	@Override
	public Iterable<Comment> findByTaskAndTeam(Task task, Team team) {
		return commentRepository.findByTaskAndTeam(task, team);
	}
	
	@Override
	public Iterable<Comment> findByResponse(ResponseStatement responseStatement) {
		return commentRepository.findByResponse(responseStatement);
	}
	
	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}

}
