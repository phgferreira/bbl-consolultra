package br.com.bbl.consolultra.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AnswerCard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -54645371918805959L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Enumerated(EnumType.STRING)
	private AnswerCardState state;
	
	@ManyToOne
	private Evaluation evaluation;
	
	@ManyToOne
	private Participant participant;
	
	@OneToMany(mappedBy = "answerCard")
	private List<AnswerSelected> answerSelecteds;
	
	public AnswerCard() {
		
	}
	
	public AnswerCard(Date date, AnswerCardState state, Evaluation evaluation, Participant participant) {
		date = new Date();
		this.date = date;
		this.state = state;
		this.evaluation = evaluation;
		this.participant = participant;
	}
	
	public boolean equals(AnswerCard ac) {
		return (this.id == ac.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public AnswerCardState getState() {
		return state;
	}

	public void setState(AnswerCardState state) {
		this.state = state;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public List<AnswerSelected> getAnswerSelecteds() {
		return answerSelecteds;
	}

	public void setAnswerSelecteds(List<AnswerSelected> answerSelecteds) {
		this.answerSelecteds = answerSelecteds;
	}

}
