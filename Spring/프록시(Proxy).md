# 프록시(Proxy)란?

프록시(Proxy)는 **"대리인"**, 또는 **"중간자"** 역할을 하는 객체나 서버를 의미하며, **요청과 응답 사이에서 중개자 역할을 수행하는 기술/패턴**입니다. 사용 맥락에 따라 다르게 적용되지만, 공통적으로 **직접 접근을 우회하거나, 기능을 추가하거나, 보호/제어**할 목적으로 사용됩니다.

---

### ✅ 1. 일반적인 개념

프록시는 **클라이언트와 실제 대상 객체(혹은 서버)** 사이에 위치하여, 클라이언트의 요청을 대신 처리하거나 중간에서 가공합니다.

* 예: 웹 브라우저 → 프록시 서버 → 인터넷

---

### ✅ 2. 소프트웨어 개발에서의 프록시 종류

#### 1) **프록시 패턴 (Proxy Pattern)** – 디자인 패턴

* 목적: **원래 객체에 대한 접근을 제어**하거나, **기능을 덧붙이기** 위해 대리 객체 사용
* 사용 예:

  * **Virtual Proxy**: 무거운 객체 생성을 지연 (예: 이미지 로딩)
  * **Protection Proxy**: 접근 권한 제어
  * **Logging/Monitoring Proxy**: 호출 시 로그 기록
* 예시 코드 (Java):

```java
interface Service {
    void run();
}

class RealService implements Service {
    public void run() {
        System.out.println("Real service is running");
    }
}

class ProxyService implements Service {
    private RealService realService;

    public void run() {
        if (realService == null) {
            realService = new RealService();
        }
        System.out.println("Logging: before service run");
        realService.run();
        System.out.println("Logging: after service run");
    }
}
```

---

#### 2) **프록시 서버 (Proxy Server)** – 네트워크

* 클라이언트와 인터넷 사이에 위치하는 중개 서버
* 주요 기능:

  * **캐싱 (속도 향상)**
  * **IP 숨김 (익명성 제공)**
  * **방화벽/보안**
  * **콘텐츠 필터링**

---

#### 3) **리버스 프록시 (Reverse Proxy)** – 서버 앞단의 프록시

* 클라이언트는 리버스 프록시에게 요청하고, 이 프록시는 내부 서버에 요청을 전달
* 대표 사례:

  * **Nginx**, **Apache HTTPD**, **AWS ALB**
* 기능:

  * 로드 밸런싱
  * 인증/SSL 종료
  * 보안 보호

---

### ✅ 3. AOP와의 비교 (Spring 관점)

| 항목       | 프록시                                      | AOP                        |
| -------- | ---------------------------------------- | -------------------------- |
| 적용 방식    | 인터페이스 기반(JDK) 또는 클래스 기반(CGLIB) 프록시 객체 생성 | 어드바이스(Advice) 기반으로 관심사를 주입 |
| 주요 목적    | 요청 가로채기, 제어, 래핑                          | 횡단 관심사(로깅, 보안, 트랜잭션 등) 분리  |
| 대표 라이브러리 | java.lang.reflect.Proxy / CGLIB          | Spring AOP, AspectJ        |

---

### ✅ 4. 스프링에서의 프록시

스프링은 AOP 구현 시 내부적으로 프록시를 사용합니다.

* `@Transactional`, `@Cacheable`, `@Async` 등의 어노테이션은 **프록시 객체로 감싸져서 동작**합니다.
* 일반적으로 프록시 객체는 런타임에 생성되어 **원본 빈의 메서드 실행 전후에 부가 로직**을 실행합니다.

---

### 요약

| 목적      | 프록시가 하는 일             |
| ------- | --------------------- |
| 접근 제어   | 권한 체크, 제한적 접근 제공      |
| 부가기능 삽입 | 로깅, 모니터링, 캐싱, 트랜잭션    |
| 성능 향상   | 캐시 프록시, 지연 초기화        |
| 보안      | IP 숨김, 요청 필터링         |
| 확장성     | AOP, 인증, 요청 라우팅 등과 결합 |

---

# 부록

Spring AOP에서 프록시는 **빈 등록 시점**에 **자동으로 생성**되며, **프록시 객체가 원래의 빈을 감싸는 구조**입니다. 핵심은 다음과 같습니다:

---

## ✅ 언제 프록시가 생성되는가?

### ✔️ 1. AOP 관련 어노테이션/설정이 있을 때

다음 중 하나라도 적용되면 스프링은 **프록시를 생성**합니다:

* `@Transactional`
* `@Async`
* `@Cacheable`
* `@Scheduled`
* 사용자 정의 `@Around`, `@Before`, `@After` 등 Aspect 어노테이션
* 또는 XML/JavaConfig에서 `<aop:config>` / `@EnableAspectJAutoProxy`

```java
@Configuration
@EnableAspectJAutoProxy  // 프록시 기반 AOP 활성화
public class AppConfig {}
```

---

### ✔️ 2. 빈 생성 시점에 프록시로 감쌈

스프링이 `@Bean`, `@Component`, `@Service` 등으로 빈을 등록할 때,
**AOP 대상 메서드가 존재하면 원본 객체 대신 프록시 객체를 생성하여 등록**합니다.

* 이 프록시는 내부적으로 원본 객체를 갖고 있으며,
* 호출 시 Advice (로깅, 트랜잭션 등)를 메서드 전후로 실행합니다.

---

## ✅ 어떻게 프록시가 생성되는가?

### ✔️ 1. 프록시 방식 결정 기준

| 조건                     | 프록시 방식                |
| ---------------------- | --------------------- |
| 대상 클래스가 **인터페이스를 구현함** | JDK Dynamic Proxy     |
| 대상 클래스에 **인터페이스가 없음**  | CGLIB 프록시 (클래스 상속 기반) |

스프링에서는 `@EnableAspectJAutoProxy(proxyTargetClass = true)` 설정을 하면,
**무조건 CGLIB 프록시를 사용**하게 됩니다.

---

### ✔️ 2. 프록시 호출 흐름

예시: `@Transactional` 적용된 서비스 호출 시

1. 클라이언트 → `MyService myService = context.getBean(MyService.class);`
2. `myService`는 실제로는 `MyServiceProxy` (프록시) 객체
3. 메서드 호출 시,

   * 트랜잭션 Advice → before logic (트랜잭션 시작)
   * 실제 메서드 실행
   * 트랜잭션 Advice → after logic (커밋/롤백)
4. 결과 반환

---

### ✔️ 3. 프록시 생성 시점 요약

| 단계                     | 설명                                              |
| ---------------------- | ----------------------------------------------- |
| ApplicationContext 초기화 | BeanFactoryPostProcessor 및 BeanPostProcessor 등록 |
| AOP 관련 어노테이션 스캔        | `AnnotationAwareAspectJAutoProxyCreator`가 감지    |
| Bean 생성 직전             | `BeanPostProcessor`가 AOP 대상 여부 판단               |
| AOP 대상이면               | 원본 빈 대신 프록시 객체 생성 후 컨테이너에 등록                    |

---

## ✅ 확인 방법

### ✔️ 실제로 프록시인지 확인하는 코드

```java
Object bean = context.getBean(MyService.class);
System.out.println(bean.getClass());
// 출력 예: com.sun.proxy.$Proxy123 (JDK Proxy)
// 또는    : MyService$$EnhancerBySpringCGLIB (CGLIB Proxy)
```

---

## 🔍 정리

| 항목     | 설명                                              |
| ------ | ----------------------------------------------- |
| 생성 시점  | ApplicationContext 초기화 중 BeanPostProcessor 단계   |
| 생성 방식  | JDK Proxy (인터페이스 기반), CGLIB (클래스 상속 기반)         |
| 주요 대상  | `@Transactional`, `@Async`, 사용자 정의 AOP Pointcut |
| 감지 클래스 | `AnnotationAwareAspectJAutoProxyCreator`        |
| 목적     | 부가 로직 (트랜잭션, 로깅 등)을 실행하고 핵심 로직 호출               |

---

### 👉 필요 시 알려드릴 수 있는 추가 내용

* `@Aspect` 정의 및 Around Advice 흐름
* 프록시 방식 강제 설정 방법 (proxyTargetClass)
* 프록시 객체와 원본 객체 차이
* AOP 대상이 안 되는 경우 (private, final 등)

필요하신 내용 골라 말씀 주세요!

