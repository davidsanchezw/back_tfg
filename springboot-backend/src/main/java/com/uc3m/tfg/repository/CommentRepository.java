
package com.uc3m.tfg.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uc3m.tfg.model.Comment;
import com.uc3m.tfg.model.ResponseStatement;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.model.Team;
import com.uc3m.tfg.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	//Consultas basadas en nombre de metodo
	public Iterable<Comment> findByTaskAndUser(Task task, User user);
	public Iterable<Comment> findByTaskAndTeam(Task task, Team team);
	public Iterable<Comment> findByResponse(ResponseStatement responseStatement);
	public Iterable<Comment> findByUser(User user);
	public Iterable<Comment> findByTeam(Team team);
}
