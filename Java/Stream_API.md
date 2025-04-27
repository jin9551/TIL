# Java Stream API

## 개요

자바의 **Stream API**는 Java 8부터 도입된 기능으로, **컬렉션(Collections)** 데이터를 처리할 때 **선언형(Declarative)** 방식으로 **간결하고 효율적으로 작업할 수 있도록** 도와주는 기능이다.
쉽게 말하면, **데이터를 필터링하고, 변형하고, 집게하는 일련의 작업을 파이프라인처럼 연결해서 처리**할 수 있는 도구의 모음이다.

> Stream API는 단독으로 뭔가 하는 게 아니라.
> "데이터 → Stream → 연산들"이라는 전체 흐름 속에서 사용된다는 점이 핵심이다.

* * *

## 1. Stream 만드는 방법 (데이터 → Stream)

Stream API를 쓰기 위해서는 **반드시 "스트림 객체"를 만들어야 한다**. 즉, Stream은 "데이터 소스"로부터 시작해서, 그 위에 중간 연산과 최종 연산을 쌓는 구조이다.

가장 많이 쓰는 순서부터 작성해 보겠다.

### 1. 컬렉션(Collections)으로부터

``` java
List<String> list = List.of("A", "B", "C");
Stream<String> stream = list.stream();
```

* .stream() → 순차 스트림
* .parrallelStream() → 병렬 스트림(멀티코어 처리)

list.stream()은 Stream<String> stream = list.stream()과 같이 스트림 객체 선언을 간략화 한것이다.

### 2. 배열로부터

``` java
String[] arr = {"A", "B", "C"};
Stream<String> stream = Arrays.stream(arr);
```

* Collections를 상속 받지 않은 Arrays(배열)은 Arrays.stream()을 이용하여 스트림 객체를 생성 가능하다.

### 3. Stream 클래스의 정적 메서드 사용

``` java
Stream<String> stream = Stream.of("A", "B", "C");

Stream<Integer> nums = Stream.iterate(1, n -> n + 1); // 1, 2, 3, ...

Stream<String> stream = Stream.of("a", "b", "c"); 

Stream<int[]> intArrayStream = Stream.of(new int[]{1, 2, 3});
```

* .of() → 가변 인자를 스트림으로
* .generate(Supplier) → 무한 스트림 (ex: 랜덤값 반복 생성)
* .iterate(seed, UnaryOperator) → 무한 반복 스트림

### 4. 파일 등 외부 자원

``` java
Stream<String> lines = Files.lines(Path.get("file.txt"));
```

### 5. Stream.builder()를 활용하여 수동으로 만들 때

``` java
// 버전 1.
Stream.Builder<String> builder = Stream.builder();

builder.add("apple");
builder.add("banana");
builder.add("cherry");

Stream<String> stream = builder.build();

// 버전 2.
Stream<String> stream2 = Stream.<String>builder()
    .add("apple")
    .add("banana")
    .add("cherry")
    .build();
```

* .build()를 호출한 후에는 .add()를 더 이상 사용할 수 없음. (불변 스트림 생성)


* * *

내 블로그 참조 : https://hobbyatelier.tistory.com/103