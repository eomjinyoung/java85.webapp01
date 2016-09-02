package example.worker;

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
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardDao.selectOne(no);
    request.setAttribute("board", board);
    
    return "/board/BoardDetail.jsp";
  }

}










