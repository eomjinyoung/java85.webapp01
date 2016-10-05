package example.controller.json;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import example.dao.BoardDao;
import example.vo.Board;
import example.vo.JsonResult;

@Controller 
@RequestMapping("/board/")
public class BoardController {
  
  @Autowired BoardDao boardDao;
  
  @RequestMapping(path="list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int length) throws Exception {
    
    try {
      HashMap<String,Object> map = new HashMap<>();
      map.put("startIndex", (pageNo - 1) * length);
      map.put("length", length);
      
      return JsonResult.success(boardDao.selectList(map));
      
    } catch (Exception e) {
      return JsonResult.fail(e.getMessage());
    }
  }
  
  @RequestMapping(path="add")
  public Object add(Board board) throws Exception {
    try {
      boardDao.insert(board);
      return JsonResult.success();
      
    } catch (Exception e) {
      return JsonResult.fail(e.getMessage());
    }
  }
  
  @RequestMapping(path="detail")
  public Object detail(int no) throws Exception {
    try {
      Board board = boardDao.selectOne(no);
      
      if (board == null) 
        throw new Exception("해당 번호의 게시물이 존재하지 않습니다.");
      
      return JsonResult.success(board);
      
    } catch (Exception e) {
      return JsonResult.fail(e.getMessage());
    }
  }
  
  @RequestMapping(path="update")
  public Object update(Board board) throws Exception {
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", board.getNo());
      paramMap.put("password", board.getPassword());
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
      }
      boardDao.update(board);
      return JsonResult.success();
      
    } catch (Exception e) {
      return JsonResult.fail(e.getMessage());
    }
  }
  
  @RequestMapping(path="delete")
  public Object delete(int no, String password) throws Exception {
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", no);
      paramMap.put("password", password);
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
      }
      boardDao.delete(no);
      return JsonResult.success();
      
    } catch (Exception e) {
      return JsonResult.fail(e.getMessage());
    }
  }
}







