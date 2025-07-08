`@Value`는 Lombok에서 제공하는 어노테이션으로, \*\*불변 객체(immutable object)\*\*를 만들 때 사용하는 어노테이션이야.
쉽게 말하면 `@Data`의 **불변 버전**이라고 생각하면 돼.

---

## ✅ `@Value` 어노테이션이 하는 일

`@Value`를 붙이면 Lombok이 아래 기능을 자동 생성해 줘:

| 포함 기능                             | 설명                             |
| --------------------------------- | ------------------------------ |
| `@Getter`                         | 모든 필드에 대한 `getter` 생성          |
| `@AllArgsConstructor`             | 모든 필드를 초기화하는 생성자 생성            |
| `@ToString`                       | `toString()` 자동 생성             |
| `@EqualsAndHashCode`              | `equals()`와 `hashCode()` 자동 생성 |
| **모든 필드를 `private final`로 자동 설정** |                                |
| **setter 미생성 → 불변 객체 보장**         |                                |

---

## ✅ 예제

```java
import lombok.Value;

@Value
public class User {
    String name;
    int age;
}
```

컴파일 후 아래와 같은 코드가 생성돼:

```java
public final class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) { ... }

    @Override
    public int hashCode() { ... }

    @Override
    public String toString() { ... }
}
```

---

## ✅ 특징 요약

| 특징                       | 설명                    |
| ------------------------ | --------------------- |
| ❌ `setter` 없음            | 값 변경 불가 (불변 객체)       |
| ✅ `getter`, 생성자 있음       | 생성자 통해 초기화            |
| ✅ 모든 필드는 `private final` | 컴파일 시 자동 적용됨          |
| ✅ thread-safe            | 값이 바뀌지 않으므로 병렬 처리에 안전 |

---

## 🆚 `@Data` vs `@Value`

| 항목       | `@Data`                     | `@Value`                          |
| -------- | --------------------------- | --------------------------------- |
| 필드       | 기본 `private`, mutable       | 기본 `private final`, immutable     |
| `setter` | 있음                          | 없음                                |
| `getter` | 있음                          | 있음                                |
| 생성자      | 선택적 (`@NoArgs`, `@AllArgs`) | `@AllArgsConstructor` 포함          |
| 변경 가능성   | 변경 가능                       | 변경 불가능                            |
| 용도       | 일반 DTO, VO                  | 값 객체(Value Object), 설정 정보, 키 객체 등 |

---

## 🚫 사용 시 주의사항

* **JPA `@Entity`와 함께 쓰지 마라.** 불변 필드는 JPA에서 수정이 안 되기 때문에 문제가 생길 수 있음
* Jackson 등 JSON 라이브러리와 함께 쓸 때는 생성자 기반 deserialization을 설정해 줘야 함

---

