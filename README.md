# 오늘의집 클론 서비스

> 항해99 4조 클론 서비스 프로젝트

## 👪 팀원
- [유재성](https://github.com/Peter-Yu-0402)
- [유하정](https://github.com/yuha00e)
- [김수환](https://github.com/openmpy)

## 🔧 구현 기능

- [x] 회원 가입 기능
    - 이메일, 닉네임 중복 검사 기능
- [x] 로그인 기능
    - Access Token 발행
    - Refresh Token 발행
- [x] Refresh Token 재발행 기능
- [x] 로그아웃 기능
- [x] 상품 기능
    - 오늘의집 상품 1시간 간격으로 크롤링 기능
    - 실시간 상품 TOP10 인기 검색어 기능
    - 상품 목록 페이징 기능
    - 선택 상품 조회 기능
    - 상품 검색 기능
- [x] 북마크 기능
    - 추가, 삭제, 조회 기능
    - 목록 페이징 기능
- [x] 리뷰 기능
    - 리뷰 CRUD 기능

## 📚 스택

- JDK 17
- Spring Boot 3.1.9
- Spring Boot JPA
- Spring Boot Validation
- Spring Boot Security
- Swagger UI
- JWT
- Elastic Beanstalk, EC2, RDS
- MySQL
- Redis
- Github Actions
- Selenium

## 🔖 ERD

```mermaid
erDiagram
    Member {
        int id PK "ID"
        string email "이메일"
        string password "비밀번호"
        string nickname "닉네임"
        string role "권한"
        date created_at "생성일자"
        date modified_at "수정일자"
    }

    Product {
        int id PK "ID"
        string brand "브랜드"
        string name "상품명"
        long price "가격"
        double discount "할인률"
        string image_url "이미지"
        boolean is_free_delivery "무료배송"
        boolean is_special_price "특가"
        date created_at "생성일자"
        date modified_at "수정일자"
    }

    Review {
        int id PK "ID"
        int member_id FK "회원 ID"
        int product_id FK "상품 ID"
        string contents "내용"
        int rating "평점"
        boolean deleted "삭제 여부"
        date created_at "생성일자"
        date modified_at "수정일자"
    }

    Bookmark {
        int id PK "ID"
        int member_id FK "회원 ID"
        int product_id FK "상품 ID"
        boolean deleted "삭제 여부"
        date created_at "생성일자"
        date modified_at "수정일자"
    }

    Member ||--o{ Product: ""
    Member ||--o{ Review: ""
    Member ||--o{ Bookmark: ""
    Product ||--o{ Review: ""
    Product ||--o{ Bookmark: ""
```

## 📄 API 명세서

URL: http://hanghae-4.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html

<img width="727" alt="스크린샷 2024-03-25 오후 7 18 56" src="https://github.com/openmpy/bucketplace-clone/assets/150704638/7c198b52-1011-42a1-a6e1-e17634a930b6">

## 📐 시스템 아키텍처

![Blank diagram - Page 1 (12)](https://github.com/openmpy/bucketplace-clone/assets/150704638/eabecd72-b965-442d-842f-9ab1885d1ccf)

