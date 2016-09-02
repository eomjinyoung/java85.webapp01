package example.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import example.dao.BoardDao;
import example.vo.Board;

//@Component
@Controller // 페이지 컨트롤러에 붙이는 애노테이션 
@RequestMapping("/board/") // 이 페이지 컨트롤러의 기본 URL을 지정한다.
public class BoardController {
  @Autowired BoardDao boardDao;
  
  @RequestMapping("list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
  
    List<Board> list = boardDao.selectList(map);
    request.setAttribute("list", list);
    
    return "/board/BoardList.jsp";
  }
  
  @RequestMapping("add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board board = new Board();
    board.setPassword(request.getParameter("password"));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    
    boardDao.insert(board);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardDao.selectOne(no);
    request.setAttribute("board", board);
    
    return "/board/BoardDetail.jsp";
  }
  
  @RequestMapping("update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    board.setPassword(request.getParameter("password"));
    
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("no", board.getNo());
    paramMap.put("password", board.getPassword());
    
    if (boardDao.selectOneByPassword(paramMap) == null) {
      throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
    }

    boardDao.update(board);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    boardDao.delete(no);
    
    return "redirect:list.do";
  }
}







