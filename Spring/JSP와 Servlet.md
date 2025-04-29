# JSP와 Servlet

## JSP (Java Server Page)
* **JSP**는 HTML 문서 안에 자바 코드를 삽입해서 동적인 웹 페이지를 만들 수 있는 기술을 뜻함.
* __.jsp__  확장자를 가지며, **서버 측에서 HTML과 자바를 결합하여 동적 페이지를 생성**한다.
* 내부적으로는 JSP는 서블릿으로 변환되어 실행된다. 즉, JSP도 서블릿으로 동작하지만 작성 방식이 다르다. (JSP -> Servlet.java -> HTML)

``` java
// Controller에서 result.jsp로 데이터 전송
request.setAttribute("name", name);
request.getRequestDispatcher("/result.jsp").forward(request, response);
```

``` html
<!--result.jsp--> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h2>${name}님, 환영합니다!</h2>
</body>
</html>
```

## Servlet
* **Servlet**은 자바 기반 웹 애플리케이션에서 서버 쪽 로직을 처리하는 **자바 클래스**(서블릿 클래스를 상속 받음)이다.
* HTTP 요청을 받아 로직을 처리하고, 결과를 다시 응답으로 돌려주는 역할을 한다.
* HTML 코드도 자바 코드 내부에서 직접 생성해야 하므로, 복잡한 UI를 만들기에는 불편하다.


``` java
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 요청을 받으면
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String name = request.getParameter("username");
        // PrintWriter로 html 코드를 내부적으로 작성하여 결과 리턴
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>" + name + "님, 환영합니다!</h2>");
        out.println("</body></html>");
    }
}
```
* * * 

| 항목 | Servlet | JSP |
|------|--------|-----|
| **파일 형식** | `.java` | `.jsp` |
| **주 사용 용도** | 로직 처리 | 화면 출력 (프론트엔드) |
| **작성 방식** | 자바 코드 중심 | HTML 중심 + 자바 코드 삽입 |
| **가독성** | UI 작성이 불편함 | UI 작성이 쉬움 |
| **변환 과정** | 바로 컴파일됨 | 내부적으로 서블릿으로 변환된 후 컴파일됨 |
| **유지보수** | 복잡한 UI일수록 어려움 | 상대적으로 쉬움 |

* * *


### 참고
> JSP와 Servlet
>> https://velog.io/@effirin/Servlet%EA%B3%BC-JSP%EC%97%90-%EB%8C%80%ED%95%B4
>> https://m.blog.naver.com/acornedu/221128616501

> JSP 출력 과정
>> https://codevang.tistory.com/191