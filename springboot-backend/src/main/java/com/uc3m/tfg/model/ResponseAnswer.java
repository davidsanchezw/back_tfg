package com.uc3m.tfg.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "responsesanswers")
public class ResponseAnswer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "correct")
	private boolean correct;
	
	@Column(name = "statement")
	private String answer;
		
	//ResponseStatement
	@ManyToOne
    @JoinColumn(name = "responsestatement_id")
    private ResponseStatement responseStatement;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ResponseStatement getResponseStatement() {
		return responseStatement;
	}

	public void setResponseStatement(ResponseStatement responseStatement) {
		this.responseStatement = responseStatement;
	}
	
	
}
