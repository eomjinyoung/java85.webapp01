package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

    Object pageController = findPageController(servletPath);
    Method requestHandler = null;
    if (pageController != null) {
      requestHandler = findRequestHandler(servletPath, pageController);
    }
    
    if (requestHandler != null) {
      try {
        String url = (String)requestHandler.invoke(pageController, request, response);
        
        if (url.startsWith("redirect:")) {
          response.sendRedirect(url.substring(9)); // url에서 "redirect:" 접두어 제거한다.
        } else {
          RequestDispatcher rd = request.getRequestDispatcher(url);
          rd.forward(request, response);
        }
        
      } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", e);
        RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
        rd.forward(request, response);
        return;
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

  private Method findRequestHandler(String servletPath, Object pageController) {
    Class<?> clazz = pageController.getClass();
    Method[] methods = clazz.getMethods();
    RequestMapping mapping = null;
    
    for (Method m : methods) { 
      mapping = m.getAnnotation(RequestMapping.class);
      if (mapping == null) 
        continue;
      if (servletPath.endsWith(mapping.value()[0] + ".do")) {
        return m;
      }
    }
    return null;
  }

  private Object findPageController(String servletPath) {
    Map<String,Object> controllerMap = applicationContext.getBeansWithAnnotation(Controller.class);
    Class<?> clazz = null;
    RequestMapping mapping = null;
    
    for (Object controller : controllerMap.values()) {
      clazz = controller.getClass();
      mapping = clazz.getAnnotation(RequestMapping.class);
      if (servletPath.startsWith(mapping.value()[0])) {
        return controller;
      }
    }
    return null;
  }
}











