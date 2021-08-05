package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.repository.ResponseStatementRepository;

@Service
public class ResponseStatementServiceImpl implements ResponseStatementService{
	
	@Autowired
	private ResponseStatementRepository responseStatementRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<ResponseStatement> findAll(){
		return responseStatementRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ResponseStatement> findAll(Pageable pageable){
		return responseStatementRepository.findAll(pageable);
	}
	
	@Override
	public Optional<ResponseStatement> findById(Long id){
		return responseStatementRepository.findById(id);
	}
	
	@Override
	public ResponseStatement save(ResponseStatement responseStatement) {
		return responseStatementRepository.save(responseStatement);
	}
	
	@Override
	public void deleteById(Long id) {
		responseStatementRepository.deleteById(id);
	}
	
	@Override
	public Optional<ResponseStatement> findByTaskAndUser(Task task, User user){
		return responseStatementRepository.findByTaskAndUser(task, user);
	}
}
