
package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
	//Consultas basadas en nombre de metodo
}
