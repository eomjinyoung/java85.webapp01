package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  //상속 받은 메서드 중에서 어떤 것을 오버라이딩 할 것인가?
  //doGet(), doPost(), 
  //service(HttpServletRequest, HttpServletResponse)
  //service(ServletRequest, ServletResponse)
  //=> GET과 POST 요청에 대해 모두 처리해야 하기 때문에
  //   service()를 오버라이딩하는 것이 좋다.
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("<title>실행 오류!</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>실행 오류!</h1>");
    
    //ServletRequest에 보관된 오류 정보를 꺼내 출력한다.
    Exception error = (Exception)request.getAttribute("error");
    out.println(error.getMessage());
    
    out.println("</body>");
    out.println("</html>");
  }
}











