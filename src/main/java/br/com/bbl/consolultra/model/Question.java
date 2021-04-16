package br.com.bbl.consolultra.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class Question implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464791269643749186L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String ask;
	
	@ManyToOne
	private Happening happening;
	
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] img;

	public String getImgForString() throws UnsupportedEncodingException {
		if (this.img != null)
			return new String(Base64.getEncoder().encode(this.img), "UTF-8");
		else
			return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public Happening getHappening() {
		return happening;
	}

	public void setHappening(Happening happening) {
		this.happening = happening;
	}

	public List<Answer> getAnswers() {
		if (this.answers == null) return new ArrayList<Answer>();
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
