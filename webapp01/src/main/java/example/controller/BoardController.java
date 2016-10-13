package example.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import example.service.BoardService;
import example.vo.Board;

@Controller 
@RequestMapping("/board/")
public class BoardController {
  @Autowired ServletContext sc;
  @Autowired BoardService boardService;
  
  @RequestMapping("list")
  public String list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int length,
      Model model) throws Exception {
    List<Board> list = boardService.getBoardList(pageNo, length);
    model.addAttribute("list", list);
    return "board/BoardList";
  }
  
  @RequestMapping("add")
  public String add(
      Board board,
      MultipartFile file1,
      MultipartFile file2) throws Exception {
    String uploadDir = sc.getRealPath("/upload") + "/";
    boardService.insertBoard(board, file1, file2, uploadDir);
    return "redirect:list.do";
  }
  
  @RequestMapping("detail")
  public String detail(int no, Model model) throws Exception {
    Board board = boardService.getBoard(no);
    model.addAttribute("board", board);
    return "board/BoardDetail";
  }
  
  @RequestMapping("update")
  public String update(Board board) throws Exception {
    boardService.updateBoard(board);
    return "redirect:list.do";
  }
  
  @RequestMapping("delete")
  public String delete(int no) throws Exception {
    boardService.deleteBoard(no);
    return "redirect:list.do";
  }
}







