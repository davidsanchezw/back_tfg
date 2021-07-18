
package com.uc3m.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	//Consultas basadas en nombre de metodo
}
