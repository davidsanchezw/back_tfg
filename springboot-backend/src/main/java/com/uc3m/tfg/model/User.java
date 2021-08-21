package com.uc3m.tfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	private List<Group> groups = new ArrayList<>();
	
	//Teams
	@ManyToMany(fetch = FetchType.LAZY,
				mappedBy="users")
	private List<Team> teams = new ArrayList<>();
	
	//Responses
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ResponseStatement> responseStatement = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
		
	
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

	@JsonBackReference(value="user-group") // PAra que no se muestr en bucle
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> group) {
		this.groups = group;
	}
	
	@JsonBackReference(value="user-response")
	public List<ResponseStatement> getResponseStatement() {
		return responseStatement;
	}

	public void setResponseStatement(List<ResponseStatement> responseStatement) {
		this.responseStatement = responseStatement;
	}
	
	public void addStatement(ResponseStatement statement) {
		responseStatement.add(statement);
        statement.setUser(this);
    }

	@JsonBackReference(value="user-team")
	public List<Team> getTeams() {
		return teams;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
        comment.setUser(this);
    }
	
	public User userDetails() {
		User userDetails = new User();
		userDetails.setId(this.id);
		userDetails.setFirstName(this.firstName);
		userDetails.setLastName(this.lastName);
		userDetails.setTypeUser(this.typeUser);		
        return userDetails;
    }
	
	public void addTeam(Team team) {
		teams.add(team);
    }
	
	public void deleteComment(Comment comment) {
		comments.remove(comment);
    }

}
