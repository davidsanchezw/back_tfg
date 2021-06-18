package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.ScheduleTime;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Long>{
	//Consultas basadas en nombre de metodo
}
