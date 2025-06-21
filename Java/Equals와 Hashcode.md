# equals()와 hashCode()

Java에서 `equals()`와 `hashCode()`는 객체의 **동등성 비교**와 **해시 기반 컬렉션 처리**에서 매우 중요한 역할을 합니다. 각각의 목적과 사용 이유를 아래에 자세히 설명할게요.

---

### ✅ 1. `equals()`의 목적: **논리적 동등성(logical equality) 비교**

* `equals()` 메서드는 두 객체가 **내용적으로 같은지**를 판단할 때 사용됩니다.
* 기본적으로 `Object` 클래스의 `equals()`는 \*\*객체의 주소값(참조)\*\*을 비교하지만, 이를 **의미 있는 비교**로 바꾸기 위해 오버라이딩합니다.

#### 예시:

```java
public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}
```

이제 `User u1 = new User("Alice"); User u2 = new User("Alice");` 라면
`u1.equals(u2)` 는 `true`를 반환하게 됩니다.

---

### ✅ 2. `hashCode()`의 목적: **해시 기반 자료구조의 성능 유지**

* `hashCode()`는 객체를 해시 기반 컬렉션(`HashMap`, `HashSet`, `Hashtable`)에 사용할 때, **객체를 빠르게 찾기 위한 주소값 역할**을 합니다.
* `equals()`가 `true`인 두 객체는 반드시 `hashCode()`도 **같아야** 합니다. (자바의 규칙)

#### 예시:

```java
@Override
public int hashCode() {
    return Objects.hash(name); // Java 7 이상
}
```

> 만약 `equals()`만 오버라이드하고 `hashCode()`는 그대로 두면, `HashSet`이나 `HashMap`에 이상한 동작을 할 수 있어요. 같은 객체인데 중복으로 들어가거나 못 찾는 문제 발생.

---

### ✅ 왜 함께 오버라이딩해야 할까?

Java의 `HashSet`, `HashMap` 등은 다음과 같은 방식으로 동작합니다:

1. 객체의 `hashCode()`를 이용해 \*\*버킷(bucket)\*\*을 찾고
2. 그 버킷 안에서 `equals()`로 **실제 객체를 비교**

즉, 두 메서드는 **짝으로 동작**합니다.

---

### ✅ 정리

| 메서드          | 목적                    | 예시 사용처                                |
| ------------ | --------------------- | ------------------------------------- |
| `equals()`   | 두 객체의 **논리적 동등성** 비교  | `List.contains()`, `Set.contains()` 등 |
| `hashCode()` | **빠른 조회**를 위한 해시 값 반환 | `HashMap`, `HashSet` 등 해시 컬렉션         |

---

필요하다면 IDE에서 자동으로 두 메서드를 함께 생성해주는 기능을 사용하면 편리합니다.
추가로 `record`를 사용하면 자동 구현되기도 해요. 원하시면 그 예시도 보여드릴게요!
