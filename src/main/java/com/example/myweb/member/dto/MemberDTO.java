package com.example.myweb.member.dto;

import com.example.myweb.member.entity.MemberEntity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    @Column(name = "seq")
    private Long seq;
    @Column(name = "member_id")
    private String memberId;
    @Column(name = "member_password")
    private String memberPassword;
    @Column(name = "member_name")
    private String memberName;
    @Column(name = "member_tel")
    private String memberTel;
    @Column(name = "role")
    private String role; // 역할 필드 추가

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setSeq(memberEntity.getSeq());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberTel(memberEntity.getMemberTel());
        memberDTO.setRole(memberEntity.getRole()); // 역할 필드 설정
        return memberDTO;
    }
}
