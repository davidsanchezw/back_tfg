package com.uc3m.tfg.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	//Consultas basadas en nombre de metodo
	public Iterable<Task> findByGroupOrderByIdDesc(Group group);
	public Optional<Task> findByCommentsOrderByIdDesc(Comment comment);
}
