package com.shyun.shop.dto;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shyun.shop.entity.Address;
import com.shyun.shop.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto {
	
	@NotEmpty(message = "이메일은 필수 입력값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotBlank(message = "이름은 필수 입력값입니다.")
	private String name;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min=8, max=16, message = "비밀번호는 8자이상, 16자 이하로 입력해주세요.")
	private String password;
	
	
	private String roadAddress;
	private String postCode;
	private String detailAddress;
	
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
	private String phone;
	
	
	public MemberFormDto passEncode(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
		return this;
	}
	
	public Member toMemberEntity() {
		return Member.builder()
				.email(email)
				.name(name)
				.password(password)
				.address(Address.builder().postCode(postCode).detailAddress(detailAddress).roadAddress(roadAddress).build())
				.build();
	}
	
	
}

