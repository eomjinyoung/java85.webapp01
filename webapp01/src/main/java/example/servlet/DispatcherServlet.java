package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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
    //예) http://localhost:8080/web02/board/list.do?pageNo=2
    //- 서버 루트 : /
    //- 웹 애플리케이션 경로(컨텍스트 경로): /web02
    //- 서블릿 경로 : /board/list.do
    //- 쿼리스트링(Query String): ?pageNo=2
    String servletPath = request.getServletPath();

    Object pageController = findPageController(servletPath);
    Method requestHandler = null;
    if (pageController != null) {
      requestHandler = findRequestHandler(servletPath, pageController);
    }
    
    if (requestHandler != null) {
      try {
        // 메서드에 전달할 파라미터 준비하기
        Object[] params = prepareRequestHandlerParameters(requestHandler, request, response);
        
        String url = (String)requestHandler.invoke(pageController, params);
        
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

  private Object[] prepareRequestHandlerParameters(
      Method requestHandler, HttpServletRequest request, HttpServletResponse response) {
    // 메서드를 호출할 때 넘겨 줄 값을 담을 바구니 준비
    ArrayList<Object> paramValues = new ArrayList<>();
    
    // 메서드가 어떤 값을 요구하는지 파라미터 정보를 알아낸다.
    Parameter[] params = requestHandler.getParameters();
    
    // 그 파라미터에 해당하는 값을 찾아 바구니에 저장한다.
    Class<?> paramType = null;
    Object paramValue = null;
    for (Parameter param : params) {
      paramType = param.getType();
      
      try {
        // 먼저 Spring IoC 컨테이너에서 파라미터 값을 찾는다.
        paramValue = applicationContext.getBean(paramType);
        paramValues.add(paramValue); // 있다면 바구니에 저장한다.
      } catch (Exception e) {
        if (paramType.isInstance(request)) { // 혹시 그 파라미터가 HttpServletReuqest 이냐?
          paramValues.add(request);
        } else if (paramType.isInstance(response)) { // 아니면 HttpServletResponse 이냐?
          paramValues.add(response);
        } else if (paramType.isInstance(request.getSession())) {
          paramValues.add(request.getSession());
        } else { // 그것도 아니면, 그런 파라미터 값은 없는데.... 못주겠네.. 그럼 null...
          paramValues.add(null);
        }
      }
    }
    
    if (paramValues.size() > 0) {
      return paramValues.toArray();
    } else {
      return null;
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











