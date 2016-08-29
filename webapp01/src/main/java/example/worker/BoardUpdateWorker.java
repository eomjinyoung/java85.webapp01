package example.worker;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/update.do")
public class BoardUpdateWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(ServletRequest request, ServletResponse response) throws Exception {
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










