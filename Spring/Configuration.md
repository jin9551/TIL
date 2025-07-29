# @Configuration

`@Configuration` 어노테이션은 Spring Framework에서 **자바 클래스를 설정 파일로 사용**할 수 있게 해주는 어노테이션입니다. 흔히 XML 설정(`applicationContext.xml`)을 대체할 때 사용합니다.

---

## 🔧 역할 요약

* `@Configuration`이 붙은 클래스는 **Spring의 설정 클래스**로 인식됩니다.
* 이 클래스 내부에서 `@Bean` 어노테이션을 사용하여 **빈(bean) 정의**를 할 수 있습니다.
* 내부적으로는 `@Component`가 포함되어 있어 **자동으로 스프링 컨테이너에 등록**됩니다.

---

## 📌 사용 예시

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

이 코드는 XML로 치면 아래와 같습니다:

```xml
<bean id="myService" class="com.example.MyServiceImpl"/>
```

---

## 🧠 내부적으로는?

* `@Configuration` 클래스는 **프록시(proxy)** 형태로 등록되어, 메서드 호출 시 항상 같은 객체(싱글톤)를 반환하도록 보장합니다.
* 아래 코드에서 `myRepository()`를 여러 번 호출하더라도 항상 같은 인스턴스가 반환됩니다:

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl(myRepository());
    }

    @Bean
    public MyRepository myRepository() {
        return new MyRepositoryImpl();
    }
}
```

위처럼 `@Configuration`이 없는 일반 클래스라면 `myRepository()`는 매번 다른 객체를 리턴할 수도 있습니다.

---

## ❗주의할 점

* `@Configuration` 없이 `@Bean`만 선언된 클래스는 프록시가 적용되지 않아 **싱글톤 보장이 깨질 수 있음**.
* 반드시 설정 클래스에는 `@Configuration`을 붙여주는 것이 좋습니다.

---


