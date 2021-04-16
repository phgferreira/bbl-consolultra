package br.com.bbl.consolultra.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916676022887642059L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String description;
	
	@Column
	private Boolean correct;
	
	@ManyToOne
	private Question question;
	
	@OneToMany(mappedBy = "answer")
	private List<AnswerSelected> answerSelecteds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<AnswerSelected> getAnswerSelecteds() {
		return answerSelecteds;
	}

	public void setAnswerSelecteds(List<AnswerSelected> answerSelecteds) {
		this.answerSelecteds = answerSelecteds;
	}
}
