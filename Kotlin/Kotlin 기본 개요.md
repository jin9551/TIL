# Kotlin이란? 

코틀린(Kotlin)은 JetBrains에서 개발한 프로그래밍 언어로, **JVM(Java Virtual Machine) 위에서 동작**하며 **Java와 100% 상호 운용 가능**한 것이 가장 큰 특징이다. Android 앱 개발에 널리 사용되며, Java보다 간결하고 안전한 문법을 제공한다. 


| 특징                  | 설명                                                  |
| ------------------- | --------------------------------------------------- |
| **Java 호환성**        | Java 코드와 상호 운용 가능 (Java 코드 사용, Kotlin에서 호출, 반대도 가능) |
| **간결성**             | Null 안전, 타입 추론, 람다 등으로 코드가 훨씬 짧고 명확                 |
| **Null 안전성**        | `null` 참조 오류 방지를 위한 언어 차원의 지원 (`?`, `!!` 등)         |
| **함수형 프로그래밍 지원**    | 람다, 고차 함수, 컬렉션 처리 함수 등 지원                           |
| **안드로이드 개발 공식 언어**  | Google이 2017년부터 Android 공식 언어로 채택                   |
| **코루틴(Coroutines)** | 경량 스레드를 지원하는 비동기 프로그래밍 모델 내장                        |



## 자바 VS 코틀린

``` java

//java
public String greet(String name) {
    if (name == null) {
        return "Hello, Guest";
    }
    return "Hello, " + name;
}
```

``` kotlin

// Kotlin
fun greet(name: String?): String {
    return "Hello, ${name ?: "Guest"}"
}

// 변수 선언

val name = "Jin"     // 변경 불가능 (immutable)
var age = 30         // 변경 가능 (mutable)

// 함수 선언

fun add(a: Int, b: Int): Int {
    return a + b
}


// null 처리

var name: String? = null
val length = name?.length ?: 0


// 클래스 선언

class Person(val name: String, var age: Int)

```


## 코틀린을 사용하는 주요 환경


* Android 앱 개발 (최대 시장 점유율)

* 서버 백엔드 (Spring + Kotlin 가능)

* 웹 프론트엔드 (Kotlin/JS)

* 네이티브 앱 개발 (Kotlin/Native)

* 스크립트 언어로도 사용 가능