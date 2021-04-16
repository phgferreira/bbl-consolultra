package br.com.bbl.consolultra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Evaluation implements Serializable, Comparable<Evaluation> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2376620387167345148L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column
	private Boolean active;
	
	@OneToMany(mappedBy = "evaluation")
	List<Happening> happenings = new ArrayList<Happening>();
	
	@OneToMany(mappedBy = "evaluation")
	List<AnswerCard> answerCards = new ArrayList<AnswerCard>();
	
	public boolean equals(Evaluation e) {
		return (this.id == e.id);
	}

	@Override
	public int compareTo(Evaluation e) {
		if (this.date.getTime() > e.date.getTime())
			return 1;
		else
			return -1;
	}
	
	public List<AnswerCard> getAnswercardsByState(AnswerCardState state) {
		List<AnswerCard> answerCards = new ArrayList<AnswerCard>();
		for (AnswerCard answerCard : this.answerCards) {
			if (answerCard.getState() == state)
				answerCards.add(answerCard);
		}
		return answerCards;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<AnswerCard> getAnswerCards() {
		return answerCards;
	}

	public void setAnswerCards(List<AnswerCard> answerCards) {
		this.answerCards = answerCards;
	}

	public List<Happening> getHappenings() {
		return happenings;
	}

	public void setHappenings(List<Happening> happenings) {
		this.happenings = happenings;
	}
}
