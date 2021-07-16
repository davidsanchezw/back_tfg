package com.uc3m.tfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	//Alumnos
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
				CascadeType.PERSIST,
		        CascadeType.MERGE
	        })
	@JoinTable(
			name="team_user",
			joinColumns={ @JoinColumn(name="team_id")}, 
			inverseJoinColumns={ @JoinColumn(name="user_id")}
			)
	private List<User> users = new ArrayList<>();
	
	
	//Tareas
		@ManyToOne
	    @JoinColumn(name = "task_id")
	    private Task task;


		public Team() {		
		}
		
		public Team(List<User> users, Task task) {
			super();
			this.users = users;
			this.task = task;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		public Task getTask() {
			return task;
		}

		public void setTask(Task task) {
			this.task = task;
		}
		
		public void removeUser(User user) {
			users.remove(user);
	        user.getTeams().remove(this);
	    }
	
	
}
