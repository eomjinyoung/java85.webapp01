package example.controller.json;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import example.dao.BoardDao;
import example.vo.Board;

@Controller 
@RequestMapping("/board/")
public class BoardController {
  
  @Autowired BoardDao boardDao;
  
  @RequestMapping(path="list", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String list(
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
    return new Gson().toJson(result);
  }
  
  @RequestMapping("list2")
  public ResponseEntity<String> list2(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int length) throws Exception {

    HashMap<String,Object> map = new HashMap<>();
    map.put("startIndex", (pageNo - 1) * length);
    map.put("length", length);
  
    List<Board> list = boardDao.selectList(map);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain;charset=UTF-8");
    
    return new ResponseEntity<>(
        "클라이언트에게 보낼 내용", 
        headers,
        HttpStatus.OK);
  }
  
  @RequestMapping(path="add", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String add(Board board) throws Exception {
    // 성공하든 실패하든 클라이언트에게 데이터를 보내야 한다.
    HashMap<String,Object> result = new HashMap<>();
    
    try {
      boardDao.insert(board);
      result.put("state", "success");
      
    } catch (Exception e) {
      result.put("state", "fail");
      result.put("data", e.getMessage());
    }
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="detail", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String detail(int no) throws Exception {
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
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="update", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String update(Board board) throws Exception {
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
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="delete", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String delete(int no, String password) throws Exception {
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
    return new Gson().toJson(result);
  }
}







