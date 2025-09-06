# HTTP 매개변수들

Spring MVC 프로젝트에서 API들이 HttpServletRequest, HttpServletResponse, HttpSession 등 HTTP와 관련된 매개변수들을 받아서 사용하는 것을 자주 볼 것이다. 이것들을 언제 혹은 왜 받아서 쓰는지 모르겠어서 이유를 찾아보았다.

---

## 1. `HttpServletRequest`

### 이유

* 클라이언트(브라우저)가 보낸 **HTTP 요청 전체**를 표현하는 객체입니다.
* 헤더, 파라미터, 쿠키, 바디(InputStream) 등 모든 정보를 다룰 수 있습니다.

### 사용하는 경우

* `@RequestParam`, `@PathVariable`, `@ModelAttribute` 같은 스프링 어노테이션으로 매핑하기 힘든 **raw 데이터 접근**이 필요할 때

  * 예: multipart 파일 업로드 처리, 요청 헤더 직접 읽기
* 파라미터를 루프 돌면서 직접 꺼내야 할 때

  ```java
  @GetMapping("/raw")
  public String raw(HttpServletRequest request) {
      String userAgent = request.getHeader("User-Agent");
      return "UA: " + userAgent;
  }
  ```

---

## 2. `HttpServletResponse`

### 이유

* 클라이언트로 보낼 **HTTP 응답**을 직접 제어할 수 있는 객체입니다.
* 상태코드, 헤더, 쿠키, 바디(OutputStream) 전송 등을 담당합니다.

### 사용하는 경우

* Spring이 기본 제공하는 뷰 리졸버 대신 **직접 응답 제어**해야 할 때

  * 예: JSON을 수동으로 작성해서 내려주기
  * 파일 다운로드 스트리밍
  * 특정 헤더 설정 (CORS, Content-Type, Cache-Control 등)

  ```java
  @GetMapping("/download")
  public void download(HttpServletResponse response) throws IOException {
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment; filename=test.txt");
      response.getWriter().write("Hello World!");
  }
  ```

---

## 3. `HttpSession`

### 이유

* 클라이언트와 서버 간의 \*\*상태 유지(session state)\*\*를 위한 객체입니다.
* 기본적으로 세션은 `JSESSIONID`라는 쿠키를 기반으로 작동합니다.

### 사용하는 경우

* 로그인 여부 확인, 사용자 인증/인가 정보 유지
* 장바구니, 임시 데이터 저장
* 요청 간 공유해야 하는 값을 저장

  ```java
  @PostMapping("/login")
  public String login(HttpSession session) {
      session.setAttribute("userId", "jin");
      return "redirect:/home";
  }

  @GetMapping("/home")
  public String home(HttpSession session) {
      String userId = (String) session.getAttribute("userId");
      return "Hello " + userId;
  }
  ```

---

## 정리

* **HttpServletRequest** → 요청 정보(raw 데이터) 직접 접근할 때
* **HttpServletResponse** → 응답을 직접 제어해야 할 때
* **HttpSession** → 사용자 상태(로그인 정보, 임시 데이터)를 요청 간 공유할 때

일반적인 CRUD API 같은 단순 컨트롤러에서는 잘 안 쓰고,
프레임워크가 추상화해주는 걸로 충분할 때가 많습니다.
하지만 **세션 기반 인증, 파일 업로드/다운로드, 헤더/쿠키 커스터마이징** 같은 상황에서는 필수로 받게 돼요.


