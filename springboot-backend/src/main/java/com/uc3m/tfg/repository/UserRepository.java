package com.uc3m.tfg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	//Consultas basadas en nombre de metodo
	public Optional<User> findByEmail(String email);
	public Iterable<User> findByGroups(Group group);
	public Iterable<User> findByGroupsAndTypeUser(Group group, int typeUser);
}
