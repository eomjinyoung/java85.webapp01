package example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/* 이 필터는 서블릿에서 사용할 Spring IoC Container를 준비하는 일을 한다.
 * 클라이언트 요청에 대해 적용하는 것은 아니기 때문에 URL을 지정할 필요는 없다.
 * 
 * 필터를 등록하면 웹 애플리케이션이 시작될 때 자동으로 객체가 생성된다.
 * 즉 init()가 자동으로 호출된다는 의미이다.
 * 
 */
public class AppInitFilter implements Filter {

  @Override
  public void init(FilterConfig config) throws ServletException {
    System.out.println("AppInitFilter.init()...");
    
    //1) Spring IoC 컨테이너를 준비한다.
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        config.getInitParameter("contextConfigLocation"));
    
    //2) ServletContext 창고를 얻는다.
    ServletContext servletContext = config.getServletContext();
    
    //3) Spring IoC 컨테이너를 ServletContext 창고에 보관한다.
    servletContext.setAttribute("applicationContext", applicationContext);
    
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
  }

  @Override
  public void destroy() {
  }

}
