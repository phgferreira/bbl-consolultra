package br.com.bbl.consolultra.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Participant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6387798672958591971L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique = true)
	private String crm;
	
	@Column
	private Integer experience;
	
	@Column
	private String specialty;
	
	@OneToMany(mappedBy = "participant")
	private List<AnswerCard> answerCards;
	
	public Participant() {
		
	}
	
	public Participant(String crm) {
		this.crm = crm;
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", crm=" + crm + ", experience=" + experience + ", specialty=" + specialty
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public List<AnswerCard> getAnswerCards() {
		return answerCards;
	}

	public void setAnswerCards(List<AnswerCard> answerCards) {
		this.answerCards = answerCards;
	}
}
