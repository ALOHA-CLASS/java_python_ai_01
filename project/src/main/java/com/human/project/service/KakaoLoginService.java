package com.human.project.service;

import com.human.project.domain.OAuthToken;
import com.human.project.domain.Users;

public interface KakaoLoginService {

    /**
     * 인증토큰 요청하기
     * @param code : 인가코드
     * @return
     */
    public OAuthToken getAccessToken(String code) throws Exception;

    public Users getUserInfo(String accessToken) throws Exception;
    
}
