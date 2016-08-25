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

@WebServlet("/board/add.do")
public class BoardAddServlet extends GenericServlet {
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
    // 클라이언트가 보내는 데이터는 UTF-8로 되어 있으니,
    // Unicode로 잘 변환하여 값을 리턴하라!
    request.setCharacterEncoding("UTF-8");
    
    Board board = new Board();
    board.setPassword(request.getParameter("password"));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("  <title>게시물 등록하기</title>");
    
    // HTML 페이지에 Refresh 폭탄 심기!
    // => HTML 페이지를 완전히 출력한 후 지정된 시간이 경과하면 특정 URL을 자동으로 요청하게 만든다.
    out.println("<meta http-equiv='Refresh' content='1;url=list.do'>");
    
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 등록 결과</h1>");
    
    try {
      boardDao.insert(board);
      out.println("등록 성공입니다!");
      
    } catch (Exception e) {
      out.println("데이터 처리 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










