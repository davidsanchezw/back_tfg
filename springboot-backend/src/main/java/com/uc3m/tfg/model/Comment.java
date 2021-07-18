package com.uc3m.tfg.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "comment_text")
	private String commentText;
	
	@Column(name = "calification_response")
	private int calificationResponse;
	
	@Column(name = "calification_commentator")
	private int calificationCommentator;
	
	@Column(name = "time")
	private LocalDateTime time;
	
	//User
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User user;
	
	//Response
	@ManyToOne
	@JoinColumn(name = "response_id")
	private ResponseStatement response;
	
	public Comment() {}

	public Comment(String commentText, int calificationResponse, int calificationCommentator, LocalDateTime time,
			User user, ResponseStatement response) {
		super();
		this.commentText = commentText;
		this.calificationResponse = calificationResponse;
		this.calificationCommentator = calificationCommentator;
		this.time = time;
		this.user = user;
		this.response = response;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getCalificationResponse() {
		return calificationResponse;
	}

	public void setCalificationResponse(int calificationResponse) {
		this.calificationResponse = calificationResponse;
	}

	public int getCalificationCommentator() {
		return calificationCommentator;
	}

	public void setCalificationCommentator(int calificationCommentator) {
		this.calificationCommentator = calificationCommentator;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ResponseStatement getResponse() {
		return response;
	}

	public void setResponse(ResponseStatement response) {
		this.response = response;
	}
	
	
}
