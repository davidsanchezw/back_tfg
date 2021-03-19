package com.uc3m.tfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "group_name")
	private String groupName;
	
	
	//Alumnos
	
	//Profesor
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
				CascadeType.PERSIST,
		        CascadeType.MERGE
	        })
	@JoinTable(
			name="group_user",
			joinColumns={ @JoinColumn(name="group_id")}, 
			inverseJoinColumns={ @JoinColumn(name="user_id")}
			)
	private List<User> users = new ArrayList<>();
	
	
	//Tareas
	
	public Group() {
	
	}

	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return groupName;
	}

	public void setName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> teachers) {
		this.users = teachers;
	}
	
	public void addUser(User user) {
        users.add(user);
        user.getGroup().add(this);
    }
	
	public void removeUser(User user) {
		users.remove(user);
        user.getGroup().remove(this);
    }

}