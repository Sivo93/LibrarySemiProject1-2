package com.example.myweb.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myweb.member.dto.MemberDTO;
import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	public void save(MemberDTO memberDTO) {
		// 1. dto -> entity 변환
		// 2. repository의 save 메서드 호출
		System.out.println("MemberService.save() 실행, 아이디:"+ memberDTO.getMemberId());
		MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
		memberRepository.save(memberEntity);
		// repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
	}
	
	public MemberDTO login(MemberDTO memberDTO) {
		// 1. 회원이 입력한 아이디로 DB에서 조회를 함
		// 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
		Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());

		if(byMemberId.isPresent()) {
			// 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
			MemberEntity memberEntity = byMemberId.get();
			if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
				// 비밀번호가 일치
				// entity -> dto 변환 후 리턴
				MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
				
				return dto;
			} else {
				// 비밀번호가 불일치(로그인실패)
				
				return null;
			}
			
		} else {
			// 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
			
			return null;
		}
	}

	public List<MemberDTO> findAll() {
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		
		List<MemberDTO> memberDTOList = new ArrayList<>();
		for(MemberEntity memberEntity: memberEntityList) {
			memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
		}
		return memberDTOList;
	}

	public MemberDTO findBySeq(Long seq) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(seq); // 원래 있는 메서드
		if(optionalMemberEntity.isPresent()) {
			
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		} else {
			return null;
		}
	}

	public MemberDTO updateForm(String myId) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(myId);
		if(optionalMemberEntity.isPresent()) {
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		} else {
			return null;
		}
	}

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

	public void deleteBySeq(Long seq) {
		memberRepository.deleteById(seq);
		
	}

	public String idCheck(String memberId) {
		Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberId);
		if(byMemberId.isPresent()) {
			// 조회결과가 있다 => 사용할 수 없다
			return null;
		} else {
			// 조회결과가 없다 => 사용할 수 있다.
			return "ok";
		}
	}
	
    public MemberDTO findByMemberId(String memberId) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberId(memberId);
        if (memberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(memberEntity.get());
        } else {
            return null;
        }
    }
	public MemberEntity getUser(String username) {
		Optional<MemberEntity> memberEntity = this.memberRepository.findByMemberId(username);
		if (memberEntity.isPresent()) {
			return memberEntity.get();
		} else {
			return null;
		}
	}
}
