package example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// 이 클래스는 웹 애플리케이션이 시작될 때 스프링 IoC 컨테이너를 준비하는 일을 한다.
public class ContextLoaderListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("ContextLoaderListener.contextInitialized()...");
    
    //1) ServletContext 창고를 얻는다.
    ServletContext servletContext = sce.getServletContext();

    //2) Spring IoC 컨테이너를 준비한다.
    //   => 스프링 설정 파일의 위치 정보를 담은 파라미터는 
    //      컨텍스트 파라미터이다.
    //   => 필터나 서블릿과 달리 리스너 설정에 파라미터 정보를 설정할 수 없다.
    //      대신 글로벌 컨텍스트 파라미터 정보를 설정하고 가져올 수 있다.
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        servletContext.getInitParameter("contextConfigLocation"));
    
    
    //3) Spring IoC 컨테이너를 ServletContext 창고에 보관한다.
    servletContext.setAttribute("applicationContext", applicationContext);
    
    
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }

}
