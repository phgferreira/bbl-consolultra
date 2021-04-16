package br.com.bbl.consolultra.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AnswerSelected implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7613810432450435146L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private AnswerCard answerCard;
	
	@ManyToOne
	private Question question;
	
	@ManyToOne
	private Answer answer;
	
	public AnswerSelected(AnswerCard answerCard, Question question) {
		this.answerCard = answerCard;
		this.question = question;
	}
	
	public AnswerSelected() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AnswerCard getAnswerCard() {
		return answerCard;
	}

	public void setAnswerCard(AnswerCard answerCard) {
		this.answerCard = answerCard;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
}
