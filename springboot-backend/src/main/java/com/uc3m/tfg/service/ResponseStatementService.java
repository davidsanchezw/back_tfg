
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;

public interface ResponseStatementService {
	
	public Iterable<ResponseStatement> findAll();
	
	public Page<ResponseStatement> findAll(Pageable pageable);
	
	public Optional<ResponseStatement> findById(Long id);
	
	public ResponseStatement save(ResponseStatement responseStatement);
	
	public void deleteById(Long id);		

	public Optional<ResponseStatement> findByTaskAndUser(Task task, User user);
	
	public Optional<ResponseStatement> findByTeam(Team team);

	public Optional<ResponseStatement> findByComments(Comment comment);
	
	public Iterable<ResponseStatement> findByTask(Task task);

}
