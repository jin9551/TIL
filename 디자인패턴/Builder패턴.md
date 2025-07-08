Builder 패턴은 **복잡한 객체의 생성 과정**을 분리하여, 동일한 생성 절차에서 서로 다른 표현을 만들 수 있게 해주는 **생성 패턴(Creational Pattern)** 중 하나야.

> 참고 : https://inpa.tistory.com/entry/GOF-%F0%9F%92%A0-%EB%B9%8C%EB%8D%94Builder-%ED%8C%A8%ED%84%B4-%EB%81%9D%ED%8C%90%EC%99%95-%EC%A0%95%EB%A6%AC

---

## 🔧 언제 사용하나?

* 생성자나 정적 팩토리만으로는 **매개변수가 너무 많아질 때**
* 객체 생성 시 **가독성이 떨어지고, 실수가 많아질 수 있을 때**
* 어떤 필드는 필수고 어떤 필드는 선택일 때
* **불변 객체**를 만들고 싶을 때 (모든 필드를 `final`로 설정한 뒤 한 번에 초기화)

---

## 📦 구조

Builder 패턴은 다음 구성요소로 나뉜다:

| 구성 요소             | 설명                                                         |
| ----------------- | ---------------------------------------------------------- |
| `Product`         | 복잡하게 생성할 대상 객체                                             |
| `Builder`         | 객체를 생성하기 위한 추상 인터페이스 (Java에선 보통 static inner class 형태로 사용) |
| `ConcreteBuilder` | 실제로 각 필드를 세팅하고 객체를 리턴하는 클래스                                |
| `Director` (선택)   | 생성 순서를 제어하는 클래스 (Java에선 생략하는 경우 많음)                        |

---

## ✍️ 예시 코드

### ✅ Before: 생성자 or 정적 팩토리 방식

```java
public class User {
    private String name;
    private int age;
    private String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
```

사용 시:

```java
User user = new User("진하", 30, "jinha@example.com");
```

문제점:

* 매개변수가 많아지면 가독성이 떨어짐
* 어떤 값이 어떤 필드인지 혼동될 수 있음
* 선택 매개변수 지원 어려움

---

### ✅ After: Builder 패턴

```java
public class User {
    private final String name;
    private final int age;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    public static class Builder {
        private String name;
        private int age;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

사용 예:

```java
User user = new User.Builder()
    .name("진하")
    .age(30)
    .email("jinha@example.com")
    .build();
```

장점:

* 필드명이 명확해서 **가독성이 뛰어남**
* 선택적 필드 설정 가능
* 객체 생성 시 **불변성(immutable)** 유지 가능

---

## 👍 장점 정리

* **가독성 향상**
* **매개변수 조합 유연함**
* **불변 객체 생성 가능**
* 코드 **확장성** 높음

---

## 👎 단점

* 클래스 수가 늘어나고 코드가 길어짐
* 모든 필드를 복사해와야 해서 중복 많을 수 있음

---

## 🛠 Java 라이브러리에서의 Builder

* `StringBuilder` / `StringBuffer` → 문자열 조립용
* `Lombok`의 `@Builder` 어노테이션 → 자동으로 빌더 생성

예:

```java
// Lombok 버전
@Builder
public class User {
    private String name;
    private int age;
    private String email;
}
```

---

