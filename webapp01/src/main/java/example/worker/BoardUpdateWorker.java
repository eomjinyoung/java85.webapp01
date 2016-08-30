package example.worker;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/update.do")
public class BoardUpdateWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    board.setPassword(request.getParameter("password"));
    
    String errorMsg = null;
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", board.getNo());
      paramMap.put("password", board.getPassword());
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        errorMsg = "해당 게시물이 없거나 암호가 일치하지 않습니다!";
        throw new Exception();
      }

      boardDao.update(board);
      response.sendRedirect("list.do");
      
    } catch (Exception e) {
      e.printStackTrace();
    
      response.setHeader("Refresh", "3;url=list.do");
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<html>");
      out.println("<head>");
      out.println("<title>게시물 변경하기</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>게시물 변경 결과</h1>");
      
      if (errorMsg != null) {
        out.println(errorMsg);
      } else {
        out.println("데이터 처리 오류입니다!");
      }
      
      out.println("</body>");
      out.println("</html>");
    }
  }

}










