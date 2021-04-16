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
public class Happening implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5940268024339359776L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String name;
	
	@ManyToOne
	private Evaluation evaluation;
	
	@OneToMany(mappedBy = "happening")
	private List<Question> questions;
	
	public Happening() {
		
	}
	
	@Override
	public String toString() {
		return "Happening [id=" + id + ", name=" + name + ", evaluation=" + (evaluation != null ? evaluation.getId() : null) + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
