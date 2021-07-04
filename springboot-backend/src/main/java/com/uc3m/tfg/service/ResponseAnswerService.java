
package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uc3m.tfg.model.ResponseAnswer;

public interface ResponseAnswerService {
	
	public Iterable<ResponseAnswer> findAll();
	
	public Page<ResponseAnswer> findAll(Pageable pageable);
	
	public Optional<ResponseAnswer> findById(Long id);
	
	public ResponseAnswer save(ResponseAnswer responseAnswer);
	
	public void deleteById(Long id);		

}
