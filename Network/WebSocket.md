웹소켓(WebSocket)은 **클라이언트와 서버 간에 양방향(full-duplex), 지속적인 연결을 유지**할 수 있는 **네트워크 통신 프로토콜**입니다. HTTP와 달리, 웹소켓은 연결을 맺은 후에도 지속적으로 데이터를 주고받을 수 있어, **실시간 데이터 송수신**이 필요한 애플리케이션(예: 채팅, 주식 시세, 게임, IoT 등)에 많이 사용됩니다.

---

## 📌 핵심 개념

| 항목        | 설명                                                   |
| --------- | ---------------------------------------------------- |
| **프로토콜**  | `ws://` 또는 보안 연결 시 `wss://` 사용                       |
| **연결 방식** | 초기에는 HTTP를 사용하여 Handshake를 하고, 이후 WebSocket 프로토콜로 전환 |
| **특징**    | 연결 유지, 실시간 양방향 통신, 낮은 오버헤드                           |

---

## 🔄 통신 흐름

1. **HTTP Handshake**

   * 클라이언트가 `Upgrade: websocket` 헤더를 포함해 서버에 요청
   * 서버가 이를 수락하면 프로토콜을 WebSocket으로 업그레이드함

2. **연결 유지**

   * 연결이 끊기지 않고 유지되며, 클라이언트와 서버가 자유롭게 메시지를 주고받을 수 있음

3. **양방향 메시지**

   * 서버 → 클라이언트
   * 클라이언트 → 서버
     실시간 푸시, 알림, 데이터 스트리밍 가능

---

## ✅ 장점

* **지속 연결**: HTTP는 요청-응답 기반이지만, WebSocket은 한 번 연결되면 계속 유지됨
* **양방향 통신**: 서버도 클라이언트에게 자유롭게 데이터를 보낼 수 있음
* **낮은 오버헤드**: HTTP보다 Header가 훨씬 작음
* **실시간 처리에 적합**: 채팅, 알림, 실시간 대시보드 등

---

## ❌ 단점

* **연결 수 제한**: 많은 클라이언트와의 연결을 관리하려면 서버 자원이 많이 듬
* **프록시/방화벽 문제**: 일부 네트워크에서는 WebSocket을 차단할 수 있음
* **복잡성 증가**: HTTP보다 연결 관리, 오류 처리, 보안 고려할 게 많음

---

## 🧪 예제 (JavaScript 클라이언트)

```javascript
const socket = new WebSocket('ws://localhost:8080/ws');

socket.onopen = () => {
  console.log('웹소켓 연결됨');
  socket.send('Hello Server');
};

socket.onmessage = (event) => {
  console.log('서버로부터 메시지:', event.data);
};

socket.onclose = () => {
  console.log('웹소켓 연결 종료');
};
```

---

## ☕ Java(Spring)에서의 WebSocket 예시

### 1. 의존성 추가 (`spring-websocket`, `spring-messaging`)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

### 2. WebSocket 설정

```java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyHandler(), "/ws").setAllowedOrigins("*");
    }
}
```

### 3. 핸들러 작성

```java
public class MyHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("수신 메시지: " + message.getPayload());
        session.sendMessage(new TextMessage("서버 응답: " + message.getPayload()));
    }
}
```
