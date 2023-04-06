# 프로젝트: Last Carnival
![index](https://user-images.githubusercontent.com/120995522/230261320-2809c8f6-0e18-4b42-8576-9913432e5833.PNG)


## 주제
- 해외 음원 차트가 포함된 웹 사이트 커뮤니티

## 프로젝트 배경
- 현재 대부분의 국내 음원 사이트들은 해외 음원을 다루지 않고 있음
- 국내 음원 사이트 이용자들의 경우, 해외 차트도 이용하길 원하는 needs가 존재하는 것을 확인
- 해외 음원 차트 기능이 포함 된 커뮤니티의 필요성을 통해 본 프로젝트를 진행


## 프로젝트 기간
- 2023-03-23 ~ 2023-04-04


## 개발 환경
- 통합 환경: Eclipse, VsCode
- 사용 언어: Java, JavaScript, HTML, CSS, JQuery
- 기술 스택: MyBatis, Spring boot, Oauth2.0, Spring Security
- DB: MySQL, MongoDB
- 웹 IDE: Github
- 협업 tool: Slack, Trello


## 주요 기능
- 게시판
- 소셜 회원가입(카카오)
- 관리자 페이지


## 프로젝트 리뷰
<< 기능 구현 이미지 >>

## 요구사항 정의서
<< 요구사항 정의서 이미지 >>

## ERD
![그림1](https://user-images.githubusercontent.com/120995522/230264333-8c11f3b6-e373-4134-af9e-8acc661e93be.png)



## 한계
- 같은 이메일의 일반회원 & 소셜회원일 경우 동일 session 부여를 통해 같은 ID로 처리하고자 했으나 Access Token과 같은 추가정보를 불러오는 데 어려움을 겪어 실패하였음
- 회원 탈퇴 시 작성했던 댓글에 대한 처리가 원활하지 못함


## 참조
- 카카오 로그인 document: https://developers.kakao.com/docs/latest/ko/kakaologin/common
- Last.fm API: https://www.last.fm/api
- Youtube API: https://developers.google.com/youtube/v3/getting-started?hl=ko


## 변경점
***
2023-03-24, 기본 UI 구현

2023-03-27, MySQL, MongoDB 연동

2023-03-28, Lastfm API 연결, 차트 페이지 

2023-04-03, 배포
***


![마이바티스](https://github.com/ALOHA-CLASS/java_python_ai_01/blob/main/project/portfolio/mybatis.png)
