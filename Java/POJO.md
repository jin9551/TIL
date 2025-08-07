# POJO란?

POJO는 **Plain Old Java Object**의 약자로, 특별한 제약 없이 작성된 **순수한 자바 객체**를 말합니다.
쉽게 말해, **특정 프레임워크나 라이브러리에 종속되지 않고**, **getter/setter**, **필드**, **기본 생성자** 등을 가진 단순한 Java 클래스입니다.

---

### ✅ POJO의 특징

| 항목       | 설명                                                                        |
| -------- | ------------------------------------------------------------------------- |
| ✔ 종속성 없음 | Spring, Hibernate 등 프레임워크의 특정 클래스를 상속하거나 어노테이션을 붙이지 않음                    |
| ✔ 간단한 구조 | 보통 `private` 필드 + `getter/setter` + `기본 생성자` 형태                           |
| ✔ 목적     | 데이터를 담기 위한 **DTO(Data Transfer Object)**, \*\*VO(Value Object)\*\*로 자주 사용 |

---

### 📌 예시

```java
public class User {
    private String name;
    private int age;

    public User() {} // 기본 생성자

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

이 객체는 어떤 프레임워크에도 의존하지 않고, 그저 데이터를 담기 위한 "순수한 자바 클래스"입니다.

---

### ❗ POJO vs JavaBean

| 항목    | POJO                   | JavaBean                                                 |
| ----- | ---------------------- | -------------------------------------------------------- |
| 의무 조건 | 없음                     | `기본 생성자`, `getter/setter`, `Serializable` 구현 등 명확한 규칙 존재 |
| 목적    | 일반적인 Java 객체           | 주로 UI 툴이나 Java EE에서 사용                                   |
| 관계    | **JavaBean은 POJO의 일종** |                                                          |

---

### 🔧 POJO가 중요한 이유

* **유지보수성**: 프레임워크에 의존하지 않기 때문에 테스트와 유지보수가 쉬움
* **유연성**: 다양한 계층(DTO, VO, Entity 등)에서 재사용 가능
* **테스트 용이성**: 단위 테스트할 때 불필요한 종속성이 없음

---

POJO, DTO, Entity는 모두 Java 객체이지만, **역할과 사용 목적**이 다릅니다. 아래에 표와 함께 핵심 차이점을 명확하게 정리해줄게요.

---

### ✅ POJO vs DTO vs Entity

| 구분          | POJO                            | DTO (Data Transfer Object)           | Entity (엔티티)                             |
| ----------- | ------------------------------- | ------------------------------------ | ---------------------------------------- |
| **정의**      | Plain Old Java Object, 순수 자바 객체 | 데이터 전달을 위한 객체                        | DB 테이블과 매핑되는 객체                          |
| **역할**      | 일반 데이터 보관 또는 로직 포함 가능           | Controller ↔ Service ↔ View 간 데이터 전달 | JPA/Hibernate 등에서 DB와 직접 연결              |
| **특징**      | 프레임워크나 규약에 얽매이지 않음              | 계층 간 데이터 이동용, 보통 로직 없음               | ORM 매핑을 위한 어노테이션 사용 (`@Entity`, `@Id` 등) |
| **예시**      | 간단한 Java 클래스                    | REST API 응답 객체, 폼 입력 값               | DB 테이블 1:1 대응 클래스                        |
| **어노테이션**   | 없음                              | 보통 없음 (가끔 `@JsonProperty`, `@Data`)  | `@Entity`, `@Table`, `@Id` 등 JPA 관련      |
| **DB와의 관계** | 없음                              | 없음                                   | DB와 1:1로 매핑                              |
| **비즈니스 로직** | 포함 가능                           | 보통 없음 (순수한 데이터 전달)                   | 일부 포함 가능하나, 무겁게 만들지 않음                   |

---

### 📌 각각의 예시 코드

#### 🧱 1. POJO

```java
public class SimpleData {
    private String name;
    private int value;

    // 생성자, getter, setter
}
```

#### 🚚 2. DTO

```java
public class UserDTO {
    private String username;
    private String email;

    // getter/setter
}
```

* 사용 예: 컨트롤러에서 클라이언트에 응답할 데이터 구조 정의

#### 🏛️ 3. Entity

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;

    // getter/setter
}
```

* 사용 예: JPA에서 DB `users` 테이블과 직접 매핑

---

### 🔄 정리 요약

* **POJO**는 가장 기본이 되는 "순수한 객체 형식"
* **DTO**는 "데이터 전달"을 위해 사용되며, 비즈니스 로직은 없음
* **Entity**는 "데이터베이스 테이블"과 매핑되며, DB 연산에 사용됨

---

필요하면 DTO ↔ Entity 간 변환 메서드도 예시로 보여줄 수 있어. 원해?

