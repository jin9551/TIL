# 객체지향 프로그래밍(OOP)란?
 
자바의 OOP(Object-Oriented Programming, 객체지향 프로그래밍)는 객체(Object)를 중심으로 프로그램을 설계하고 

구성하는 패러다임이다. 자바는 객체지향 언어로 설계되었기 때문에 OOP의 4대 핵심 개념을 중심으로 구조화된다.
 
-------------------

## 객체란?

여기서 객체란 현실 세계의 사물이나 개념을 프로그램 안에서 표현한 실체를 의미한다.

즉, 어떤 "것"을 코드로 표현했을 때, 그 "것"이 바로 객체이다.

> 객체는 속성(데이터)과 동작(기능)을 함께 가지고 있는 독립된 단위이다.
 
 
객체의 예시를 하나 들어보겠다.

강아지를 객체로 표현한다면 다음과 같다.

* 속성(필드): 이름, 나이, 품종, 색깔 등 → 데이터

* 동작(메서드): 짖는다, 뛴다, 먹는다 등 → 기능

``` java
public class Dog {
    // 속성(필드, 데이터)
    String name; // 개 이름
    int age;     // 개 나이
	
    // 동작(메서드)
    void bark() {
        System.out.println("멍멍!");
    }
}
```
 
이렇게 클래스를 정의하고 실제로 사용할 때 객체를 생성해서 쓰면 된다.

``` java
Dog myDog = new Dog();  // Dog 클래스를 이용해 "객체" 생성
myDog.name = "콩이";
myDog.age = 2;
myDog.bark(); // 출력: 멍멍!
```
 
 -------------------

## 4대 핵심 개념

다시 OOP로 돌아와서 4대 핵심 개념에 대해 적어보겠다.
 
OOP의 4대 핵심 개념은 다음과 같다:

* 캡슐화(Encapsulation)
* 상속(Inheritance)
* 다형성(Polymorphism)
* 추상화(Abstraction)

예시 코드와 함께 각 개념의 정의와 목적을 알아보겠다.
-------------------


### 1. 캡슐화 (Encapsulation)

* 정의: 객체의 속성과 메서드를 하나로 묶고, 외부에서 직접 접근하지 못하도록 보호하는 것

* 목적: 데이터 보호, 유지보수 용이성

* 예시:

``` java
public class User {
    private String name; // 외부에서 직접 접근 불가

    public String getName() {  // 외부에서 호출 가능
        return name; // getter
    }

    public void setName(String name) { // 외부에서 호출 가능
        this.name = name; // setter
    }
}
```
 
캡슐화는 관련이 있는 변수와 함수를 하나의 클래스로 묶고, 외부에서 쉽게 접근하지 못하도록 은닉하는 게 핵심이다. 

이것을 정보은닉(Information Hiding)이라 한다.
 
정보은닉의 장점은 다음과 같다.

* 유지보수나 확장 시 오류의 범위를 최소화 할 수 있다.
* 객체의 정보손상 및 오용을 방지한다.
* 조작법이 바뀌어도 사용법 자체는 바뀌지 않는다 (함수 호출)
* 데이터가 바뀌어도 다른 객체에 영향을 주지 않기 때문에 독립성이 보장된다.
* 모듈화가 가능하다.

위에 예시로 든 User 클래스의 경우 데이터(String name)은 직접 접근이 불가능하고, 해당 데이터를 변경하기 위해서는 함수(getName(), setName())으로만 가능하다. 
 
-------------------

### 2. 상속 (Inheritance)

* 정의: 기존 클래스(부모)의 속성과 기능을 새로운 클래스(자식)가 물려받는 것

* 목적: 코드 재사용성 증가, 계층적 구조화

* 예시:

``` java
public class Animal {                 // 부모 클래스
	String name;
    public void eat() {
        System.out.println("먹는다");
    }
}

public class Dog extends Animal {    // 부모 Animal을 상속 받은 자식 클래스
    public void bark() {
        System.out.println("짖는다");
    }
}
```

상속 개념은 쉽게 말해 부모 클래스(상위 클래스)와 자식 클래스(하위 클래스)가 있으며, 

자식 클래스는 부모 클래스의 대부분의 것을 상속 받아 그대로 쓸 수 있다는 것을 의미한다. (private 접근 제한을 갖는 데이터 및 함수는 상속받지 못한다.)

상속을 하는 이유는 간단하다. 이미 만들어진 클래스를 재사용하여 코드의 재사용성을 늘리고, 개발 시간을 단축시키기 위해서다.
 
위에 예시로 든 Dog는 Animal의 name과 bark()를 상속 받아서 사용 가능하다.

``` java
Dog doggy = new Dog();
dog.name = "White";   // Dog 내부에는 없지만 Animal의 name을 상속 받아서 사용 가능
dog.bark();          
dog.eat();             // 위와 마찬가지
```
 

### 3. 다형성 (Polymorphism)

* 정의: 같은 타입의 참조 변수가 다양한 객체를 참조할 수 있도록 함

* 종류:
    * 오버로딩(Overloading): 같은 메서드 이름을 다른 파라미터(매개변수)를 주어 중복 정의
    * 오버라이딩(Overriding): 부모 메서드를 자식이 재정의

* 예시:

``` java
class Animal {
    public void sound() {
        System.out.println("소리");
    }
}

class Cat extends Animal {           // Animal을 상속 받음

    // 오버라이딩
    @Override					     
    public void sound() {            // Animal의 sound()를 Cat의 sound()로 재정의
        System.out.println("야옹");
    }
    
    // 오버로딩
    public void eat() {            
        System.out.println("냠냠");
    }
    
    
    public void eat(String eatSound) {   // eat()와 같은 이름이지만 다른 매개변수를 가졌음 
        System.out.println(eatSound);
    }

    
    
}

Animal a = new Cat();
a.sound(); // "야옹"
String catFood = "챱챱"
a.eat(); // "냠냠"
a.eat(catFood); // "챱챱"

``` 

다형성이란 하나의 객체나 메소드가 여러 가지 다른 형태를 가질 수 있는 것을 말한다.

서로 상속 관계에 있는 부모 클래스와 자식 클래스 사이에서만 사용된다. 

업캐스팅과 다운캐스팅도 알아두면 좋다.
 
-------------------

### 4. 추상화 (Abstraction)

* 정의: 복잡한 내부 구현은 숨기고, 필요한 부분만 노출하는 것
* 방법:
    * 추상 클래스: abstract class와 abstract method
    * 인터페이스: 다중 구현 지원
* 예시:

``` java
// abstract class 추상화 클래스
// 일반 메서드  또는 멤버 변수를 가질 수 있다.

abstract class Machine {
    public String country;   // 멤버 변수
     
    public void stop() {     // 일반 메서드
		System.out.println("정지");
    }
    
    // abstract method 추상화 메서드
    abstract void operate();    // Machine을 상속 받는 하위 클래스는 operate를 구현해야한다.
}

class Printer extends Machine {
    void operate() {            // operate를 구현한다.
        System.out.println("인쇄 중...");
    }
}


// interface 인터페이스
// 상수와 추상메서드만 가질 수 있다. 
// (java 8 버전부터는 default와 static 메서드가 추가되어 사용 가능하다.)

public interface Animal {
    public static final String name = "이름";
    final int weight = 10;
    static int age = 1;
    

    abstract void sound();    // Animal을 상속 받는 하위 클래스는 sound를 구현해야한다.
}

public interface Feline {
    abstract void move();    // Feline을 상속 받는 하위 클래스는 move를 구현해야한다.
}

// 다중 상속 가능
class Cat implements Animal,Feline {
    void sound() {            // sound를 구현한다.
        System.out.println("야옹");
    }
    
    void move() {            // move를 구현한다.
        System.out.println("사뿐사뿐");
    }
    
}
```
 
추상화는 상위 클래스에서 클래스들의 공통적인 속성과 동작을 정의하고 이를 하위 클래스에서 구현하는 것이다.

간단하게 말하자면 개집 구조의 설계도만 작성하고 이를 만들어서 꾸미는 것은 제작자한테 맡기는 것이다.
 
추상화는 abstract 제어자를 가지는 abstract class와 abstract method

혹은 인터페이스(interface)로 만들 수 있다.
 
 
 
이상으로 자바의 객체지향 프로그래밍에 대해 간단하게 적어봤다.

출처: https://hobbyatelier.tistory.com/113 [취미공방:티스토리]