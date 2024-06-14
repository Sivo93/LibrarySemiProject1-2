package com.example.myweb.rboard.controller;

import java.sql.SQLException;
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

import com.example.myweb.rboard.RBoardService;
import com.example.myweb.rboard.RBoardVO;

@Controller
public class RBoardController {
	
	@Resource(name="rboardService")
	private RBoardService rboardService;
	
	private static final Logger logger = LoggerFactory.getLogger(RBoardController.class);
	
	@RequestMapping(value="/rboardList", method=RequestMethod.GET)
	public String rboardAList(Locale locale, Model model) throws SQLException {
		
		List<RBoardVO> rboardList = rboardService.getList(null);
		model.addAttribute("rboardList", rboardList);
		
		return "reviewboard/list";
	}
	
	@RequestMapping(value="/rboardDetail", method=RequestMethod.GET)
	public String rboardDetail(@RequestParam("seq") int seq, Model model) throws SQLException {
		//System.out.println("boardDetail >>>> seq : " + seq);
		
		RBoardVO vo = new RBoardVO();
		vo.setSeq(seq);
		RBoardVO rboard = rboardService.getOne(vo);
		model.addAttribute("rboard", rboard);
		
		return "reviewboard/detail";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.GET)
	public String rboardUpdate(@RequestParam("seq") int seq, Model model) {
		
		RBoardVO vo = new RBoardVO();
		vo.setSeq(seq);
		RBoardVO rboard = rboardService.getOne(vo);
		model.addAttribute("rboard", rboard);
		
		return "reviewboard/update";
	}
	
	@RequestMapping(value="/rboardUpdate", method=RequestMethod.POST)
	public String rboardUpdate2(RBoardVO vo, Model model) {
		
		rboardService.update(vo);
		
		return "redirect:rboardList";
	}
	
	
	@RequestMapping(value="/rboardWrite", method=RequestMethod.POST)
	public String rboardWriteOk(RBoardVO vo) {
		
		rboardService.insert(vo);
		
		return "redirect:rboardList";
	}
	
	@RequestMapping(value="/rboardDelete", method=RequestMethod.GET)
	public String rboardDelete(RBoardVO vo) {
		
		rboardService.delete(vo);
		
		return "redirect:rboardList";
	}
}