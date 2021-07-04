package com.uc3m.tfg.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "times")
public class ScheduleTime implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "presentation") 
	private LocalDateTime presentation;
	
	@Column(name = "revision") 
	private LocalDateTime revision;
	
	@Column(name = "final_presentation")
	private LocalDateTime finalPresentation;
	
	@Column(name = "final_revision")
	private LocalDateTime finalRevision;	
		
	
	//Task
	@OneToOne(mappedBy = "scheduleTime")
    private Task task;
	
	public ScheduleTime() {
	
	}
	
	public ScheduleTime(LocalDateTime presentation, LocalDateTime revision, LocalDateTime finalPresentation,
			LocalDateTime finalRevision) {
		super();
		this.presentation = presentation;
		this.revision = revision;
		this.finalPresentation = finalPresentation;
		this.finalRevision = finalRevision;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPresentation() {
		return presentation;
	}

	public void setPresentation(LocalDateTime presentation) {
		this.presentation = presentation;
	}

	public LocalDateTime getRevision() {
		return revision;
	}

	public void setRevision(LocalDateTime revision) {
		this.revision = revision;
	}

	public LocalDateTime getFinalPresentation() {
		return finalPresentation;
	}

	public void setFinalPresentation(LocalDateTime finalPresentation) {
		this.finalPresentation = finalPresentation;
	}

	public LocalDateTime getFinalRevision() {
		return finalRevision;
	}

	public void setFinalRevision(LocalDateTime finalRevision) {
		this.finalRevision = finalRevision;
	}

	@JsonBackReference(value="task-time") // PAra que no se muestr en bucle
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
}
	