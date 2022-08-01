package com.shyun.shop.config;

import java.util.Map;
import java.util.Optional;
import java.util.jar.Attributes;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyun.shop.constant.Role;
import com.shyun.shop.entity.Member;
import com.shyun.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		//소셜 로그인 인증완료상태...
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		System.out.println("registrationId : " + registrationId);
		//소셜정보를 memberDB저장하는 경우
		//DB저장 
		
		return saveSocialUser(oAuth2User, registrationId);
	}
	private OAuth2User saveSocialUser(OAuth2User oAuth2User, String registrationId) {
		String name=null;
		String email=null;
		String pass=registrationId; //비밀번호 의미없음 그냥 아무값이나 넣는거임
		if(registrationId.equals("google")) {
			name = oAuth2User.getAttribute("name");
			email = oAuth2User.getAttribute("email");
		//	pass= oAuth2User.getAttribute("google");
			/*
			Map<String , Object> userinfo =	oAuth2User.getAttributes();
			
			for(String key : userinfo.keySet()) {
				System.out.println(key + ":" + userinfo.get(key)); //이름은 변수
			}
			*/
			//위에랑 아래랑 같은표현
			
			
//			userinfo.keySet().forEach(key->{
		//	System.out.println(key + ":" + userinfo.get(key)); //이름은 변수
//			});
		}else if(registrationId.equals("naver")) {
			
			
			Map<String,Object> response = oAuth2User.getAttribute("response");
			
			
			name =(String) response.get("name");
			email = (String) response.get("email");
			//pass= (String) response.get("naver");
			
			/*
			 * for(String key : response.keySet()) { System.out.println(key + ":" +
			 * response.get(key)); //이름은 변수 }
			 */
		}else if(registrationId.equals("kakao")) {
			Map<String,Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
			email =(String) kakaoAccount.get("email");
			@SuppressWarnings("unchecked")
			Map<String,Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
			name = (String)profile.get("nickname");
			//pass= registrationId;
		}
		
		Member result = memberRepository.findByEmail(email);
//				.map(e->e.socialUpdate(name,passwordEncoder.encode(pass)))
//				.map(CustomUserDetails::new); //일반회원 또는 소셜회원 존재하는지 체크  
			
		if( result!=null) {
			Member entity  = memberRepository.save(
					result.socialUpdate(name, passwordEncoder.encode(pass)
							)); //업데이트하기 위해서 
			return new CustomUserDetails(entity);
		}
		
		//가입이 안된사람은 소셜로 회원가입
		Member entity = memberRepository.save(Member.builder()
				.email(email)
				.name(name)
				.password(passwordEncoder.encode(pass))
				.isSocial(true)
				.build().addRole(Role.USER));
		
		return new CustomUserDetails(entity);
	}
}
