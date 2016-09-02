package example.worker;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;

@Component("/board/delete.do")
public class BoardDeleteWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      boardDao.delete(no);
      response.sendRedirect("list.do");
      
    } catch (Exception e) {
      //ServletRequest 보관소에 오류 정보 저장
      request.setAttribute("error", e);
      
      response.setHeader("Refresh", "3;url=list.do");
      
      RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
      rd.forward(request, response);
    }
  }

}










