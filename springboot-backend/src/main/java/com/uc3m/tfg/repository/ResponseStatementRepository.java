package com.uc3m.tfg.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.User;

@Repository
public interface ResponseStatementRepository extends JpaRepository<ResponseStatement, Long>{
	//Consultas basadas en nombre de metodo
	public Optional<ResponseStatement> findByTaskAndUser(Task task, User user);
}