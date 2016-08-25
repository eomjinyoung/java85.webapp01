package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import example.dao.BoardDao;
import example.vo.Board;

@WebServlet("/board/detail.do")
public class BoardDetailServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  ApplicationContext iocContainer ;
  BoardDao boardDao;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); 
    iocContainer = new ClassPathXmlApplicationContext(
        "conf/application-context.xml");
    boardDao = iocContainer.getBean(BoardDao.class);
  }
  
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    int no = Integer.parseInt(request.getParameter("no"));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("  <title>게시물 상세조회</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 상세조회</h1>");
    
    try {
      Board board = boardDao.selectOne(no);
      if (board == null) { 
        out.println("해당 번호의 게시물이 없습니다.");
      } else {
        out.printf("번호: %d<br>", board.getNo());
        out.printf("제목: %s<br>", board.getTitle());
        out.printf("내용: %s<br>", board.getContents());
        out.printf("작성자: %s<br>", board.getWriter());
        out.printf("등록일: %s<br>", board.getCreatedDate());
        out.printf("조회수: %s<br>", board.getViewCount());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










