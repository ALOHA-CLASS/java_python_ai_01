package com.human.project.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.human.project.domain.OAuthToken;
import com.human.project.domain.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaoServiceImpl implements KakaoLoginService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    @Override
    public OAuthToken getAccessToken(String code) throws Exception {

        // HTTP 요청 헤더 
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 바디
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", restApiKey);
        body.add("redirect_uri", "http://localhost:8080/authorize/kakao");

        body.add("code", code);

        // HTTPEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(body, header);
        

        // RestTemplate (브라우저, Postman 없이 자바코드에서 HTTP 요청)
        RestTemplate restTemplate = new RestTemplate();

        // 요청 및 응답
        String url = "https://kauth.kakao.com/oauth/token";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String accessTokenBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(accessTokenBody, OAuthToken.class);

        return oAuthToken;
    }

    @Override
    public Users getUserInfo(String accessToken) throws Exception {

        // HTTP 요청 헤더 
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTPEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(header);

        // RestTemplate (브라우저, Postman 없이 자바코드에서 HTTP 요청)
        RestTemplate restTemplate = new RestTemplate();

        // 요청 및 응답
        String url = "https://kapi.kakao.com/v2/user/me";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String userInfo = responseEntity.getBody();

        Users user = new Users();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userInfoMap = objectMapper.readValue(userInfo, Map.class);

        String kakaoId = userInfoMap.get("id").toString();
        String name = ((Map<String, String>) userInfoMap.get("properties")).get("nickname");
        String email = ((Map<String, String>) userInfoMap.get("kakao_account")).get("email");
        String thumbnailImage = ((Map<String, String>) userInfoMap.get("properties")).get("thumbnail_image");

        log.info("kakaoId : " + kakaoId);
        log.info("name : " + name);
        log.info("email : " + email);
        log.info("썸네일 : " + thumbnailImage);

        user.setUserId(kakaoId);
        user.setName(name);
        user.setEmail(email);
        // log.info("userInfo : " + userInfo);

        return user;
    }

    
    

    
}
