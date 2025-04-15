# 제네릭
## 제네릭이란 ?
자바에서 제네릭(Generics)은 클래스 내부에서 사용할 데이터 타입을 외부에서 지정하는 기법
객체별로 다른 타입의 자료가 저장될 수 있도록 해야한다.

자바에서 배열과 함께 자주 쓰이는 자료형이 리스트(List)인데, 다음과 같이 클래스 선언문법에 꺾쇠 괄호'<>'로 되어 있다
``` java
ArrayList<String> list = new ArrayList<>();
```
저 꺽쇠 괄호가 제네릭인데, 괄호 안에는 타입명을 기재한다. 그리하면 저 리스트 클래스 자료형의 타입은 String 타입으로 지정되어 문자열 데이터만 저장할 수 있게 된다.


## 타입 파라미터 정의

### 제네릭 타입이란
타입을 파라미터로 가지는 클래스와 인터페이스를 말한다
* 선언시 클래스 또는 인터페이스 이름 뒤에 <> 부호(다이아몬드 연산자)가 붙는다
* <> 사이에 타입 파라미터가 위치한다.

타입파라미터
* 일반적으로 대문자 알파벳 한문자로 표현한다
* 개발 코드에서 타입 파라미터 자리에 구체적인 타입을 지정해야 한다.

``` java
class FruitBox<T> {
    List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
}


// 제네릭 타입 매개변수에 정수 타입을 할당
FruitBox<Integer> intBox = new FruitBox<>(); 

// 제네릭 타입 매개변수에 실수 타입을 할당
FruitBox<Double> intBox = new FruitBox<>(); 

// 제네릭 타입 매개변수에 문자열 타입을 할당
FruitBox<String> intBox = new FruitBox<>(); 

// 클래스도 넣어줄 수 있다. (Apple 클래스가 있다고 가정)
FruitBox<Apple> intBox = new FruitBox<Apple>();
```

### 타입 파라미터 할당 가능 타입

제네릭에서 할당 받을 수 있는 타입은 Reference 타입 뿐이다. 
* int형이나 double형 같은 원시 타입(Primitive Type)을 제네릭 타입 파라미터로 넘길 수 없다.
* Wrapper 클래스 (Intger, Double, Long, ...) 가능
* 사용자 정의 클래스 (Apple,...) 가능


### 복수 타입 파라미터
제네릭은 2개, 3개 얼마든지 만들 수 있다.
제네릭 타입 구분은 꺽쇠 괄호 안에 쉼표(,)로 하며 <T, U> 와 같은 형식을 통해 복수 파라미터를 지정할 수 있다.

``` java
import java.util.ArrayList;
import java.util.List;

class Apple {}
class Banana {}

class FruitBox<T, U> {
    List<T> apples = new ArrayList<>();
    List<U> bananas = new ArrayList<>();

    public void add(T apple, U banana) {
        apples.add(apple);
        bananas.add(banana);
    }
}

public class Main {
    public static void main(String[] args) {
    	// 복수 제네릭 타입
        FruitBox<Apple, Banana> box = new FruitBox<>();
        box.add(new Apple(), new Banana());
        box.add(new Apple(), new Banana());
    }
}
```


### 타입 파라미터 기호 네이밍
제네릭 기호는 딱히 문법적으로 정해진 것이 없다.
* 명명하고 싶은대로 아무 단어나 넣어도 문제는 없지만, 대중적으로 통하는 통상적인 네이밍이 있으면 그걸 사용한다.

### 예시 )
``` java
<T>       타입(Type)
<E>       요소(Element), 예를 들어 List
<K>       키(Key), 예를 들어 Map<k, v>
<V>       리턴 값 또는 매핑된 값(Variable)
<N>       숫자(Number)
<S, U, V> 2번째, 3번째, 4번째에 선언된 타입

```


## 제네릭 타입을 사용의 효과

### 제네릭을 사용하므로 얻는 이점

```java
List list = new ArrayList(); // 타입 변환 제거
list.add("hello");
String str = (String) list.get(0);

List<String> list = new ArrayList<>();
list.add("hello");
String str = list.get(0);
```
컴파일시 강한 타입 체크를 할 수 있다. 실행시 타입 에러가 나는 것보다 컴파일시에서 미리 타입을 강하게 체크해서 에러를 사전에 방지한다.

위 코드 처럼 타입을 변환을 제거할 수 있다.



```java
// 빈번한 타입 변한 발생 -> 성능 저하
public class Box {
    private Object object;
    public void setObject(Obejct object){
        this.object = object;
    }
}
```

