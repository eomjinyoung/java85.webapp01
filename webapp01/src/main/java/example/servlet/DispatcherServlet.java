package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import example.worker.Worker;

@WebServlet("*.do")
public class DispatcherServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;
 
  // 스프링 IoC 컨테이너
  ApplicationContext applicationContext;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    // ContextLoaderListener가 준비한 스프링 IoC 컨테이너 꺼내기
    applicationContext = 
        WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
  }
  
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    //1) 클라이언트가 요청한 서블릿 경로를 알아낸다.
    //=> ServletRequest에는 서블릿 경로를 알아내는 메서드가 없다.
    //=> 파라미터로 넘어오는 객체는 원래 HttpServletRequest 객체이다.
    //=> 따라서 원래 타입으로 형변환해라!
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    
    //예) URL => http://localhost:8080/web02/board/list.do?pageNo=2&length=5
    // getServletPath()의 리턴 값 => /board/list.do
    String servletPath = httpRequest.getServletPath();
    
    //2) 서블릿 경로를 가지고 그 요청을 처리할 워커를 찾는다.
    Worker worker = (Worker)applicationContext.getBean(servletPath);
    
    //3) 해당 URL을 처리할 워커가 있다면 실행하고, 없다면 안내 메시지를 출력한다.
    if (worker != null) {
      try {
        worker.execute(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>오류!</title></head>");
      out.println("<body>");
      out.println("<h1>오류!</h1>");
      out.println("<p>해당 URL을 처리할 수 없습니다.</p>");
      out.println("</body></html>");
    }
  }
}











