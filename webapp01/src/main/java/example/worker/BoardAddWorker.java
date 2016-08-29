package example.worker;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/add.do")
public class BoardAddWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(ServletRequest request, ServletResponse response) throws Exception {
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










