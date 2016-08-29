package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import example.dao.BoardDao;
import example.vo.Board;

@WebServlet("/board/list.do")
public class BoardListServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  BoardDao boardDao;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); 
    
    // 스프링에서 제공하는 ContextLoaderListener를 사용한다면,
    // 다음의 방식으로 스프링 IoC 컨테이너를 꺼내야 한다.
    
    //1) 모든 서블릿이 공유하는 ServletContext 창고를 알아낸다.
    ServletContext servletContext = config.getServletContext();
    
    //2) ServletContext 창고와 WebApplicationContextUtils 도우미 객체를 사용하여 
    //   스프링 IoC 컨테이너를 꺼낸다.
    ApplicationContext applicationContext = 
        WebApplicationContextUtils.getWebApplicationContext(servletContext);
    
    //3) Spring IoC 컨테이너에서 BoardDao 구현체를 꺼낸다.
    boardDao = applicationContext.getBean(BoardDao.class);
  }
  
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    int pageNo = 1;
    int length = 5;
    
    if (request.getParameter("pageNo") != null) {
      pageNo = Integer.parseInt(request.getParameter("pageNo"));
    }
    
    if (request.getParameter("length") != null) {
      length = Integer.parseInt(request.getParameter("length"));
    }
    
    HashMap<String,Object> map = new HashMap<>();
    map.put("startIndex", (pageNo - 1) * length);
    map.put("length", length);
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("  <title>게시물 목록조회</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 목록조회</h1>");
    out.println("<p><a href='form.html'>새 글</a></p>");
    
    try {
      List<Board> list = boardDao.selectList(map);
      for (Board b : list) {
        out.printf("%d, <a href='detail.do?no=%1$d'>%s</a>, %s, %s, %d<br>\n", 
            b.getNo(), b.getTitle(), b.getWriter(), b.getCreatedDate(), b.getViewCount());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










