
package com.uc3m.tfg.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
	//Consultas basadas en nombre de metodo
	public Iterable<Team> findByTask(Task task);
	public Optional<Team> findByTaskAndUsers(Task task, User user);
}
