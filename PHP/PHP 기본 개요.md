# PHP란?

PHP는 웹 개발에 널리 사용되는 서버 사이드 스크립트 언어이다. 동적인 웹사이트와 웹 애플리케이션을 만들 때 주로 사용되며, HTML 안에 직접 코드를 삽입할 수 있는 특징을 가지고 있다.


## PHP의 기본 개요

* PHP는 Personal Homepage tools의 줄임말이었지만, 지금은 **Hypertext Preprocessor**라고 정의된다.

* 용도 : 동ㅈ거 웹 페이지 생성, 폼 처리, 쿠키 관리, 데이터베이스 연동

* 장점 :
    * 배우기 쉬움
    * 오픈소스 (무료, 방대한 커뮤니티와 라이브러리)
    * 다양한 웹 호스팅 환경에서 기본 지원

## 기본 문법 예시

``` php
<?php
  echo "안녕하세요, PHP!";
?>
```

> 데이터베이스 연결 예시 (MySQL)

``` php
<?php
$conn = mysqli_connect("localhost", "username", "password", "database");

if (!$conn) {
  die("연결 실패: " . mysqli_connect_error());
}
echo "연결 성공!";
?>
```


## PHP의 주요 프레임워크

* Laravel – 현대적인 MVC 패턴, 보안 및 라우팅 기능 탁월

* CodeIgniter – 가볍고 빠른 퍼포먼스를 위한 프레임워크

* Symfony – 대형 프로젝트에 적합한 구조화된 프레임워크

PHP는 WordPress, Drupal, Joomla 같은 CMS의 기반이기도 해서 여전히 웹 세계에서 매우 중요한 역할을 한다고 한다.