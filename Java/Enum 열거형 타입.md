# Enum(열거형)이란?

Enum은 "Enumeration"의 약자이며, Enumeration은 "열거, 목록, 일람표"라는 뜻을 가지고 있다. 
보통 한글로는 열거형이라 부른다. **열거형**은 어렵게 생각할 필요 없이 **상수들의 집합**으로 이해하면 된다.

프로그래밍을 하다보면 배열이나 리스트 등 여러개의 묶음 데이터를 다루는 일이 빈번하다. 이 묶음 데이터 중에는 데이터 주제에 따라 몇가지로 한정된 값만을 가지는 경우가 있다.

대표적인 예시로는 '요일'이나 '계절' 혹은 '주사위'가 있다. 요일은 '월,화,수,목,금,토,일' 이렇게 7가지 밖에 없고, 계절은 '봄,여름,가을,겨울' 4가지로 한정되어 있다. 

이와 같이 **정해져 있는 한정된 데이터 묶음을 열거형 타입인 Enum으로 묶어주면 보다 구조적으로 프로그래밍**을 할 수 있다.


* * *


## 왜 enum이 만들어 졌는가?

과거에는 여러가지 방법으로 상수를 정의했다.



* 1. final 상수

final 제어자를 사용하여 한 번 지정하면 바뀌지 않게 설정되며, 동시에 static을 사용하여 메모리에 한 번만 할당되게 설정된다. 하지만 이 방법은 접근제어자들 때문에 가독성이 좋지 못하다라는 단점이 존재한다.

``` java
class EnumExample {
    private final static int MONDAY = 1;
    private final static int TUESDAY = 2;
    private final static int WEDNESDAY = 3;
    private final static int THURSDAY = 4;
    private final static int FRIDAY = 5;
    private final static int SATURDAY = 6;
    private final static int SUNDAY = 7;
    
    public static void main(String[] args) {

        int day = EnumExample.MONDAY;

        switch (day) {
            case EnumExample.MONDAY:
                System.out.println("월요일 입니다.");
                break;
            case EnumExample.TUESDAY:
                System.out.println("화요일 입니다.");
                break;
            case EnumExample.WEDNESDAY:
                System.out.println("수요일 입니다.");
                break;
        }
    }
}
```




* 2. 인터페이스 상수


interface는 반드시 추상 메서드만 선언할 수 있는 것이 아니다. 인터페이스 내에서도 상수를 선언할 수 있는데, 인터페이스의 멤버는 public static final 속성을 생략할 수 있는 특징을 이용하여 코드를 좀 더 간결하게 작성할 수 있다.

``` java
interface DAY {
    int MONDAY = 1;
    int TUESDAY = 2;
    int WEDNESDAY = 3;
    int THURSDAY = 4;
    int FRIDAY = 5;
    int SATURDAY = 6;
    int SUNDAY = 7;
}

interface MONTH {
    int JANUARY = 1;
    int FEBRUARY = 2;
    int MARCH = 3;
    int APRIL = 4;
    int MAY = 5;
    int JUNE = 6;
    int JULY = 7;
    int AUGUST = 8;
    int SEPTEMBER = 9;
    int OCTOBER = 10;
    int NOVEMBER = 11;
    int DECEMBER = 12;
}
```
하지만 인터페이스로 상수를 정의하는 방법도 문제가 있다. 
조금 원론적인 문제이긴 한데, 상수가 결국은 정수값이라는 문제이다. 상수는 '변하지 않는 값'이라는 의미이지만 '고유한 값' 이라는 의미도 어느정도 내포하고 있다.
그래서 아래 코드 처럼 다른 집합에 정의된 상수들 끼리 서로 비교하는 로직이 가능하거나, 잘못된 상수가 할당되었음에도 결국은 정수값이기 때문에 컴파일 에러없이 실행된다는 점이다. 이것이 무엇이 문제냐 일수도 있겠지만 프로그램 크기가 커질수록 개발자의 실수가 잦아지며 이러한 제약적이지 않는 요소들 때문에 프로그램에 버그가 발생하게 되는 것이다.

``` java
public static void main(String[] args) {

    int day = DAY.MONDAY;

    // 상수를 비교하는 논리적으로 잘못된 행위를 함으로써 day 변수에 다른 상수값이 들어가버림
    if (DAY.MONDAY == MONTH.JANUARY) {
        // ...
        day = MONTH.JANUARY;
    }

    // day 변수에 있는 상수는 MONTH 상수이기 때문에 조건문에서 걸러져야 되지만,
    // 결국 정수값이기 때문에 에러가 안나고 그대로 수행됨 -> 프로그램 버그 발생 가능성
    switch (day) {
        case DAY.MONDAY:
            System.out.println("월요일 입니다.");
            break;
        case DAY.TUESDAY:
            System.out.println("화요일 입니다.");
            break;
        case DAY.WEDNESDAY:
            System.out.println("수요일 입니다.");
            break;
    }
}
```



* 3. 자체 클래스 상수
 

상수를 정수값으로 구성하는 것이 아니라 독립된 고유의 객체로 선언하여 자체 클래스로 인스턴스화해 상수처럼 사용하는 기법도 있다.

자기 자신 객체를 인스턴스화 하고 final static화 함으로써 고유의 객체를 만들어 이를 상수처럼 사용하는 것이다.

``` java

class Day {
    // 자기 자신 객체를 인스턴스화 하고 final static 화 함으로써 고유의 객체 상수를 얻게 됨
    public final static Day MONDAY = new Day();
    public final static Day TUESDAY = new Day();
    public final static Day WEDNESDAY = new Day();
}

class Month {
    public final static Month JANUARY = new Month();
    public final static Month FEBRUARY = new Month();
    public final static Month MARCH = new Month();
}
```

하지만 가독성이 안좋고 switch문을 사용할 수 없다는 큰 단점이 있다.

``` java
public static void main(String[] args) {

    Day day = Day.MONDAY;

    // if문은 문제 없지만
    if(day == Day.MONDAY) {
        System.out.println("월요일 입니다.");
    }

    // switch문에서는 사용할수 없다
    switch (day) {
        case DAY.MONDAY:
            System.out.println("월요일 입니다.");
            break;
        case DAY.TUESDAY:
            System.out.println("화요일 입니다.");
            break;
        case DAY.WEDNESDAY:
            System.out.println("수요일 입니다.");
            break;
    }
}
```

* * *

## enum 상수

이와 같은 문제들 때문에 자바에서 아예 상수만을 다루는 enum 타입 클래스를 만들었다.
자바의 enum은 인터페이스와 같이 **독립된 특수한 클래스**로 구분한다. 일종의 객체이기 때문에 힙(heap) 메모리에 저장되며 각 enum 상수들은 별개의 메모리 주소값을 가짐으로써 완벽히 독립된 상수를 구성할 수 있다.

### enum의 장점

* 코드가 단순해지며 가독성이 좋아진다.
* 허용 가능한 값들을 제한하여 유형 안전(type safe)을 제공한다.
* 키워드 enum을 사용하기 때문에 구현의 의도가 열거임을 분명하게 나타낼 수 있다.
* 자체 클래스 상수와 달리 switch문에서도 사용할 수 있다.
* 단순 상수와 비교해 IDE의 적극적인 지원을 받을 수 있다. (자동완성, 오타검증, 텍스트 리팩토링 등)
* 리팩토링 시 변경 범위가 최소화 된다. (enum에서 한번에 관리하기 때문에 내용의 추가가 필요하더라도, enum 코드 외에 수정할 필요가 없다.)
* enum은 본질적으로 Thread safe인 싱글톤 객체이므로 싱글톤 클래스를 생성하는데에도 사용된다.

> 추가적으로 enum 성능은 정수 상수와 다르지 않으며 열거 타입을 메모리에 올리는 공간과 초기화 시간이 있지만 체감될 정도는 아니다.


* * * 

## Enum 기본 문법

### Enum 선언

열거 타입은 상수 데이터들의 집합이다. 따라서 아래와 같이 배열처럼 나열하여 표현하면 된다.

* enum명은 클래스와 같이 **첫 문자를 대문자**로 **나머지는 소문자**로 구성한다.
* 관례적으로, 열거 상수는 모두 대문자로 작성한다.
* 열거 상수가 여러 단어로 구성될 경우, 단어 사이를 밑줄(_)로 연결한다.

``` java
// 요일 열거 타입
enum Week {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

enum LoginResult {
	LOGIN_SUCCESS,
	LOGIN_FAILED
}
```

### Enum 참조 방식

Enum 타입 객체도 하나의 데이터 타입이므로 변수를 선언하고 사용하면 된다.

``` java
// 열거타입 변수 = 열거타입.열거상수;
Week monday = Week.MONDAY;
Week sunday = Week.SUNDAY;

Week today = null; // 참조 타입이기 때문에 null도 저장 가능
today = Week.SUNDAY;

// 주소값 비교
System.out.println(today == Week.SUNDAY); // true
```


### Enum 메서드 종류

String 같은 자바의 여러 클래스가 자체 내장 메서드를 가지고 있듯이, enum 역시 내장 메서드를 가지고 있다.

모든 Enum 타입은 컴파일 시에 java.lang.Enum 클래스를 상속하게 되어있기 때문에, java.lang.Enum에 선언된 메소드를 이요할 수 있다.

| 메서드 이름                  | 반환 타입      | 설명                               |
| ----------------------- | ---------- | -------------------------------- |
| `values()`              | `enum[]`      | 모든 enum 상수를 배열로 반환 (컴파일러가 자동 생성) |
| `valueOf(String name)`  | `enum`        | 이름에 해당하는 enum 상수를 반환             |
| `name()`                | `String`   | enum 상수의 **이름(문자열)** 반환          |
| `ordinal()`             | `int`      | enum 상수가 정의된 순서(0부터 시작) 반환       |
| `compareTo(Enum other)` | `int`      | enum 간 순서 비교                     |
| `equals(Object obj)`    | `boolean`  | 동일 enum 상수인지 비교                  |
| `hashCode()`            | `int`      | 해시코드 반환                          |
| `toString()`            | `String`   | 문자열 반환 (기본적으로 `name()`과 같음)      |
| `getDeclaringClass()`   | `Class<T>` | 이 enum 상수가 속한 enum 클래스 반환        |

#### 사용 예시
``` java
enum Week {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

Week w = Week.FRIDAY;

// name()
// 열거 객체의 문자열을 리턴
String weekName = w.name();
System.out.println(weekName); // Spring

// ordinal()
// 열거 객체의 순번(0부터 시작)을 리턴
// 전체 열거 객체 중 몇 번째 열거 객체인지 알려준다
int weekNum = w.ordinal();
System.out.println(weekNum); // 4

// compareTo()
// 열거 객체를 비교해서 순번 차이를 리턴 (시작점을 어느 열거 객체의 기준으로 몇번째 위치하는지)
Week w1 = Week.TUESDAY; // 2
Week w2 = Week.SATURDAY; // 6

// 열거 객체가 매개값의 열거 객체보다 순번이 빠르다 → 음수를 리턴
int compare1 = w1.compareTo(w2); // SATURDAY 기준으로 TUESDAY 위치 (6에서 2가 되기 위한 값)
System.out.println(compare1); // -4

// 열거 객체가 매개값의 열거 객체보다 순번이 늦다 → 양수를 리턴
int compare2 = w2.compareTo(w1); // TUESDAY 기준으로 SATURDAY 위치 (2에서 6가 되기 위한 값)
System.out.println(compare2); // 4


// valueOf()
// 문자열을 입력받아서 일치하는 열거 객체를 리턴
Week w3 = Week.valueOf("SUNDAY"); // w3 변수는 Week.SUNDAY 열거 객체를 참조하게 됨
System.out.println(w3); // SUNDAY

// values()
// 모든 열거 객체들을 배열로 리턴
Week[] w4 = Week.values();

System.out.println(Arrays.toString(w4)); // [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY]

for (Week type : Week.values()) { // 열거 순회
    System.out.println(type);  // 순서대로 열거 객체 출력
}
```


* * * 

### Enum 생성자와 필드 추가

* enum의 생성자는 무조건 private 또는 default 접근자여야 합니다 (public, protected 불가)

* enum은 외부에서 new로 인스턴스를 생성할 수 없습니다

* 생성자는 각 상수가 생성될 때 호출됩니다.

``` java
public enum Day {
    MONDAY("월요일"),
    TUESDAY("화요일");

    private final String koreanName;

    // 생성자 (private이 기본이므로 생략 가능)
    Day(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

```


### Enum 메서드 정의

``` java
public enum SampleEnum {
    // 상수별로 메서드 저으이
    CONSTANT1("value1") {
        @Override
        public void execute() {
            System.out.println("C1");
        }
    },
    CONSTANT2("value2") {
        @Override
        public void execute() {
            System.out.println("C2");
        }
    };
    // 필드
    private final String value;
    // 생성자
    SampleEnum(String value) {
        this.value = value;
    }
    // 일반 메서드
    public String getValue() {
        return value;
    }
    // 추상화 메서드 (위에서 override하여 정의 가능)
    public abstract void execute();
}
```

* * *

> 참고 1 : https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC

> 참고 2 : https://chatgpt.com/g/g-p-67ef79213e58819194ff7ec37770f500-jaba-gongbu/c/6818a1a3-32b4-8009-a09a-2d24d42aeb68
