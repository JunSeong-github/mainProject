# ERP Demo Project (Spring Boot + React + Docker)

포트폴리오용으로 개발 중인 **ERP 데모 프로젝트**입니다.  
Spring Boot 백엔드와 React 프론트엔드로 구현하며, Docker & GitHub Actions를 활용한 CI/CD 파이프라인까지 설정했습니다.  
ERP에서 자주 사용되는 **구매, 판매, 입고, 출고, 재고 관리** 기능을 단계적으로 구현하고 있습니다.

---

## 🚀 현재 구현된 기능

### 📌 공통
- **Spring Boot 3.x / JPA** 기반 백엔드
- **React + Vite + TypeScript** 기반 프론트엔드
- **MySQL 8.0** 데이터베이스 연결
- **Docker Compose** 환경 구성 (backend / frontend / mysql 컨테이너 실행)
- **GitHub Actions** CI 파이프라인 (빌드 및 Docker 이미지 생성 자동화)

### 📌 게시판 모듈 (CRUD 학습용)
- 게시글 등록 / 조회 / 수정 / 삭제 API
- React 프론트엔드에서 게시글 리스트 및 작성/수정 화면 연결 완료

### 📌 ERP 모듈
- **구매(PO, Purchase Order)**
  - 발주 생성 (`POCreateRequest`)
  - 발주 조회 / 승인 API
- **판매(SO, Sales Order)**
  - 판매주문 등록 DTO/서비스 기본 구조 생성
- **입고(GRN, Goods Receipt Note)**
  - 입고 생성 요청 DTO 정의
  - 서비스/리포지토리 기본 구조 작성
- **출고(Shipment)**
  - 출고 요청 DTO / 서비스 뼈대 작성
- **재고(Inventory)**
  - 품목(Item) 등록 / 조회 DTO 및 서비스 작성
- **창고(Warehouse)**
  - 창고 엔티티 및 기본 관리 기능 뼈대 작성

---

## 🛠️ 앞으로 할 작업 (To Do)

- **ERP 서비스 완성**
  - Sales Order → Shipment → Inventory 흐름 연결
  - GRN(입고)과 PO(발주) 연계 처리
  - 상태 전환 로직 (예: 발주 → 승인 → 입고 처리)
- **프론트엔드 ERP 화면**
  - ERP 메뉴/네비게이션 UI
  - 발주/입고/출고/재고 관리 페이지
  - React Query를 이용한 API 연동
- **인증/권한**
  - Spring Security + JWT 기반 로그인/권한 처리
- **테스트**
  - JUnit + H2 DB 기반 단위/통합 테스트 작성
- **배포**
  - Docker Hub / 클라우드 서버 배포 (CI/CD 확장)

---

## 📦 기술 스택

- **Backend**: Java 17, Spring Boot, Spring Data JPA, Hibernate, Lombok
- **Frontend**: React, TypeScript, Vite, React Query, React Router
- **Database**: MySQL 8.0
- **Infra**: Docker, Docker Compose, GitHub Actions
- **Tools**: IntelliJ IDEA, Git, GitHub

---

<pre> ## 📌 프로젝트 구조 (일부) ``` src/main/java/com/example/mainproject 
  ├─ api │ 
  ├─ post (게시판 모듈) 
  │ └─ erp 
  │ ├─ controller (ERP API 컨트롤러) 
  │ ├─ dto (요청/응답 DTO) 
  │ └─ service (비즈니스 로직) 
  ├─ domain (엔티티) 
  ├─ repository (JPA 리포지토리) 
  └─ application (Spring Boot 메인) ``` </pre>


---

## 🗓️ 진행 현황 요약

- ✅ 게시판 CRUD + 프론트 연결 완료  
- ✅ ERP 기본 도메인(Purchase, Sales, GRN, Shipment, Inventory, Warehouse) 설계 완료  
- ✅ Docker Compose 실행 환경 구축 (백엔드+프론트+DB)  
- ✅ GitHub Actions CI 빌드 파이프라인 설정 완료  
- 🔄 ERP 서비스 상세 로직/연동 개발 중  
- 🔄 ERP 화면 프론트엔드 연동 작업 예정  
- 🔄 JWT 인증/권한 처리 예정  
- 🔄 테스트 코드 및 서버 배포 예정  

