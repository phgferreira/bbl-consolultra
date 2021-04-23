package br.com.bbl.consolultra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.Failed;
import br.com.bbl.consolultra.repository.FailedRepository;

@Controller
public class FailedController {

	@Autowired
	private FailedRepository fr;
	
	@RequestMapping("/failed")
	private ModelAndView failed(Integer id) {
		Failed failed = fr.findById(id).get();
		
		ModelAndView mv = new ModelAndView("failed");
		mv.addObject("message", failed.getMessage());
		return mv;
	}
	
	@RequestMapping("/faileds")
	private ModelAndView list() {
		List<Failed> faileds = new ArrayList<Failed>();
		fr.findAll().forEach(faileds::add);
		
		ModelAndView mv = new ModelAndView("failed_list");
		mv.addObject("faileds", faileds);
		return mv;
	}
}
