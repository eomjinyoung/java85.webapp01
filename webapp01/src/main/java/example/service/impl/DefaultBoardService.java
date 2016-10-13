package example.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import example.dao.BoardDao;
import example.dao.BoardFileDao;
import example.service.BoardService;
import example.util.FileUploadUtil;
import example.vo.Board;
import example.vo.BoardFile;

@Service 
public class DefaultBoardService implements BoardService {
  @Autowired BoardDao boardDao;
  @Autowired BoardFileDao boardFileDao;
  
  public List<Board> getBoardList(int pageNo, int length) throws Exception {
    HashMap<String,Object> map = new HashMap<>();
    map.put("startIndex", (pageNo - 1) * length);
    map.put("length", length);
    return boardDao.selectList(map);
  }
  
  public void insertBoard(Board board, 
      MultipartFile file1,
      MultipartFile file2,
      String uploadDir) throws Exception {
    
    boardDao.insert(board);
    
    String newFilename = null;
    if (!file1.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file1.getOriginalFilename());
      try {
        file1.transferTo(new File(uploadDir + newFilename));
        BoardFile boardFile = new BoardFile();
        boardFile.setFilename(newFilename);
        //boardFile.setBoardNo(board.getNo());
        boardFile.setBoardNo(10200);
        boardFileDao.insert(boardFile);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    if (!file2.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file2.getOriginalFilename());
      try {
        file2.transferTo(new File(uploadDir + newFilename));
        BoardFile boardFile = new BoardFile();
        boardFile.setFilename(newFilename);
        boardFile.setBoardNo(board.getNo());
        boardFileDao.insert(boardFile);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public Board getBoard(int no) throws Exception {
    return boardDao.selectOne(no);
  }
  
  public void updateBoard(Board board) throws Exception {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("no", board.getNo());
    paramMap.put("password", board.getPassword());
    
    if (boardDao.selectOneByPassword(paramMap) == null) {
      throw new Exception("해당 게시물이 없거나 암호가 일치하지 않습니다!");
    }
    boardDao.update(board);
  }
  
  public void deleteBoard(int no) throws Exception {
    boardDao.delete(no);
  }
}







