package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	//Consultas basadas en nombre de metodo
	public Iterable<Task> findByGroup(Group group);
}
