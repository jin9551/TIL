# DispatcherServlet

## 디스패처 서블릿(DispatcherServlet)이란?

디스패처 서블릿의 dispatch는 '보내다'라는 뜻을 가지고 있다. 디스패처 서블릿은 __HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤로에 위임해주는 프론트 컨트롤러__라고 정의할 수 있다. 

좀 더 자세하게 설명하면, 클라이언트로부터 어떠한 요청이 들어오면 Tomcat과 같은 **서블릿 컨테이너**가 요청을 받게 된다. 그리고 이 모든 요청을 **프론트 컨트롤러**인 디스패처 서블릿이 가장 먼저 빋게 된다. 그러면 디스패처 서블릿은 공통적인 작업을 먼저 처리한 후에 해당 요청을 처리해야 할 컨트롤러를 찾아서 위임한다. 

## DispatcherServlet의 장점

Spring MVC 구조에서 디스패처 서블릿이 등장함에 따라 web.xml의 역할을 상당히 축소시켜주었음. 과거에는 모든 서블릿을 URL 매핑을 위해 __web.xml__에 모두 등록해 주어야 했지만, **디스패처 서블릿이 해당 애플리케이션으로 들어오는 모든 요청을 핸들링해주고 공통 작업을 처리**해 주어서 설정이 간단해 졌다.

``` xml
<!-- web.xml에서 dispatcherServlet 설정-->
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
		
	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```


## DispatcherServlet의 역할

1. 요청 수신
사용자가 /hello 같은 URL로 요청을 보내면 DispatcherServlet이 먼저 받습니다.

2. Handler Mapping
URL에 매핑된 Controller 클래스의 메서드(@RequestMapping 등) 를 찾습니다.

3. Handler Adapter 호출
해당 컨트롤러를 실행할 수 있도록 적절한 어댑터를 통해 메서드를 호출합니다.

4. ModelAndView 반환
컨트롤러는 결과(Model)와 보여줄 화면 이름(View 이름)을 담은 ModelAndView를 반환합니다.

5. ViewResolver 실행
view name을 실제 JSP, Thymeleaf 등 뷰 파일 경로로 해석합니다.

6. 응답 렌더링
최종적으로 화면을 만들어 사용자에게 HTML로 보여줍니다.

## Spring MVC 구조에서 흐름 요약

[사용자 요청]
      ↓
DispatcherServlet
      ↓
HandlerMapping → 어떤 Controller를 호출할지 결정
      ↓
HandlerAdapter → Controller 실행
      ↓
Controller → ModelAndView 반환
      ↓
ViewResolver → 뷰 경로 결정
      ↓
View (JSP, Thymeleaf 등)
      ↓
[사용자 응답]

* * *

### 참고 
> https://mangkyu.tistory.com/18
