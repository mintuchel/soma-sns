# mini-sns 데이터베이스 스키마

## 2. 도메인 개요

| 엔티티 | 설명 |
|--------|------|
| **Member** | 서비스 사용자. PK는 `id`(BIGINT, AUTO_INCREMENT), 로그인 식별자는 `name`. |
| **Post** | 회원이 작성한 게시물. BIGINT PK. |
| **Like** | 게시물에 대한 회원별 좋아요(중복 불가, 복합 PK). |
| **Comment** | 게시물에 달린 댓글. BIGINT PK. |

---

## 4. 테이블 정의

### 4.1 `members`

회원 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `BIGINT` | NO | `AUTO_INCREMENT` | PK | 서버 기본 생성 |
| `name` | `VARCHAR(20)` | NO | — | **UNIQUE** | 로그인 식별자 |
| `password` | `CHAR(64)` | NO | — | — | 해시 길이 고정 가정(예: SHA-256 hex) 시 그에 맞춤 |
| `email` | `VARCHAR(255)` | NO | — | **UNIQUE** | 중복 가입 방지 |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 생성 시각 |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 수정 시각(앱에서 갱신 필요) |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 탈퇴/비활성 등 소프트 삭제 |

**인덱스·제약(마이그레이션 기준):** PK(`id`), UNIQUE(`name`), UNIQUE(`email`).

---

### 4.2 `posts`

게시글 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `BIGINT` | NO | `AUTO_INCREMENT` | PK | 서버 기본 생성 |
| `member_id` | `BIGINT` | NO | — | FK → `members(id)` | |
| `author` | `VARCHAR(20)` | NO | — | — | 작성 시점의 `members.name` 스냅샷 |
| `title` | `VARCHAR(100)` | NO | — | — | 게시글 제목 |
| `description` | `TEXT` | NO | — | — | 본문/설명 |
| `image_url` | `TEXT` | YES | — | — | 이미지 URL |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 소프트 삭제 |

**인덱스·제약:** PK(`id`), FK `member_id` → `members(id)`.

---

### 4.3 `likes`

- 좋아요 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `post_id` | `BIGINT` | NO | — | PK, FK → `posts(id)` | |
| `member_id` | `BIGINT` | NO | — | PK, FK → `members(id)` | |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 좋아요 시각 |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |

**인덱스·제약:** 복합 PK(`post_id`, `member_id`).

---

### 4.4 `comments`

- 댓글 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `BIGINT` | NO | `AUTO_INCREMENT` | PK | |
| `post_id` | `BIGINT` | NO | — | FK → `posts(id)` | |
| `member_id` | `BIGINT` | NO | — | FK → `members(id)` | |
| `content` | `TEXT` | NO | — | — | 댓글 본문 |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 소프트 삭제 |

**인덱스·제약:** PK(`id`), FK `post_id`, FK `member_id`.

## 7. 마이그레이션 출처

정의는 다음 파일과 일치해야 한다.

- `src/main/resources/db/migration/V1__init_schema.sql`

불일치 시 **DDL(마이그레이션)을 우선**하고, 본 문서를 따라잡는다.
