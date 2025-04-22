# 자바 SOLID 원칙이란? (객체 지향 설계 5원칙)

자바에서의 SOLID 원칙은 객체지향 설계(OOD, Object-Oriented Design)의 다섯 가지 핵심 원칙을 말한다.
 
> SOLID  원칙은 유지보수성과 확장성을 높이기 위한 가이드라인이며 다음 다섯 가지 원칙의 앞글자를 딴 약어이다.
 

1. SRP : 단일 책임 원칙
2. OCP : 개방 폐쇄 원칙
3. LSP : 리스코프 치환 원칙
4. ISP : 인터페이스 분리 원칙
5. DIP : 의존 역전 원칙

* * *

## 1. S - 단일 책임 원칙 (Single Responsibility Principle, SRP)

> 클래스는 하나의 책임만 가져야 한다.


* 한 클래스가 하나의 기능을 가져야 한다는 뜻.
* 예: ReportPrinter 클래스는 보고서를 출력하는 역할만 해야 하며, 보고서를 생성하거나 저장하는 책임은 가지면 안 됨.

 

``` java
class ReportPrinter {  // 출력하는 역할
    public void print(String report) {
        System.out.println("Printing" + report);
    }
}


class ReportScanner {  // 스캔하는 역할
    public void print(String report) {
        System.out.println("Scanning : "+report);
    }
}
```

* * *

## 2. O - 개방-폐쇄 원칙 (Open/Closed Principle, OCP)

> 소프트웨어 요소는 확장에는 열려 있어야 하고, 변경에는 닫혀 있어야 한다.


* 기능을 수정할 때 기존 코드를 변경하지 않고, 새로운 클래스를 확장(상속, 위임 등) 하여 구현해야 함.
* 간단하게 생각하면 추상화 사용을 통한 관계 구축(상속)을 권장하는 뜻이다.
* 예: 직사각형(Rectangle)과 원(Circle)이라는 객체를 구현해야 할 때, Shape라는 추상화 클래스가 있다면 이것을 확장(상속)하여 구현 가능.

 

``` java
abstract class Shape {          
    abstract double area();
}

class Rectangle extends Shape {  // Shape을 확장(extends)
    double width, height;
    public Rectangle(double w, double h) { this.width = w; this.height = h; }
    double area() { return width * height; }
}

class Circle extends Shape {    // Shape을 확장(extends)
    double radius;
    public Circle(double r) { this.radius = r; }
    double area() { return Math.PI * radius * radius; }
}
```

* * *

## 3. L - 리스코프 치환 원칙 (Liskov Substitution Principle, LSP)

> 자식 클래스는 언제나 부모 클래스를 대체할 수 있어야 한다.


* 부모 클래스의 객체를 사용하는 프로그램에서 자식 클래스로 교체해도 문제가 없어야 함. (업캐스팅을 하여도 문제없어야 함)
* 잘못된 상속은 이 원칙을 위배함. (자식 클래스에서 부모 클래스의 메서드를 오버라이딩하는 경우)

``` java 
class Bird {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

class Eagle extends Bird {
    
    public int fly(int a) {   // 자기 멋대로 오버로딩을 해버림
        System.out.println("it flew " + a + " meters");
    }
}
```

→ 이건 LSP 위반. Eagle이 Bird의 fly를 오버로딩 해버렸기 때문에, 부모 클래스를 자식 클래스로 교체했을 때 원하는 결과가 나오지 않는다.

* * *

## 4. I - 인터페이스 분리 원칙 (Interface Segregation Principle, ISP)

> 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.


* 사용하지 않는 메서드를 구현하게 만드는 비대한 인터페이스는 나쁘다. 
* 인터페이스는 목적에 맞게 작게 쪼개야 함. (SRP는 클래스를 분리, ISP는 인터페이스를 분리)
* 한 번 인터페이스를 분리하여 구성해 놓고 나중에 수정사항이 생겨서 또 인터페이스를 분리하는 행위는 지양해야 한다.

``` java 
interface Printer {   // 프린터의 인터페이스
    void print();
}

interface Scanner {   // 스캐너의 인터페이스
    void scan();
}

// 사용자는 필요한 인터페이스만 구현
class SimplePrinter implements Printer {
    public void print() {
        System.out.println("Printing...");
    }
}
```

* * *

## 5. D - 의존 역전 원칙 (Dependency Inversion Principle, DIP)

> 고수준 모듈은 저수준 모듈에 의존해서는 안 된다. 둘 다 추상화에 의존해야 한다.


* 구현 클래스가 아니라 인터페이스나 추상 클래스에 의존하게 만들어야 함.
* 의존 관계를 맺을 때 변화하기 쉬운 것 또는 자주 변화하는 것보다는 , 변화하기 어려운 것이나 거의 변화하가 없는 것에 의존하라는 뜻
* 인터페이스나 추상 클래스는 일종의 설계도이기 때문에 변화가 없음. 만약 어떤 클래스를 참조해야 하는 상황이 생긴다면, 그 클래스를 직접 참조하는 것이 아니라 그 대상의 상위 요소(설계도)를 참조하라는 뜻.

 
``` java
interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender {
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class NotificationService {
    private MessageSender sender;

    // 생성자 주입
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notify(String message) {
        sender.send(message);
    }
}
```

* * *

출처: https://hobbyatelier.tistory.com/114 [취미공방:티스토리]