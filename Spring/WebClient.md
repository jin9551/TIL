# WebClient

**Spring WebClient**는 Spring 5에서 도입된 **비동기(Asynchronous), 논블로킹(Non-blocking) 방식의 HTTP 클라이언트**입니다.

기존에 널리 사용되던 `RestTemplate`을 대체하기 위해 만들어졌으며, 현대적인 리액티브 프로그래밍 모델을 지원합니다.

---

## 1. 주요 특징

* **논블로킹(Non-blocking) I/O:** 요청을 보낸 후 응답을 기다리는 동안 스레드가 차단되지 않아 적은 수의 스레드로 대량의 요청을 처리할 수 있습니다.
* **리액티브 스트림(Reactive Streams) 지원:** `Mono`와 `Flux`를 사용하여 데이터를 처리합니다.
* **함수형 스타일 API:** 메서드 체이닝을 통해 가독성 높은 코드를 작성할 수 있습니다.
* **동기/비동기 모두 지원:** 기본은 비동기지만, 필요에 따라 `.block()`을 호출하여 동기 방식처럼 사용할 수도 있습니다.

---

## 2. RestTemplate vs WebClient

가장 큰 차이는 **스레드 모델**에 있습니다.

| 특징 | RestTemplate | WebClient |
| --- | --- | --- |
| **방식** | 동기 + 블로킹 (Blocking) | 비동기 + 논블로킹 (Non-blocking) |
| **스레드** | 요청당 하나의 스레드 할당 | 적은 수의 스레드로 다수 요청 처리 |
| **라이브러리** | Spring Web (Servlet 스택) | Spring WebFlux (Reactive 스택) |
| **상태** | 유지보수 모드 (Maintenance Mode) | 적극 권장 (Recommended) |

---

## 3. 기본적인 사용법

### 의존성 추가 (Gradle 기준)

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
}

```

### 간단한 GET 요청 예시

```java
WebClient webClient = WebClient.create("https://api.example.com");

Mono<String> response = webClient.get()
        .uri("/data/{id}", 1)
        .retrieve() // 응답 본문을 가져옴
        .bodyToMono(String.class); // 0 또는 1개의 결과를 비동기적으로 받음

// 결과 구독 (비동기 실행)
response.subscribe(System.out::println);

```

### POST 요청 예시 (JSON 전송)

```java
public Mono<User> createUser(User user) {
    return webClient.post()
            .uri("/users")
            .bodyValue(user)
            .retrieve()
            .bodyToMono(User.class);
}

```

---

## 4. 왜 WebClient를 써야 할까?

최근의 서비스 아키텍처는 수많은 마이크로서비스(MSA) 간의 통신이 빈번합니다. `RestTemplate`은 응답이 올 때까지 스레드가 대기해야 하므로, 통신이 많아질수록 **스레드 부족 현상**이 발생하기 쉽습니다. 반면 `WebClient`는 시스템 자원을 훨씬 효율적으로 사용하여 더 높은 처리량(Throughput)을 제공합니다.

---

네, 좋습니다! `WebClient`의 핵심인 **이벤트 루프(Event Loop)** 기반 논블로킹 모델을 시각적으로 이해하면 왜 효율적인지 명확해집니다.

기존의 `RestTemplate`과 비교하여 동작 방식을 설명해 드릴게요.

---

### 1. RestTemplate vs WebClient 동작 방식 비교

#### **RestTemplate (Blocking)**

* **1:1 스레드 모델:** 요청이 들어올 때마다 스레드를 하나씩 할당합니다.
* **대기 상태:** API 응답이 올 때까지 해당 스레드는 아무것도 하지 못하고 '차단(Blocking)'된 상태로 기다립니다.
* **문제점:** 동시 접속자가 많아지면 스레드 수가 급증하여 메모리 부하가 커지고, 스레드 전환(Context Switching) 비용이 발생합니다.

#### **WebClient (Non-blocking)**

* **이벤트 루프 모델:** 적은 수의 고정된 스레드(보통 CPU 코어 수의 2배)만 사용합니다.
* **비보류 방식:** 요청을 보낸 후 응답을 기다리지 않고 즉시 다른 작업을 수행합니다.
* **콜백(Callback):** 외부 서비스에서 응답이 오면 '이벤트'가 발생하고, 그때서야 결과값을 처리합니다.

---

### 2. WebClient 내부 아키텍처 (Reactive Stack)

`WebClient`는 하부 엔진으로 보통 **Netty**를 사용합니다.

1. **Request 전송:** 애플리케이션이 요청을 던지면, 이벤트 루프에 이 작업이 등록됩니다.
2. **작업 위임:** Netty 채널을 통해 요청을 보내고, 스레드는 즉시 해제되어 다른 요청을 처리하러 갑니다.
3. **응답 수신:** 외부 서버로부터 데이터가 도착하면 Netty가 이를 감지하고 이벤트를 발생시킵니다.
4. **결과 반환:** 준비된 데이터를 `Mono`나 `Flux`에 담아 최종적으로 사용자에게 전달합니다.

---

### 3. 요약하자면

`WebClient`는 마치 **"주문 후 진동벨을 받고 자리에 앉아 자기 할 일을 하다가, 벨이 울리면 음식을 찾으러 가는 손님"**과 같습니다. 반면 `RestTemplate`은 **"음식이 나올 때까지 카운터 앞에서 줄을 서서 기다리는 손님"**에 비유할 수 있습니다.

---

네, `WebClient`가 내부적으로 어떻게 요청을 처리하고 다시 응답을 받아오는지 그 과정을 아키텍처 다이어그램 형태로 설명해 드릴게요.

`WebClient`는 기본적으로 **Spring WebFlux** 프레임워크 위에서 동작하며, 네트워크 처리를 위해 **Netty**와 같은 비동기 서버 엔진을 사용합니다.

---

### WebClient 내부 동작 아키텍처

이 구조를 단계별로 풀어서 설명하면 다음과 같습니다.

### 1. 요청 생성 (Request Generation)

* 사용자가 `webClient.get()` 등을 호출하면 요청 정보가 생성됩니다.
* 이때 `ExchangeFilterFunction`이 있다면 요청이 전달되기 전에 가로채서 로그를 남기거나 헤더를 추가하는 전처리를 수행합니다.

### 2. HTTP 클라이언트 엔진 (Netty / Jetty)

* `WebClient`는 실제 네트워크 I/O를 직접 하지 않고, 내부의 `ClientHttpConnector`를 통해 엔진(기본값은 **Reactor Netty**)에 전달합니다.
* **Event Loop 스레드:** 요청을 받은 Netty의 이벤트 루프 스레드는 소켓에 데이터를 쓰고 즉시 다른 작업을 하러 떠납니다. (이 지점에서 Non-blocking이 발생합니다.)

### 3. 리액티브 파이프라인 (Reactive Pipeline)

* **Mono / Flux:** 데이터는 리액티브 스트림즈 표준에 따라 흐릅니다.
* 데이터가 아직 도착하지 않았더라도 `Mono<T>`라는 "미래에 올 데이터에 대한 약속" 객체를 즉시 반환합니다.

### 4. 응답 처리 (Response Handling)

* 외부 서버에서 응답이 도착하면 Netty의 이벤트 루프가 이를 감지합니다.
* **Decoder:** 수신된 바이트(Byte) 데이터를 설정된 `HttpMessageReader`를 통해 JSON이나 String 객체로 변환합니다.
* **Subscription:** 최종적으로 사용자가 `.subscribe()`하거나 반환된 `Mono`를 리액티브 체인에서 사용할 때 데이터가 전달됩니다.

---

### 시각적 흐름 요약

| 구성 요소 | 역할 | 비유 |
| --- | --- | --- |
| **WebClient API** | 인터페이스 제공 | 주문서 작성 및 접수처 |
| **Exchange Strategies** | 직렬화/역직렬화 (JSON 변환 등) | 식재료를 요리로 가공하는 과정 |
| **Netty Event Loop** | 비동기 I/O 실행 | 주방에서 주문을 배분하고 완료 시 알리는 매니저 |
| **Mono / Flux** | 결과 전달 매개체 | 주문 완료 시 울리는 진동벨 |

---

아주 날카롭고 좋은 질문입니다! 결론부터 말씀드리면, **WebClient는 기본적으로 HTTP 통신을 사용합니다.** (물론 WebSocket도 지원하지만, 일반적인 API 호출은 HTTP/1.1 또는 HTTP/2를 기반으로 합니다.)

질문하신 "응답을 기다리는 방식"에 대해 많은 분이 헷갈려 하시는데, 그 핵심 차이를 시각적 아키텍처와 함께 설명해 드릴게요.

---

### 1. WebClient는 왜 WebSocket이 아닐까?

**WebSocket**은 한 번 연결(Handshake)을 맺으면 양방향으로 계속 데이터를 주고받는 **지속적인 연결(Persistent Connection)** 방식입니다.

반면, **WebClient**가 사용하는 **Non-blocking HTTP** 방식은 다음과 같습니다:

1. 요청을 보낸다 (Request).
2. 응답이 올 때까지 통로(Socket)는 열어두지만, **내 스레드는 다른 일을 하러 간다.**
3. 외부 서버에서 응답 데이터가 도착하면, **네트워크 카드(OS 레벨)가 신호를 준다.**
4. 이벤트 루프가 그 신호를 낚아채서 멈췄던 작업을 마저 처리한다.

---

### 2. 이벤트 루프가 "기다린다"는 것의 진짜 의미

질문하신 것처럼 이벤트 루프가 응답을 '가만히 서서' 기다린다면 기존의 Blocking 방식과 다를 게 없습니다. 하지만 여기서의 기다림은 **"관심 등록"**에 가깝습니다.

* **Blocking (RestTemplate):** "음식 나올 때까지 나 여기서 안 움직이고 기다릴 거야!" (스레드 점유)
* **Non-blocking (WebClient):** "음식 나오면 이 진동벨 울려줘. 난 저기 가서 책 보고 있을게." (스레드 해방)

위 다이어그램처럼 Netty의 이벤트 루프는 **Selector(셀렉터)**라는 시스템 콜을 사용합니다.

* 이벤트 루프 스레드는 수천 개의 연결을 관리하면서, **"데이터가 도착한 소켓이 있는지"**만 아주 빠르게 훑고 지나갑니다.
* 데이터가 온 게 없다면? 즉시 다음 연결을 확인하거나 다른 요청을 처리합니다.
* 즉, **스레드가 멈춰서 기다리는(Idle) 시간이 거의 없습니다.**

---

### 3. 요약 및 비교

| 구분 | WebClient (HTTP) | WebSocket |
| --- | --- | --- |
| **연결 방식** | Stateless (요청-응답 후 원칙적 종료*) | Stateful (연결 유지) |
| **통신 방향** | 단방향 (클라이언트가 먼저 요청) | 양방향 (서버도 먼저 보냄) |
| **효율성** | 이벤트 루프를 통해 스레드 효율 극대화 | 실시간 데이터 전송에 최적화 |

**최신 HTTP는 Keep-alive로 연결을 재사용하지만, 논리적인 구조는 요청-응답 모델입니다.*

