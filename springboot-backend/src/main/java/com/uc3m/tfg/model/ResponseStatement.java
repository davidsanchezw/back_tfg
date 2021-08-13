package com.uc3m.tfg.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "responsesstatement")
public class ResponseStatement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "last_time")
	private LocalDateTime lastTime;

	@Column(name = "statement")
	private String statement;

	// ResponseAnswer
	@OneToMany(mappedBy = "responseStatement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ResponseAnswer> responseAnswer = new ArrayList<>();

	//User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	//Task
	@ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
	
	//Team
	@OneToOne
	@JoinColumn(name = "team_id", referencedColumnName = "id")
	private Team team;

	//Forum
	@OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
		

	public ResponseStatement(LocalDateTime lastTime, String statement, List<ResponseAnswer> responseAnswer, User user,
			Task task) {
		super();
		this.lastTime = lastTime;
		this.statement = statement;
		this.responseAnswer = responseAnswer;
		this.user = user;
		this.task = task;
	}
	
	public ResponseStatement() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getLastTime() {
		return lastTime;
	}

	public void setLastTime(LocalDateTime lastTime) {
		this.lastTime = lastTime;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public List<ResponseAnswer> getResponseAnswer() {
		return responseAnswer;
	}

	public void setResponseAnswer(List<ResponseAnswer> responseAnswer) {
		this.responseAnswer = responseAnswer;
	}
	
	public void addAnswer(ResponseAnswer answer) {
		responseAnswer.add(answer);
		answer.setResponseStatement(this);
    }

	@JsonBackReference(value="user-response")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonBackReference(value="task-response")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
        comment.setResponse(this);
    }

	public void setTeam(Team team) {
		this.team = team;
	}

	
}
