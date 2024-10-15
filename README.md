# Secret Diary (Back-end)

해당 프로젝트의 Front-end 코드에 대한 내용은 [Secret_Diary_Front_End](https://github.com/junseop619/SecretDiary_Front_End)을 참조해주세요

<br></br>

# 목차
1. 소개글

    1-1. 프로젝트 소개

    1-2. 사용 기술

    1-3. 사용 환경

2. Database structure

3. Service structure

    3-1. User service

    3-2. Security structure

    &emsp;3-2-1. 기능 구조도

    &emsp;3-2-2. 각 기능에 대한 설명

    &emsp;3-2-3. 현재 제공하고 있는 service


    3-3. Notice service

    3-4. Friend structure


5. 기능 구현 참고 문서(본인 블로그)

    4-1. ㅁㅁㅁㅁㅁㅁ

    4-2. ㅁㅁㅁㅁㅁㅁ

<br></br>

---

# 1. 소개글

## 1-1. 프로젝트 소개

[Secret_Diary_Front_End](https://github.com/junseop619/SecretDiary_Front_End) 의 서버 기능 구현을 위한 Back-end 프로젝트로 Spring을 이용하여 구현하였습니다.

<br></br>

## 1-2. 사용 기술

![js](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![js](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![js](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white) ![js](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) ![js](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

<br></br>

## 1-3. 사용 환경

![js](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

<br></br>

# 2. Database structure

![SecretDiary_DB](https://github.com/user-attachments/assets/abb890c9-7f6e-41cc-be06-84f64a0be136)

<br></br>

# 3. Service structure

저의 Secret Diary 프로젝트에서 사용하고 있는 Spring에서 데이터를 주고받는 구조를 계층형 아키텍쳐(Layered Architecture)를 통해 보여드리겠습니다.

첫 번째 Layered Architecture

![DO1](https://github.com/user-attachments/assets/9a1f364d-d791-42a3-b599-f8159ad1d577)

두 번째 Layered Architecture

![DO2](https://github.com/user-attachments/assets/300c9dd0-0bf4-4044-8764-72a3481c13b9)

현재 저의 프로젝트에서는 2개의 아키텍쳐를 사용중입니다.

첫 번째 아키텍쳐의 경우 service와 Repository가 Entity를 통해 직접적으로 data를 주고받는 아키텍쳐입니다.

두 번째 아키텍쳐의 경우 service와 DB 사이에 DAO 계층이 중간에 존재하여 data를 주고받는 아키텍쳐입니다.

첫 번째 아키텍쳐의 경우 간결하고 직관적이나 확장성의 문제를 갖고 있습니다.

두 번째 아키텍쳐의 경우 비즈니스 로직과 데이터 접근 로직을 분리하여 유지보수성과 확장성을 높일 수 있으나 복잡성이 증가한다는 문제를 갖고 있습니다.

해당 프로젝트에서 대부분 첫 번째 아키텍쳐를 선정하였으나 일부 기능에 대해서는 두 번째 아키텍쳐를 선택하여 구현하였습니다. 

각 기능 구현에 있어서 아키텍쳐 선정 기준의 경우 2 case 모두 보여주기 위한 이유였습니다.

<br></br>

## 3-1. User service

User controller를 통해 제공하고 있는 기능입니다. 해당 기능 구현 방식에 대해서는 항목 ~을 참고해주시길 바랍니다.

- 회원가입

- 로그인

- 자동 로그인

- User 프로필 정보(image) 변경

- 로그아웃

- 내 정보 가져오기

- 회원 탈퇴

<br></br>

## 3-2. Security structure

> ## 3-2-1. 기능 구조도

![security_structure](https://github.com/user-attachments/assets/8a45e8ca-c07b-4990-8b1a-900a6525b623)

<br></br>

> ## 3-2-2. 각 기능에 대한 설명

(blog link)

<br></br>

> ## 3-2-3. 현재 제공하고 있는 service

- auto login, 헤다만 달면 현재 코드에서도 추가적을로 authuntication 가능하다 뭐시기

<br></br>

## 3-3. Notice service

Notice controller를 통해 제공하고 있는 기능입니다. 해당 기능 구현 방식에 대해서는 항목 ~을 참고해주시길 바랍니다.

- 게시물 등록하기

- 내 게시물 리스트 보기

- 친구 게시물 리스트 보기

- 내 게시물 검색하기

- 특정 게시물 자세히 보기

<br></br>

## 3-4. Friend structure

![friend_structure](https://github.com/user-attachments/assets/98443e87-add7-42ac-89e3-8b2867e9b689)

위 사진은 친구관리와 친구 요청을 도식화한 구조도 입니다.

아래는 Friend controller를 통해 제공하고 있는 기능입니다. 해당 기능 구현 방식에 대해서는 항목 ~를 참고해주시길 바랍니다.

- 친구 요청 보내기

- 친구 요청 목록 보기

- 친구 요청 수락하기

- 내 친구 보기

- 내 친구 검색

- 친구 검증 (친구 여부 확인)

<br></br>

# 4. 사용 기술



