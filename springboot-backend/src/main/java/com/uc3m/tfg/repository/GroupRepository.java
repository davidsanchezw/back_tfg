
package com.uc3m.tfg.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.User;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
	//Consultas basadas en nombre de metodo
	public Optional<Group> findByGroupName(String groupName);
	public Iterable<Group> findByUsers(User user);
	public Optional<Group> findByTasks(Task	task);

}
