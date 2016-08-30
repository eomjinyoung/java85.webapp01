package example.worker;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/add.do")
public class BoardAddWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board board = new Board();
    board.setPassword(request.getParameter("password"));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    
    try {
      boardDao.insert(board);
      response.sendRedirect("list.do");
      
    } catch (Exception e) {
      e.printStackTrace();
    
      response.setHeader("Refresh", "3;url=list.do");
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<html>");
      out.println("<head>");
      out.println("<title>게시물 등록하기</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>게시물 등록 결과</h1>");
      out.println("데이터 처리 오류입니다!");
      out.println("</body>");
      out.println("</html>");
    }
  }

}










