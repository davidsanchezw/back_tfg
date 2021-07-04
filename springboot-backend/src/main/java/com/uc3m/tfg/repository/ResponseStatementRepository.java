package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.ResponseStatement;

@Repository
public interface ResponseStatementRepository extends JpaRepository<ResponseStatement, Long>{
	//Consultas basadas en nombre de metodo
}