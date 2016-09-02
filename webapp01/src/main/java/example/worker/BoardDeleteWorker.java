package example.worker;

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
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    boardDao.delete(no);
    
    return "redirect:list.do";
  }

}










