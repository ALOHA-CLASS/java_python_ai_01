package com.human.project.domain;

import java.util.Map;

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
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
			String picture, String id) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.id = id;
	}
	

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		
		return ofKakao(userNameAttributeName, attributes);
		
	}


	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		
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

