package br.com.bbl.consolultra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping("/erro")
	public ModelAndView erro(Integer e) {
		String summary;
		String msg;
		
		switch (e) {
		case 1:
			summary = "Senha Start Master incorreto";
			msg = "Revise a senha e tente novamente";
			break;

		default:
			summary = "Erro desconhecido";
			msg = "Código de erro não programado";
			break;
		}
		
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("summary", summary);
		mv.addObject("msg", msg);
		return mv;
	}
}
