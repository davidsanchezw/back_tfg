package com.uc3m.tfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "type_user")
	private int typeUser;	
	
	@Column(name = "hash")
	private String hash;
	
	//Groups
	@ManyToMany(fetch = FetchType.LAZY,
			mappedBy="users")
	private List<Group> group = new ArrayList<>();
	
	public User() {
	
	}
	
	public User(String firstName, String lastName, String email, int typeUser, String salt, String hash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.typeUser = typeUser;
		this.hash = hash;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}
	
	

}
