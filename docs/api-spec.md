# API 목록

## 인증 및 인가

| 기능   | 메서드  | URI         | Request Body  | 인증 필요 여부 | Response Body  |
| ---- | ---- | ----------- | ------------- | -------- | -------------- |
| 회원가입 | POST | /signup     | SignUpRequest | X        | SignUpResponse |
| 로그인  | POST | /auth/login | LoginRequest  | O        | LoginResponse  |

---

## 내 정보 + 내 게시글

| 기능    | 메서드 | URI            | Request Body | 인증 필요 여부 | Response Body   |
| ----- | --- | -------------- | ------------ | -------- | --------------- |
| 마이페이지 | GET | /auth/me/      | X            | O        | MyPageResponse  |
| 내 게시글 | GET | /auth/me/posts | X            | O        | MyPostsResponse |

---

## 게시글 관리

| 기능     | 메서드    | URI             | Request Body | 인증 필요 여부 | Response Body          |
| ------ | ------ | --------------- | ------------ | -------- | ---------------------- |
| 게시글 목록 | GET    | /posts          | X            | X        | List[PostInfoResponse] |
| 게시글 상세 | GET    | /posts/{postId} | X            | X        | PostInfoResponse       |
| 게시글 작성 | POST   | /posts          | PostRequest  | O        | X                      |
| 게시글 수정 | PATCH  | /posts/{postId} | PostRequest  | O        | X                      |
| 게시글 삭제 | DELETE | /posts/{postId} | X            | O        | X                      |

---

## 댓글

| 기능    | 메서드    | URI                     | Request Body   | 인증 필요 여부 | Response Body             |
| ----- | ------ | ----------------------- | -------------- | -------- | ------------------------- |
| 댓글 조회 | GET    | /post/{postId}/comments | X              | X        | List[CommentInfoResponse] |
| 댓글 작성 | POST   | /post/{postId}/comments | CommentRequest | O        | X                         |
| 댓글 삭제 | DELETE | /comments/{commentsId}  | X              | O        | X                         |

---

## 좋아요

| 기능     | 메서드  | URI                            | Request Body | 인증 필요 여부 | Response Body |
| ------ | ---- | ------------------------------ | ------------ | -------- | ------------- |
| 좋아요 추가 | POST | /posts/{postId}/likes          | X            | O        | X             |
| 좋아요 삭제 | POST | /posts/{postId}/likes/{likeId} | X            | O        | X             |

# API 명세서

## 1. 인증 및 인가

### 회원가입

**POST /signup**

#### Request

```json
{
  "username": "string",
  "password": "string",
  "email": "string"
}
```

#### Response

```json
{
  "userId": "number",
  "username": "string",
  "createdAt": "datetime"
}
```

---

### 로그인

**POST /auth/login**

#### Request

```json
{
  "id": "string",
  "password": "string"
}
```

#### Response

```json
{
  "access_token": "string"
}
```

---

## 2. 내 정보 + 내 게시글

### 마이페이지

**GET /auth/me/**

#### Response

```json
{
  "userId": "number",
  "username": "string",
  "email": "string",
  "createdAt": "datetime"
}
```

---

### 내 게시글

**GET /auth/me/posts**

#### Response

```json
{
  "posts": [
    {
      "postId": "number",
      "content": "string",
      "createdAt": "datetime",
      "likes": "number",
      "comments": "number"
    }
  ]
}
```

---

## 3. 게시글 관리

### 게시글 목록

**GET /posts**

#### Response

```json
{
  "posts": [
    {
      "postId": "number",
      "author": "string",
      "content": "string",
      "createdAt": "datetime",
      "likes": "number",
      "comments": "number"
    }
  ]
}
```

---

### 게시글 상세

**GET /posts/{postId}**

#### Response

```json
{
  "postId": "number",
  "author": "string",
  "content": "string",
  "createdAt": "datetime",
  "likes": "number",
  "comments": "number"
}
```

---

### 게시글 작성

**POST /posts**

#### Request

```json
{
  "content": "string"
}
```

---

### 게시글 수정

**PATCH /posts/{postId}**

#### Request

```json
{
  "content": "string"
}
```

---

### 게시글 삭제

**DELETE /posts/{postId}**

---

## 4. 댓글

### 댓글 조회

**GET /post/{postId}/comments**

#### Response

```json
{
  "comments": [
    {
      "commentId": "number",
      "author": "string",
      "content": "string",
      "createdAt": "datetime"
    }
  ]
}
```

---

### 댓글 작성

**POST /post/{postId}/comments**

#### Request

```json
{
  "content": "string"
}
```

---

### 댓글 삭제

**DELETE /comments/{commentsId}**

---

## 5. 좋아요

### 좋아요 추가

**POST /posts/{postId}/likes**

---

### 좋아요 삭제

**POST /posts/{postId}/likes/{likeId}**
