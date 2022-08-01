package com.shyun.shop.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.shyun.shop.constant.Role;
import com.shyun.shop.dto.MemberFormDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Getter @Setter
@Entity
public class Member extends BaseEntity {
	
	@Column(name="member_id")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Embedded
	private Address address;
	
	
	private boolean isDeleted; // Default : false(0)  탈퇴 여부 확인  TRUE : 탈퇴신청한회원 -> 로직생성시 탈퇴여부까지 확인해야됨
	
	private boolean isSocial;
	
	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Faq> faq = new ArrayList<>();
	
	
	@Builder.Default
	@Enumerated(EnumType.STRING) //DB에 저장시 문자여롤 저장할때 적용
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roleSet = new HashSet<>();
	
	
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		memberFormDto.passEncode(passwordEncoder);
		Member member = memberFormDto.toMemberEntity().addRole(Role.ADMIN).addRole(Role.USER);
		return member;
	}

	

	public Member socialUpdate(String name, String password) {
		this.name=name;
		this.password=password;
		return this;
	}
	
	public Member addRole(Role role) {
		roleSet.add(role);
		return this;
	}
	public Member removeRole(Role role) {
		roleSet.remove(role);
		return this;
	}

	


}
