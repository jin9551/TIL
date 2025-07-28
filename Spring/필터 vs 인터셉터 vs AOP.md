# 필터 vs 인터셉터 vs AOP

| 구분          | 대상                                       | 호출 시점               | 주요 목적             |
| ----------- | ---------------------------------------- | ------------------- | ----------------- |
| Filter      | **서블릿 이전** (ServletRequest/Response)     | DispatcherServlet 전 | 보안, 인코딩, 로깅, CORS |
| Interceptor | **HandlerMapping 이후** (HandlerMethod 전후) | 컨트롤러 전/후            | 인증/인가, 로깅, 사용자 정보 |
| AOP         | **비즈니스 로직** (메서드 실행 전후)                  | 빈 내부 메서드 실행 시       | 트랜잭션, 로깅, 공통 처리   |


---

Spring에서는 **Filter**, **Interceptor**, **AOP** 세 가지가 모두 존재하며, 각각 요청 처리 흐름에서 다르게 동작하고 목적도 다릅니다. 아래는 세 가지 기술을 비교하며 설명한 정리입니다.

---

## ✅ 1. 핵심 개념 요약

| 구분        | Filter                                | Interceptor                               | AOP                           |
| --------- | ------------------------------------- | ----------------------------------------- | ----------------------------- |
| **기반**    | 자바 서블릿 표준 (`javax.servlet.Filter`)    | 스프링 MVC (`HandlerInterceptor`)            | 스프링 AOP (`@Aspect`, Proxy 기반) |
| **대상**    | `ServletRequest` / `ServletResponse`  | `HandlerMethod` (컨트롤러 메서드)                | Spring Bean의 메서드              |
| **호출 시점** | DispatcherServlet **이전**              | DispatcherServlet → HandlerMapping **이후** | 실제 Bean의 **메서드 실행 시점 전/후/예외** |
| **주요 목적** | 인코딩 설정, 보안, 로깅, 인증 등 전처리              | 로그인 인증, 권한 체크, 로깅 등 컨트롤러 제어               | 트랜잭션, 로깅, 성능 측정, 공통 관심사 처리    |
| **반응 가능** | 요청 / 응답 모두 가능                         | 요청 / 응답 가능 (`preHandle`, `postHandle`)    | 메서드 실행 전/후, 예외 등 모두 처리 가능     |
| **등록 방식** | `web.xml` 또는 `FilterRegistrationBean` | `WebMvcConfigurer.addInterceptors()`      | `@Aspect` + `@Around` 등       |
| **종료 조건** | `chain.doFilter()` 호출 여부              | `return true` or `false`                  | AOP Advice 정의 방식에 따름          |

---

## ✅ 2. 각 요소 상세 설명

### 🔹 Filter (서블릿 필터)

* **DispatcherServlet 이전**에 동작
* 가장 앞단에서 **HTTP 요청/응답 전체를 다룰 수 있음**
* 서블릿 기반이기 때문에 Spring에 의존하지 않음

```java
public class MyFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // 전처리
        chain.doFilter(request, response); // 다음 필터/서블릿 호출
        // 후처리
    }
}
```

📌 **주요 용도:**

* CORS 처리
* 로깅 (요청 IP, URI 등)
* XSS 방지, 캐싱 정책
* 인코딩 설정 (`UTF-8`)

---

### 🔹 Interceptor (스프링 인터셉터)

* **DispatcherServlet → HandlerMapping → Controller** 중간에 개입
* 스프링 MVC에서만 사용 가능
* `HandlerInterceptor` 구현

```java
public class MyInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 컨트롤러 실행 전
        return true; // false면 요청 차단
    }

    public void postHandle(...) {
        // 컨트롤러 실행 후, 뷰 렌더링 전
    }

    public void afterCompletion(...) {
        // 뷰 렌더링 후
    }
}
```

📌 **주요 용도:**

* 로그인 여부 확인
* 권한 체크
* 타이머 측정
* 뷰에 공통 데이터 추가

---

### 🔹 AOP (관점 지향 프로그래밍)

* 스프링 Bean의 **메서드 실행 시점 전후 또는 예외 발생 시점**에 개입
* 서비스 레이어 이상에서 자주 사용됨

```java
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before method: " + joinPoint.getSignature());
        Object result = joinPoint.proceed(); // 실제 메서드 실행
        System.out.println("After method");
        return result;
    }
}
```

📌 **주요 용도:**

* 트랜잭션 처리 (`@Transactional`)
* 로깅 (서비스 단위)
* 공통 예외 처리
* 퍼포먼스 측정

---

## ✅ 3. 호출 순서 비교

요청 흐름:

```plaintext
Client →
  [Filter] →
    DispatcherServlet →
      [Interceptor.preHandle()] →
        Controller (@RequestMapping) →
      [Interceptor.postHandle()] →
    ViewResolver →
  [Interceptor.afterCompletion()] →
Response 반환
```

AOP는 위 흐름과 무관하게 **Bean 메서드 호출 시점**에 개입합니다.

---

## ✅ 언제 어떤 걸 써야 할까?

| 요구사항 예시                | 적합한 기술      |
| ---------------------- | ----------- |
| 모든 요청의 IP, URI를 로깅해야 함 | Filter      |
| 로그인 여부 확인 후 특정 요청만 허용  | Interceptor |
| 서비스 로직 시간 측정 및 트랜잭션    | AOP         |
| CORS 설정, 인코딩 설정        | Filter      |
| 특정 컨트롤러 요청만 가로채기       | Interceptor |
| 특정 서비스 메서드 실행 시 로깅     | AOP         |

---

