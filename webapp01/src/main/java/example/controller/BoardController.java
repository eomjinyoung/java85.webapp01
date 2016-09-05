package example.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import example.dao.BoardDao;
import example.vo.Board;

//@Component
@Controller // 페이지 컨트롤러에 붙이는 애노테이션 
@RequestMapping("/board/") // 이 페이지 컨트롤러의 기본 URL을 지정한다.
public class BoardController {
  @Autowired BoardDao boardDao;
  
  @RequestMapping("list")
  public String list(
      HttpServletRequest request,
      @RequestParam(name="pageNo", defaultValue="1") int pageNo,
      @RequestParam(name="length", defaultValue="5") int length) throws Exception {

    HashMap<String,Object> map = new HashMap<>();
    map.put("startIndex", (pageNo - 1) * length);
    map.put("length", length);
  
    List<Board> list = boardDao.selectList(map);
    request.setAttribute("list", list);
    
    return "/board/BoardList.jsp";
  }
  
  @RequestMapping("add")
  public String add(
      @RequestParam(name="password") String password,
      @RequestParam(name="title") String title,
      @RequestParam(name="contents") String contents) throws Exception {
    Board board = new Board();
    board.setPassword(password);
    board.setTitle(title);
    board.setContents(contents);
    
    boardDao.insert(board);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("detail")
  public String detail(
      HttpServletRequest request,
      @RequestParam(name="no") int no) throws Exception {
    Board board = boardDao.selectOne(no);
    request.setAttribute("board", board);
    
    return "/board/BoardDetail.jsp";
  }
  
  @RequestMapping("update")
  public String update(
      @RequestParam(name="no") int no,
      @RequestParam(name="password") String password,
      @RequestParam(name="title") String title,
      @RequestParam(name="contents") String contents) throws Exception {
    
    Board board = new Board();
    board.setNo(no);
    board.setTitle(title);
    board.setContents(contents);
    board.setPassword(password);
    
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
  public String delete(
      HttpServletRequest request,
      @RequestParam(name="no") int no) throws Exception {
    
    boardDao.delete(no);
    return "redirect:list.do";
  }
}







