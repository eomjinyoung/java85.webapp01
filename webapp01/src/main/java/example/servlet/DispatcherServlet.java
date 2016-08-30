package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import example.worker.Worker;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
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
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String servletPath = request.getServletPath();
    
    Worker worker = (Worker)applicationContext.getBean(servletPath);
    
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











