# 프로젝트: Last Carnival
![index](https://user-images.githubusercontent.com/120995522/230261320-2809c8f6-0e18-4b42-8576-9913432e5833.PNG)


## 주제
- 해외 음원 차트가 포함된 웹 사이트 커뮤니티

## 프로젝트 배경
- 현재 대부분의 국내 음원 사이트들은 해외 음원을 다루지 않고 있음
- 국내 음원 사이트 이용자들의 경우, 해외 차트도 이용하길 원하는 needs가 존재하는 것을 확인
- 해외 음원 차트 기능이 포함 된 커뮤니티의 필요성을 통해 본 프로젝트를 진행

### 팀 구성
- 인원 : 4명
- 주요 업무 : 

## 프로젝트 기간
- 2023-03-23 ~ 2023-04-04


## 개발 환경
- 사용 언어
  + <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/SQL-F80000?style=flat&logo=SQL&logoColor=white"> <img src="https://img.shields.io/badge/html-E34F26?style=flat&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=flat&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/jquery-0769AD?style=flat&logo=jquery&logoColor=white">
- 프레임워크
  + <img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat&logo=bootstrap&logoColor=white"> <img src="https://img.shields.io/badge/springboot 2-6DB33F?style=flat&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity 5-6DB33F?style=flat&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis 3-6DB33F?style=flat&logo=MyBatis&logoColor=white"> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white">
- 사용 Tools
  + <img src="https://img.shields.io/badge/eclipseide:4.25.0-2C2255?style=flat&logo=eclipseide&logoColor=white"/> <img src="https://img.shields.io/badge/openjdk:17.0.4.1-686767?style=flat&logo=openjdk&logoColor=black"/> <img src="https://img.shields.io/badge/visualstudiocode:1.74.1-007ACC?style=flat&logo=visualstudiocode&logoColor=white"/> <img src="https://img.shields.io/badge/mysql:8.0.31-4479A1?style=flat&logo=mysql&logoColor=white"/>
- 라이브러리
  + <img src="https://img.shields.io/badge/Lombok-6DB33F?style=flat&logo=Lombok&logoColor=white"> <img src="https://img.shields.io/badge/Validation-6DB33F?style=flat&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Devtools-6DB33F?style=flat&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/OkHttp-6DB33F?style=flat&logo=OkHttp&logoColor=white"> <img src="https://img.shields.io/badge/commons.io-D22128?style=flat&logo=apache&logoColor=white"> <img src="https://img.shields.io/badge/commons.codec-D22128?style=flat&logo=apache&logoColor=white"> <img src="https://img.shields.io/badge/Guava-4285F4?style=flat&logo=google&logoColor=white">
- 사용 DB : <img src="https://img.shields.io/badge/mysql:8.0.31-4479A1?style=flat&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/mongodb-47A248?style=flat&logo=mongodb&logoColor=white"/>
- 참조 API : <img src="https://img.shields.io/badge/lastdotfm-D51007?style=flat&logo=lastdotfm&logoColor=white"/> <img src="https://img.shields.io/badge/youtube-FF0000?style=flat&logo=youtube&logoColor=white"/> <img src="https://img.shields.io/badge/kakao-FFCD00?style=flat&logo=kakao&logoColor=white"/>
- 협업 Tools : <img src="https://img.shields.io/badge/slack-4A154B?style=flat&logo=slack&logoColor=white"/> <img src="https://img.shields.io/badge/trello-0052CC?style=flat&logo=trello&logoColor=white"/>
- 웹 IDE : <img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white"/>


## 요구사항 정의서
<< 요구사항 정의서 이미지 >>

## ERD
![그림1](https://user-images.githubusercontent.com/120995522/230264333-8c11f3b6-e373-4134-af9e-8acc661e93be.png)


## 주요 기능
- 게시판
- 소셜 회원가입(카카오)
- 관리자 페이지


## 프로젝트 리뷰
<details>
<summary><h3>메인 화면</h3></summary>
<div markdown="1">

- 해외 음원 차트 50개를 보여 줌
![image](https://user-images.githubusercontent.com/120995529/230283352-916d974b-8bd7-46d4-9dc3-83ddd537aa73.png)

- 앨범 이미지, 유튜브 이미지 눌렀을 때 유튜브 링크로 이동
![youtubebutton-1](https://user-images.githubusercontent.com/120995529/230284511-ee522bc3-144a-49ab-acf0-a92e92415fbf.png)
</div>
</details>

***

<details>
<summary><h3>회원가입</h3></summary>
<div markdown="1">

- 중복확인 한개라도 수행 안할 시 등록이 안됨
![회원가입중복확인X](https://user-images.githubusercontent.com/120995529/230286925-78f61c08-d589-4ba3-aef9-e4c7a6e4fc83.png)

- 유효성 검사
![회원가입유효성검사](https://user-images.githubusercontent.com/120995529/230287011-79be95aa-6ce0-4e30-bb70-b4b3442b10f8.png)

- 회원가입 완료 시 자동 로그인
![회원가입후자동로그인1](https://user-images.githubusercontent.com/120995529/230287394-42d1ad03-acf2-40dd-88b2-886e993147ff.png)
</div>
</details>

***

<details>
<summary><h3>프로필</h3></summary>
<div markdown="1">

![프로필](https://user-images.githubusercontent.com/120995529/230287658-a474c314-aade-43b4-a54f-25b1adce236f.png)

- 내 정보 수정
![개인정보수정](https://user-images.githubusercontent.com/120995529/230287823-183caf22-a069-40d0-bd98-b2776e71bd0c.png)

  - 이메일 변경 시 중복확인 및 유효성 검사
  ![이메일변경](https://user-images.githubusercontent.com/120995529/230290211-f978c6d4-1bef-459a-a8c0-5e4f26c2026f.jpg)


  - 비밀번호 변경 시 유효성 검사
  ![비밀번호변경휴요성](https://user-images.githubusercontent.com/120995529/230287999-6a4df00d-eccf-4e8a-8602-640e2aafd713.png)
</div>
</details>

***

<details>
<summary><h3>로그인</h3></summary>
<div markdown="1">

- 아이디 저장 및 자동 로그인 기능
  - 아이디 저장 : 로그인 시 아이디 자동 입력
- 카카오 로그인
![카카오로그인](https://user-images.githubusercontent.com/120995529/230291839-393ea344-22f5-48b9-bafa-98acf5e455e1.png)
</div>
</details>

***

<details>
<summary><h3>아이디 / 비밀번호 찾기</h3></summary>
<div markdown="1">

- 비밀번호 찾기 수행 시 비밀번호는 임시 비밀번호로 변경
![아이디비밀번호찾기](https://user-images.githubusercontent.com/120995529/230292688-2bff2a72-ed7f-4a5b-a2ec-d51f2a49af61.jpg)
</div>
</details>

***

<details>
<summary><h3>커뮤니티</h3></summary>
<div markdown="1">

- 로그인 여부
![커뮤니티로그인여부](https://user-images.githubusercontent.com/120995529/230293673-de7e9b26-45b4-4430-b69c-7160262494da.jpg)

- 게시글 및 댓글 
![커뮤니티](https://user-images.githubusercontent.com/120995529/230296940-9c449c10-8336-4728-a790-c3a0a280dbf5.jpg)
</div>
</details>

***

<details>
<summary><h3>관리자</h3></summary>
<div markdown="1">

- 회원관리
![회원 관리 - 관리자](https://user-images.githubusercontent.com/120995529/230303135-51701790-0ca4-4318-a178-1f30f0c385d0.png)

- 닉네임 변경 및 회원 삭제
![관리자](https://user-images.githubusercontent.com/120995529/230302072-94051e3b-d8c1-4725-a58b-7212e62b8d7f.jpg)

- 권한 관리
![회원 권한 관리 - 관리자](https://user-images.githubusercontent.com/120995529/230303344-ae97d43b-f4f0-44ec-8336-ff0eb4b082ad.png)

- 게시물 관리
![게시글 보기+댓글 - 관리자](https://user-images.githubusercontent.com/120995529/230303254-7edf9e50-f3a1-43ac-aec9-f2c40f323240.png)
</div>
</details>

***


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

<img src="https://github.com/ALOHA-CLASS/java_python_ai_01/blob/main/project/portfolio/mybatis.png" width="24" height="24">
Mybatis


![마이바티스](https://github.com/ALOHA-CLASS/java_python_ai_01/blob/main/project/portfolio/mybatis.png)
