# JPA와 Hibernate 

## JPA(Java Persistent API)란?

> https://dbjh.tistory.com/77 참고

JPA란 **자바 ORM(Object Relational Mapping) 기술에 대한 API 표준 명세**를 의미한다.

JPA는 특정 기능을 하는 라이브러리는 아니고 **ORM을 사용하기 위한 인터페이스를 모아둔 것**이다.

JPA는 자바 어플리케이션에서 관계형 데이터베이스를 어떻게 사용해야 하는지를 정의하는 방법 중 한 가지이다.

JPA를 사용하기 위해서는 **JPA를 구현한 Hibernate, EclipseLink, DataNucleus와 같은 ORM 프레임워크를 사용**해야 한다.

## Hibernate

Hibernate를 많이 사용하는 이유는 가장 범용적으로 다양한 기능을 제공하기 때문이다.

Hibernate는 JPA의 구현체 중 하나이며, SQL을 사용하지 않고 직관적인 코드(메서드)를 사용해 데이터를 조작할 수 있다.
Hibernate는 내부적으로 JDBC API를 사용하고 있으며, 단지 개발자가 직접 SQL을 작성하지 않을 뿐이다.

* 구조도
Application > Hibernate > JPA > JDBC API  <> DB


* Session은 EntityManager
* SessionFactory는 EntityMangerFactory
* Transaction은 EntityTransaction
들이 각각의 자바 인터페이스를 구현했다.



* * *

## Spring Data JPA

Spring Data JPA는 Spring에서 제공하는 모듈 중 하나로 JPA를 쉽고 편하게 사용할 수 있도록 도와준다.

기존 JPA를 사용하려면 EntityManager를 주입받아 사용해야 하지만 Spring Data JPA는 JPA를 한 단계 더 추상화 시킨 Repository 인터페이스를 제공한다.

Spring Data JPA가 JPA를 추상화 했다는 말은, Spring Data JPA의 Repository의 구현에서 JPA를 사용하고 있다는 뜻.

사용자가 Repository 인터페이스에 정해진 규칙대로 메소드를 입력하면, Spring이 알아서 해당 메소드 이름에 적합한 쿼리를 날리는 구현체를 만들어 빈으로 등록해준다.


### Hibernate와 Spring Data JPA의 차이점
하이버네이트는 JPA 구현체이고, 스프링 데이터 JPA는 JPA에 대한 데이터 접근의 추상화라고 말할 수 있다.
스프링 데이터 JPA는 GenericDao라는 커스텀 구현체를 제공한다. 이것의 메소드의 명칭으로 JPA 쿼리들을 생성할 수 있다.
 
Spring Data를 사용하면 Hibernate, Eclipse Link 등의 JPA 구현체를 사용할 수 있다.
또 한가지는 @Transaction 어노테이션을 통해 트랜잭션 영역을 선언하여 관리할 수 있다.
 
Hibernate는 낮은 결합도의 이점을 살린 ORM 프레임워크로써 API 레퍼런스를 제공한다.
여기서 반드시 기억해야할 점은 **Spring Data JPA는 항상 Hibernate와 같은 JPA 구현체가 필요**하다.


> https://dev-coco.tistory.com/74 참고