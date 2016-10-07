package example.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import example.dao.BoardDao;
import example.dao.BoardFileDao;
import example.util.FileUploadUtil;
import example.vo.Board;
import example.vo.BoardFile;

@Controller 
@RequestMapping("/board/")
public class BoardController {
  @Autowired ServletContext sc;
  @Autowired BoardDao boardDao;
  @Autowired BoardFileDao boardFileDao;
  
  @RequestMapping("list")
  public String list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int length,
      Model model) throws Exception {

    HashMap<String,Object> map = new HashMap<>();
    map.put("startIndex", (pageNo - 1) * length);
    map.put("length", length);
  
    List<Board> list = boardDao.selectList(map);
    model.addAttribute("list", list);
    
    return "board/BoardList";
  }
  
  @RequestMapping("add")
  public String add(
      Board board,
      MultipartFile file1,
      MultipartFile file2) throws Exception {
    
    boardDao.insert(board);
    
    String newFilename = null;
    if (!file1.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file1.getOriginalFilename());
      try {
        file1.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
        BoardFile boardFile = new BoardFile();
        boardFile.setFilename(newFilename);
        boardFile.setBoardNo(board.getNo());
        boardFileDao.insert(boardFile);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    if (!file2.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file2.getOriginalFilename());
      try {
        file2.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
        BoardFile boardFile = new BoardFile();
        boardFile.setFilename(newFilename);
        boardFile.setBoardNo(board.getNo());
        boardFileDao.insert(boardFile);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    
    return "redirect:list.do";
  }
  
  @RequestMapping("detail")
  public String detail(int no, Model model) throws Exception {
    Board board = boardDao.selectOne(no);
    model.addAttribute("board", board);
    return "board/BoardDetail";
  }
  
  @RequestMapping("update")
  public String update(Board board) throws Exception {
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
  public String delete(int no) throws Exception {
    boardDao.delete(no);
    return "redirect:list.do";
  }
}







