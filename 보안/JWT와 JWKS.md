# JWT와 JWKS

JWT와 JWKS는 보안 토큰 기반 인증에서 자주 같이 쓰이지만, 개념과 역할이 다릅니다.
정리하면 **JWT는 토큰 그 자체**, **JWKS는 토큰의 서명을 검증하는 데 쓰이는 공개키 집합**입니다.

---

## 1. JWT (JSON Web Token)

JWT는 **JSON 데이터를 안전하게 전달하기 위한 토큰 포맷**입니다.
주로 **사용자 인증 정보**나 \*\*권한(permissions)\*\*을 담아 서버 간 주고받습니다.

### 구조

JWT는 `Header.Payload.Signature` 형식의 세 부분으로 구성됩니다.
예:

```
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTYiLCJuYW1lIjoiSm9obiBEb2UifQ.
Vh7uKu8s8j1...
```

| 구분            | 내용                                       |
| ------------- | ---------------------------------------- |
| **Header**    | 토큰 타입(JWT)과 서명 알고리즘(RS256, HS256 등)      |
| **Payload**   | 클레임(Claim)이라고 부르는 실제 데이터(사용자 ID, 만료시간 등) |
| **Signature** | Header+Payload를 비밀키로 서명한 값. 변조 방지용       |

---

## 2. JWKS (JSON Web Key Set)

JWKS는 \*\*JWT의 서명을 검증하기 위한 공개키 모음(JSON 형식)\*\*입니다.
서버는 JWT를 발급할 때 \*\*비밀키(private key)\*\*로 서명하고,
검증하려는 쪽은 JWKS에 있는 \*\*공개키(public key)\*\*를 이용해 서명이 맞는지 확인합니다.

### 특징

* JWKS URL 예시:

  ```
  https://auth.example.com/.well-known/jwks.json
  ```
* JSON 구조 예시:

  ```json
  {
    "keys": [
      {
        "kty": "RSA",
        "kid": "abc123",
        "use": "sig",
        "n": "0vx7agoebGcQSuuPiL...",
        "e": "AQAB",
        "alg": "RS256"
      }
    ]
  }
  ```
* `kid`(Key ID)로 어떤 키를 써야 하는지 식별 가능

---

## 3. JWT + JWKS 관계

1. **발급**: 인증 서버가 JWT를 발급 (비밀키로 서명)
2. **전달**: 클라이언트가 이 JWT를 API 요청 시 Authorization 헤더에 첨부
3. **검증**: API 서버가 JWKS URL에서 공개키를 가져와 JWT 서명 검증
4. **신뢰**: 서명이 유효하면 Payload의 클레임을 신뢰하고 로직 수행

---

## 4. 그림 예시 (흐름)

```
[Auth Server] --(private key 서명)--> JWT 발급
     |
     v
[Client] --(Authorization: Bearer JWT)--> [API Server]
     |
     v
[API Server] --(JWKS URL)--> 공개키 가져오기 --> JWT 서명 검증
```

---

원하시면 제가 이걸 **JWT 구조도 + JWKS 동작 흐름**을 시각화해서 그려 드릴 수도 있습니다.
그렇게 하면 더 직관적으로 이해될 거예요.
그림으로 그려 드릴까요?
