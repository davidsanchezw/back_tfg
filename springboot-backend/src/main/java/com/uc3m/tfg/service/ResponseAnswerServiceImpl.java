package com.uc3m.tfg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uc3m.tfg.model.ResponseAnswer;
import com.uc3m.tfg.repository.ResponseAnswerRepository;

@Service
public class ResponseAnswerServiceImpl implements ResponseAnswerService{
	
	@Autowired
	private ResponseAnswerRepository responseAnswerRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<ResponseAnswer> findAll(){
		return responseAnswerRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ResponseAnswer> findAll(Pageable pageable){
		return responseAnswerRepository.findAll(pageable);
	}
	
	@Override
	public Optional<ResponseAnswer> findById(Long id){
		return responseAnswerRepository.findById(id);
	}
	
	@Override
	public ResponseAnswer save(ResponseAnswer responseAnswer) {
		return responseAnswerRepository.save(responseAnswer);
	}
	
	
	@Override
	public void deleteById(Long id) {
		responseAnswerRepository.deleteById(id);
	}
}
