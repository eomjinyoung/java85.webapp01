package example.worker;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/detail.do")
public class BoardDetailWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        out.printf("<form action='update.do' method='post'>\n");
        out.printf("<input type='hidden' name='no' value='%d'>\n", board.getNo());
        out.printf("번호: %d<br>", board.getNo());
        out.printf("제목: <input type='text' name='title' value='%s'><br>\n", 
                   board.getTitle());
        out.printf("내용: <textarea cols='60' rows='10' name='contents'>%s</textarea><br>\n", 
                   board.getContents());
        out.printf("암호: <input type='password' name='password'><br>\n");
        out.printf("작성자: %s<br>\n", board.getWriter());
        out.printf("등록일: %s<br>\n", board.getCreatedDate());
        out.printf("조회수: %s<br>\n", board.getViewCount());
        out.printf("<button>변경</button>\n");
        out.printf("<p><a href='delete.do?no=%d'>삭제</a></p>\n", board.getNo());
        out.printf("</form>\n");
      }
      
    } catch (Exception e) {
      out.println("데이터 처리 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










