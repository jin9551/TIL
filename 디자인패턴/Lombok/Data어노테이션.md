`@Data`는 Lombok에서 제공하는 **복합 어노테이션**이야. 클래스에 자주 쓰는 메서드들을 한꺼번에 자동 생성해줘.

---

## 📌 `@Data` 어노테이션이 하는 일

`@Data`를 붙이면 Lombok이 아래 5가지 기능을 자동으로 생성해준다:

| 포함 어노테이션                   | 기능 설명                                  |
| -------------------------- | -------------------------------------- |
| `@Getter`                  | 모든 필드에 대한 getter 생성                    |
| `@Setter`                  | 모든 필드에 대한 setter 생성                    |
| `@ToString`                | `toString()` 메서드 생성                    |
| `@EqualsAndHashCode`       | `equals()`와 `hashCode()` 생성            |
| `@RequiredArgsConstructor` | `final` 또는 `@NonNull` 필드만 초기화하는 생성자 생성 |

---

## ✅ 예제

```java
@Data
public class User {
    private String name;
    private int age;
}
```

→ 컴파일 시 아래와 같은 메서드들이 자동 생성됨:

```java
public String getName();
public void setName(String name);
public int getAge();
public void setAge(int age);
public String toString();
public boolean equals(Object o);
public int hashCode();
// final 또는 @NonNull 필드가 있을 경우 생성자도
```

---

## ✅ `@Data` vs 개별 어노테이션

| 어노테이션                | 설명                                                      |
| -------------------- | ------------------------------------------------------- |
| `@Data`              | 전체 자동화 (POJO 전용)                                        |
| `@Getter`, `@Setter` | 일부 필드만 설정하고 싶을 때                                        |
| `@Value`             | 불변 객체 생성 시 (`@Data` + `final`, `private`, no `setters`) |
| `@Builder`           | 빌더 패턴 생성자 추가 (따로 써야 함)                                  |

---

## 📌 네 코드 예시 분석

```java
@Data
@Builder
public class MFAResponse extends IdentityResponse {
	private String homeId;
	private List<MFA> mfaList;
}
```

* `@Data` → `getHomeId()`, `setHomeId()`, `getMfaList()`, `setMfaList()` 등 자동 생성
* `@Builder` → `MFAResponse.builder().homeId("abc").mfaList(...).build();` 가능
* `IdentityResponse`를 상속한 서브클래스

---

## ⚠️ 주의사항

* `@Data`는 모든 필드에 대해 `setter`도 생성하므로, **불변 객체를 만들고 싶을 땐 `@Value` 또는 개별 어노테이션 조합 사용**이 더 적절함
* JPA `@Entity`에 `@Data` 쓰는 건 권장되지 않음 (무한 루프 등 발생 가능성 있음)

---
