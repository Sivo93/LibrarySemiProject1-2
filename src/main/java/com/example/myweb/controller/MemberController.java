package com.example.myweb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myweb.member.dto.MemberDTO;
import com.example.myweb.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/member/save")
	public String saveForm() {
		return "member/save";
	}

	@PostMapping("/member/save")
	public String save(@ModelAttribute MemberDTO memberDTO, Model model) {
		// 중복 확인
		String checkResult = memberService.idCheck(memberDTO.getMemberId());
		if ("ok".equals(checkResult)) {
			memberService.save(memberDTO);
			System.out.println("권한: " + memberDTO.getRole());
			return "member/login";
		} else {
			model.addAttribute("errorMessage", "이미 사용중인 아이디입니다.");
			return "member/save";
		}
	}

	@GetMapping("/member/login")
	public String loginForm() {
		return "member/login";
	}

	@PostMapping("/member/login")
	public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
		MemberDTO loginResult = memberService.login(memberDTO);
		if (loginResult != null) {
			session.setAttribute("loginId", loginResult.getMemberId());
			session.setAttribute("member", loginResult); // 추가된 부분
			session.setAttribute("Role", loginResult.getRole()); // 추가된 부분
			return "redirect:/";
		} else {
			return "member/login";
		}
	}

	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/member/")
	public String findAll(Model model) {
		List<MemberDTO> memberDTOList = memberService.findAll();
		model.addAttribute("memberList", memberDTOList);
		return "member/list";
	}

	@GetMapping("/member/{seq}")
	public String findBySeq(@PathVariable Long seq, Model model) {
		MemberDTO memberDTO = memberService.findBySeq(seq);
		model.addAttribute("member", memberDTO);
		return "member/detail";
	}

	@GetMapping("/member/update")
	public String updateForm(HttpSession session, Model model) {
		String myId = (String) session.getAttribute("loginId");
		MemberDTO memberDTO = memberService.updateForm(myId);
		model.addAttribute("updateMember", memberDTO);
		return "member/update";
	}

	@GetMapping("/member/delete/{seq}")
	public String deleteById(@PathVariable Long seq, HttpSession session) {
		memberService.deleteBySeq(seq);
		session.invalidate(); // 로그아웃 처리
		return "redirect:/"; // 메인 페이지로 리디렉션
	}

	// 관리자가 회원 삭제 처리
	@GetMapping("/admin/member/delete/{seq}")
	public String deleteMemberById(@PathVariable Long seq, HttpSession session) {
		memberService.deleteBySeq(seq);
		return "redirect:/member/"; // 관리자 회원 리스트 페이지로 리다이렉트
	}

	@PostMapping("/member/id-check")
	public @ResponseBody String idCheck(@RequestParam("memberId") String memberId) {
		String checkResult = memberService.idCheck(memberId);
		return checkResult;
	}

	@GetMapping("/member/main")
	public String main() {
		return "/member/main";
	}

	@GetMapping("/member/myInfo")
	public String myInfo(HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginId");
		if (loginId != null) {
			MemberDTO member = memberService.findByMemberId(loginId);
			model.addAttribute("member", member);
			return "member/detail";
		} else {
			return "redirect:/member/login";
		}
	}

	@PostMapping("/member/verifyPassword")
	public String verifyPassword(@RequestParam("password") String password, HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginId");
		if (loginId != null) {
			MemberDTO member = memberService.findByMemberId(loginId);
			if (member != null && member.getMemberPassword().equals(password)) {
				model.addAttribute("member", member);
				return "member/update";
			} else {
				model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("member", member);
				return "member/detail";
			}
		} else {
			return "redirect:/member/login";
		}
	}

	@PostMapping("/member/update")
	public String update(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginId");
		MemberDTO existingMember = memberService.findByMemberId(loginId);
		if (existingMember != null && existingMember.getMemberPassword().equals(memberDTO.getMemberPassword())) {
			model.addAttribute("errorMessage", "새 비밀번호가 기존 비밀번호와 같습니다.");
			model.addAttribute("member", memberDTO);
			return "member/update";
		} else {
			memberService.update(memberDTO);
			return "redirect:/member/myInfo";
		}
	}
}
