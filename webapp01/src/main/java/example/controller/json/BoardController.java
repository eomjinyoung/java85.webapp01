package example.controller.json;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import example.dao.BoardDao;
import example.vo.Board;

@Controller 
@RequestMapping("/board/")
public class BoardController {
  
  @Autowired BoardDao boardDao;
  
  @RequestMapping(path="list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int length) throws Exception {
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      HashMap<String,Object> map = new HashMap<>();
      map.put("startIndex", (pageNo - 1) * length);
      map.put("length", length);
      
      List<Board> list = boardDao.selectList(map);
      result.put("state", "success");
      result.put("data", list);
      
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    return result;
  }
  
  @RequestMapping(path="add")
  public Object add(Board board) throws Exception {
    // 성공하든 실패하든 클라이언트에게 데이터를 보내야 한다.
    HashMap<String,Object> result = new HashMap<>();
    
    try {
      boardDao.insert(board);
      result.put("state", "success");
      
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    
    return result;
  }
  
  @RequestMapping(path="detail")
  public Object detail(int no) throws Exception {
    HashMap<String,Object> result = new HashMap<>();
    
    try {
      Board board = boardDao.selectOne(no);
      
      if (board == null) 
        throw new Exception("해당 번호의 게시물이 존재하지 않습니다.");
      
      result.put("state", "success");
      result.put("data", board);
      
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    
    return result;
  }
  
  @RequestMapping(path="update")
  public Object update(Board board) throws Exception {
    HashMap<String,Object> result = new HashMap<>();
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", board.getNo());
      paramMap.put("password", board.getPassword());
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
      }
      boardDao.update(board);
      result.put("state", "success");
      
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    
    return result;
  }
  
  @RequestMapping(path="delete")
  public Object delete(int no, String password) throws Exception {
    HashMap<String,Object> result = new HashMap<>();
    try {
      HashMap<String,Object> paramMap = new HashMap<>();
      paramMap.put("no", no);
      paramMap.put("password", password);
      
      if (boardDao.selectOneByPassword(paramMap) == null) {
        throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
      }
      boardDao.delete(no);
      result.put("state", "success");
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    return result;
  }
}







