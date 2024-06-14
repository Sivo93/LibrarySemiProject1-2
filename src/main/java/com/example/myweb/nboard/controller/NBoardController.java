package com.example.myweb.nboard.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myweb.nboard.NBoardService;

import com.example.myweb.nboard.NBoardVO;

@Controller
public class NBoardController {
	
	@Resource(name="nboardService")
	private NBoardService nboardService;
	
	private static final Logger logger = LoggerFactory.getLogger(NBoardController.class);
	
	@RequestMapping(value="/nboardList", method=RequestMethod.GET)
	public String nboardList(Locale locale, Model model) throws SQLException {
		
		List<NBoardVO> nboardList = nboardService.getList(null);
		model.addAttribute("nboardList", nboardList);
		
		return "notiboard/list";
	}
	
	@RequestMapping(value="/nboardDetail", method=RequestMethod.GET)
	public String nboardDetail(@RequestParam("seq") int seq, Model model) throws SQLException {
		
		NBoardVO vo = new NBoardVO();
		vo.setSeq(seq);
		NBoardVO nboard = nboardService.getOne(vo);
		model.addAttribute("nboard", nboard);
		
		return "notiboard/detail";
	}
	
	@RequestMapping(value="/nboardUpdate", method=RequestMethod.GET)
	public String nboardUpdate(@RequestParam("seq") int seq, Model model) {
		
		NBoardVO vo = new NBoardVO();
		vo.setSeq(seq);
		NBoardVO nboard = nboardService.getOne(vo);
		model.addAttribute("nboard", nboard);
		
		return "notiboard/update";
	}
	
	@RequestMapping(value="/nboardUpdate", method=RequestMethod.POST)
	public String nboardUpdate2(NBoardVO vo, Model model) {
		
		nboardService.update(vo);
		
		return "redirect:nboardList";
	}
	
	@RequestMapping(value="/nboardWrite", method=RequestMethod.POST)
	public String nboardWriteOk(NBoardVO vo) {
		
		nboardService.insert(vo);
		
		return "redirect:nboardList";
	}
	
	@RequestMapping(value="/nboardDelete", method=RequestMethod.GET)
	public String nboardDelete(NBoardVO vo) {
		
		nboardService.delete(vo);
		
		return "redirect:nboardList";
	}
}