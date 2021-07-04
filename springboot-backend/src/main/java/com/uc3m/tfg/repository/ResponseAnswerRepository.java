package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.ResponseAnswer;

@Repository
public interface ResponseAnswerRepository extends JpaRepository<ResponseAnswer, Long>{
	//Consultas basadas en nombre de metodo
}