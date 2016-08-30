package example.worker;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/list.do")
public class BoardListWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    
    // 페이지 상단에 정보 출력하기 위해 include 기법 사용.
    RequestDispatcher rd = request.getRequestDispatcher("/header");
    rd.include(request, response); // 실행하고 돌아와야 하기 때문에 include 여야 한다.
    
    out.println("<h1>게시물 목록조회</h1>");
    out.println("<p><a href='form.html'>새 글</a></p>");
    
    try {
      List<Board> list = boardDao.selectList(map);
      for (Board b : list) {
        out.printf("%d, <a href='detail.do?no=%1$d'>%s</a>, %s, %s, %d<br>\n", 
            b.getNo(), b.getTitle(), b.getWriter(), b.getCreatedDate(), b.getViewCount());
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










