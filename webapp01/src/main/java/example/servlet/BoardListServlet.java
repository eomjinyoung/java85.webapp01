package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import example.dao.BoardDao;
import example.util.ApplicationContextHelper;
import example.vo.Board;

@WebServlet("/board/list.do")
public class BoardListServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  BoardDao boardDao;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); 
    boardDao = ApplicationContextHelper.getApplicationContext().getBean(BoardDao.class);
  }
  
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
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
    out.println("<h1>게시물 목록조회</h1>");
    out.println("<p><a href='form.html'>새 글</a></p>");
    
    try {
      List<Board> list = boardDao.selectList(map);
      for (Board b : list) {
        out.printf("%d, <a href='detail.do?no=%1$d'>%s</a>, %s, %s, %d<br>\n", 
            b.getNo(), b.getTitle(), b.getWriter(), b.getCreatedDate(), b.getViewCount());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회 오류입니다!");
      e.printStackTrace();
    }
    
    out.println("</body>");
    out.println("</html>");
  }

}










