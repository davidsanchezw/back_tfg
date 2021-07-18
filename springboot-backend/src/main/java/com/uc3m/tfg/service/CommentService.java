
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.Comment;

public interface CommentService {
	
	public Iterable<Comment> findAll();
	
	public Page<Comment> findAll(Pageable pageable);
	
	public Optional<Comment> findById(Long id);
	
	public Comment save(Comment comment);
	
	public void deleteById(Long id);	
	
}
