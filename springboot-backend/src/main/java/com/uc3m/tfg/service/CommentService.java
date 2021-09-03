
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;

public interface CommentService {
	
	public Iterable<Comment> findAll();
	
	public Page<Comment> findAll(Pageable pageable);
	
	public Optional<Comment> findById(Long id);	
	
	public Iterable<Comment> findByTaskAndUser(Task task, User user);
	
	public Iterable<Comment> findByTaskAndTeam(Task task, Team team);
	
	public Iterable<Comment> findByResponse(ResponseStatement responseStatement);
	
	public Iterable<Comment> findByUser(User user);
	
	public Iterable<Comment> findByeam(Team team);
	
	public Comment save(Comment comment);
	
	public void deleteById(Long id);	
	
}
