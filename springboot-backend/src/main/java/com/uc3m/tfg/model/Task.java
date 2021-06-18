package com.uc3m.tfg.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "task")
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type_identity") // grupal o individual
	private int typeIdentity;
	
	@Column(name = "type_task") // test o desarrollo
	private int typeTask;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "statement")
	private String statement;	
	
	@Column(name = "reviews")
	private int reviews;
	
	@Column(name = "answers")
	private int answers;
	
	//Group
	@ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
	
	@OneToOne
	@JoinColumn(name = "scheduleTime_id", referencedColumnName = "id")
	private ScheduleTime scheduleTime;
	
	public Task() {
	
	}
	
	
	public Task(int typeIdentity, int typeTask, String title, String statement, int reviews, int answers) {
		super();
		this.typeIdentity = typeIdentity;
		this.typeTask = typeTask;
		this.title = title;
		this.statement = statement;
		this.reviews = reviews;
		this.answers = answers;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getTypeIdentity() {
		return typeIdentity;
	}



	public void setTypeIdentity(int typeIdentity) {
		this.typeIdentity = typeIdentity;
	}



	public int getTypeTask() {
		return typeTask;
	}



	public void setTypeTask(int typeTask) {
		this.typeTask = typeTask;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getStatement() {
		return statement;
	}



	public void setStatement(String statement) {
		this.statement = statement;
	}



	public int getReviews() {
		return reviews;
	}



	public void setReviews(int reviews) {
		this.reviews = reviews;
	}



	public int getAnswers() {
		return answers;
	}



	public void setAnswers(int answers) {
		this.answers = answers;
	}	
	
	@JsonBackReference // PAra que no se muestr en bucle
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group =  group;
	}


	public ScheduleTime getScheduleTime() {
		return scheduleTime;
	}


	public void setScheduleTime(ScheduleTime scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	
	

}