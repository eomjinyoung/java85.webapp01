package example.worker;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("  <title>게시물 상세조회</title>");
    out.println("</head>");
    out.println("<body>");
    
    // 페이지 상단에 정보 출력하기 위해 include 기법 사용.
    RequestDispatcher rd = request.getRequestDispatcher("/header");
    rd.include(request, response); // 실행하고 돌아와야 하기 때문에 include 여야 한다.
    
    out.println("<h1>게시물 상세조회</h1>");
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
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
      //ServletRequest 보관소에 오류 정보 저장
      request.setAttribute("error", e);
      
      rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
    }
    
    // 페이지 하단에 정보 출력하기 위해 include 기법 사용.
    rd = request.getRequestDispatcher("/footer");
    rd.include(request, response); // 실행하고 돌아와야 하기 때문에 include 여야 한다.
    
    out.println("</body>");
    out.println("</html>");
  }

}










