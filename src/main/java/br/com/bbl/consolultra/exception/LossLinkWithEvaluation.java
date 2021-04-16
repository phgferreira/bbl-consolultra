package br.com.bbl.consolultra.exception;

public class LossLinkWithEvaluation extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Perdeu o vínculo com a Avaliação";
	}

}
