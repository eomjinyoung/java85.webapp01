package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

@WebServlet("/board/update.do")
public class BoardUpdateServlet extends GenericServlet {
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
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    board.setPassword(request.getParameter("password"));
    
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("  <title>게시물 변경하기</title>");
    out.println("  <meta http-equiv='Refresh' content='1;url=list.do'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 변경 결과</h1>");
    
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", board.getNo());
      paramMap.put("password", board.getPassword());
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        out.println("해당 게시물이 없거나 암호가 일치하지 않습니다!");
      } else {
        boardDao.update(board);
        out.println("변경 성공입니다!");
      }
    } catch (Exception e) {
      out.println("데이터 처리 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










