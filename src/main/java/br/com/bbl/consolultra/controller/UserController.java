package br.com.bbl.consolultra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@RequestMapping("/userForm")
	public ModelAndView form(Integer id) {
		
		ModelAndView mv = new ModelAndView("user_form");
		return mv;
	}
}
