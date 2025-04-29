# Model1과 Model2 아키텍처 

## 개요

Model1과 Model2는 **JSP(JavaServer Pages)** 기반의 웹 애플리케이션 아키텍처(Web Application Architecture)를 구분할 때 사용하는 개념이다.

* * * 

## Model1 아키텍처

> JSP가 클라이언트의 요청에 대한 로직 처리와 view에 대한 처리를 모두 수행한다.

* JSP가 모든 역할(비즈니스 로직 + 화면 출력, Controller + View)을 담당
* Servlet 없이 JSP만으로 처리 가능

#### 장점 
* 구조가 단순해서 소규모 프로젝트에 빠르게 적용 가능

#### 단점
* 유지보수가 어려움 (로직과 뷰가 섞여 있음)
* 코드 재사용 및 테스트 어려움
* 협업에 비효율적(디자이너와 개발자 분업 불가)

* * * 

## Model2 아키텍처

> View와 Controller가 분리된 구조를 가지고 있음

Model2 아키텍처는 **MVC 패턴**을 웹 개발에 도입한 구조이다.
클라이언트 요청에 대한 처리는 Servlet(Controller)과 Model이, 결과는 JSP(View)가 처리

### MVC 패턴 (Model-View-Controller Pattern)

| 역할        | 구현체 예시 |설명                              |
|-------------|----------|-----------------------------------|
| Controller  | Servlet  |사용자의 요청을 처리 (Servlet)     |
| Model       | Service, Dao, Beans| 비즈니스 로직 및 데이터 처리 클래스 |
| View        | JSP      | 결과 화면 출력 (JSP)              |

> 참고
>> https://m.blog.naver.com/jhc9639/220967034588

#### 장점
* view 코드와 로직 코드가 분리되어 있어 Model1에 비해 복잡도가 낮다
* 백엔드와 프론트 앤드가 분리되어 있어 분업이 용이하다.
* 유지보수가 Model1과 비교하여 쉽다.
* 확장성이 뛰어나다.

#### 단점
* 구조가 복잡하여 진입 장벽이 높다


* * * 


### 참고
> https://devdange.tistory.com/entry/Web-Model1-Model2-MVC-%ED%8C%A8%ED%84%B4%EC%9D%98-%EA%B5%AC%EC%A1%B0%EC%99%80-%EC%9E%A5%EB%8B%A8%EC%A0%90

> https://velog.io/@ovan/MVC-Model1%EA%B3%BC-MVC-Model2
