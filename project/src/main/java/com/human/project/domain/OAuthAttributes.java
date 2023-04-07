package com.human.project.domain;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class OAuthAttributes {
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	private String id;
	private String nickname;
	private String accessToken;
	private String registrationId;
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
			String picture, String id, String nickname, String socialType) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.id = id;
		this.nickname = nickname;
		this.accessToken = accessToken;
		this.registrationId = registrationId;
	}
	

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		if("naver".equals(registrationId)) {
			return ofNaver("id", attributes);
        }
		else if("kakao".equals(registrationId)) {
			return ofKakao(userNameAttributeName, attributes);
		}
        else {
        	return ofGoogle(userNameAttributeName, attributes);
        }

	}


	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		System.out.println("ofKakao=="+attributes);
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
		
		log.info("kakaoAccount : " + kakaoAccount);
		log.info("profile : " + profile);
		log.info("thumbnail_image_url : " + profile.get("thumbnail_image_url"));

		return OAuthAttributes.builder()
							  .name((String) profile.get("nickname"))
							  .email((String) kakaoAccount.get("email") )
							  .id( String.valueOf( attributes.get(userNameAttributeName) ) )
							  .picture((String) profile.get("thumbnail_image_url") )
							  .attributes(attributes)
							  .nameAttributeKey(userNameAttributeName)
							  .build();
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		System.out.println("ofNaver=="+attributes);
		Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("response");


		return OAuthAttributes.builder()
							  .name((String) naverAccount.get("name"))
							  .email((String) naverAccount.get("email") )
							  .picture((String) naverAccount.get("profile_image") )
							  .attributes(naverAccount)
							  .nameAttributeKey(userNameAttributeName)
							  .build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
		
		log.info("kakaoAccount : " + kakaoAccount);
		log.info("profile : " + profile);
		log.info("thumbnail_image_url : " + profile.get("thumbnail_image_url"));

		return OAuthAttributes.builder()
							  .name((String) profile.get("nickname"))
							  .email((String) kakaoAccount.get("email") )
							  .id( String.valueOf( attributes.get(userNameAttributeName) ) )
							  .picture((String) profile.get("thumbnail_image_url") )
							  .attributes(attributes)
							  .nameAttributeKey(userNameAttributeName)
							  .build();
	}
}