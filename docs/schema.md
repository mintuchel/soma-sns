# mini-sns 데이터베이스 스키마

## 2. 도메인 개요

| 엔티티 | 설명 |
|--------|------|
| **Member** | 서비스 사용자. 로그인 식별자는 `id`(문자열 PK). |
| **Post** | 회원이 작성한 게시물. UUID PK. |
| **Like** | 게시물에 대한 회원별 좋아요(중복 불가, 복합 PK). |
| **Comment** | 게시물에 달린 댓글. UUID PK. |
---

## 4. 테이블 정의

### 4.1 `members`

회원 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `VARCHAR(20)` | NO | — | PK | 로그인/외부 식별자 겸 PK |
| `password` | `CHAR(64)` | NO | — | — | 해시 길이 고정 가정(예: SHA-256 hex) 시 그에 맞춤 |
| `email` | `VARCHAR(255)` | NO | — | **UNIQUE** | 중복 가입 방지 |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 생성 시각 |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 수정 시각(앱에서 갱신 필요) |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 탈퇴/비활성 등 소프트 삭제 |

**인덱스·제약(마이그레이션 기준):** PK(`id`), UNIQUE(`email`).

---

### 4.2 `posts`

게시글 테이블

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `UUID` | NO | `gen_random_uuid()` | PK | 서버 기본 생성 |
| `member_id` | `VARCHAR(20)` | NO | — | FK → `members(id)` | `ON DELETE CASCADE` |
| `description` | `TEXT` | YES | — | — | 본문/설명 |
| `image_url` | `TEXT` | YES | — | — | 이미지 URL |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 소프트 삭제 |

**인덱스·제약:** PK(`id`), FK `member_id` → `members(id)` **ON DELETE CASCADE**.

---

### 4.3 `likes`

- 좋아요 테이블
- 회원 삭제 시 댓글은 **CASCADE**로 삭제된다.

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `post_id` | `UUID` | NO | — | PK, FK → `posts(id)` | `ON DELETE CASCADE` |
| `member_id` | `VARCHAR(20)` | NO | — | PK, FK → `members(id)` | `ON DELETE CASCADE` |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | 좋아요 시각 |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |

**인덱스·제약:** 복합 PK(`post_id`, `member_id`), 양쪽 FK **ON DELETE CASCADE**.

---

### 4.4 `comments`

- 댓글 테이블
- 게시물 또는 회원 삭제 시 댓글은 **CASCADE**로 삭제된다.

| 컬럼 | 타입 | NULL | 기본값 | 제약 | 비고 |
|------|------|------|--------|------|------|
| `id` | `UUID` | NO | `gen_random_uuid()` | PK | |
| `post_id` | `UUID` | NO | — | FK → `posts(id)` | `ON DELETE CASCADE` |
| `member_id` | `VARCHAR(20)` | NO | — | FK → `members(id)` | `ON DELETE CASCADE` |
| `content` | `TEXT` | NO | — | — | 댓글 본문 |
| `created_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `updated_at` | `TIMESTAMP` | NO | `CURRENT_TIMESTAMP` | — | |
| `deleted_at` | `TIMESTAMP` | YES | — | — | 소프트 삭제 |

**인덱스·제약:** PK(`id`), FK `post_id`, FK `member_id` 모두 **ON DELETE CASCADE**.

## 7. 마이그레이션 출처

정의는 다음 파일과 일치해야 한다.

- `src/main/resources/db/migration/V1__init_schema.sql`

불일치 시 **DDL(마이그레이션)을 우선**하고, 본 문서를 따라잡는다.
