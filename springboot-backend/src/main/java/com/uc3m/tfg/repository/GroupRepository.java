
package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
	//Consultas basadas en nombre de metodo
}
