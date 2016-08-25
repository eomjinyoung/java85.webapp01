package example.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppInitServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); // 원래 하던 일은 하고!
    
    //1) Spring IoC 컨테이너를 준비한다.
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        config.getInitParameter("contextConfigLocation"));
    
    //2) ServletContext 창고를 얻는다.
    ServletContext servletContext = config.getServletContext();
    
    //3) Spring IoC 컨테이너를 ServletContext 창고에 보관한다.
    servletContext.setAttribute("applicationContext", applicationContext);
  }
  
  @Override
  public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
    // 직접 클라이언트가 사용할 서블릿이 아니기 때문에
    // 특별히 이 메서드에 코드를 작성하지 않는다.
  }

}
