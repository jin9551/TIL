# JWT에서 클레임(Claim)이란?

JWT에서 \*\*클레임(Claim)\*\*은
토큰 안에 들어있는 **속성(정보)과 그 값**을 의미합니다.

쉽게 말해,

> "이 토큰이 무엇을 나타내는지"
> "누가, 언제, 어떤 조건으로 발급했는지"
> "어떤 권한을 갖는지"
> 같은 내용을 key-value 형태로 담아둔 데이터입니다.

---

### 🔹 클레임의 종류

1. **등록(Registered) 클레임** – JWT 표준에서 정의된 필드

   * `iss` (issuer) : 발급자
   * `sub` (subject) : 토큰 주체(예: userId)
   * `iat` (issued at) : 발급 시각
   * `exp` (expiration) : 만료 시각
   * `aud` (audience) : 대상(토큰을 사용할 주체)

2. **공개(Public) 클레임** – 표준 외, 합의된 공개 필드

   * 예: `userRegion`, `userEnvironment`

3. **비공개(Private) 클레임** – 서비스 내부에서만 쓰는 커스텀 필드

   * 예: `permissions`, `mobileId`, `parentUserId`

---

즉,
클레임은 **JWT의 Payload 부분에 담겨 있는, 토큰의 의미와 사용 목적을 설명하는 데이터 집합**입니다.
검증 시 서버는 이 클레임을 읽어

* **이 토큰이 유효한지**
* **누구를 위한 것인지**
* **어떤 권한이 있는지**
  등을 판단합니다.

---
