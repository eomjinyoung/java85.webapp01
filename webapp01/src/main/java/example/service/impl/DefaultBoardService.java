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
    if (file1 != null && !file1.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file1.getOriginalFilename());
      file1.transferTo(new File(uploadDir + newFilename));
      BoardFile boardFile = new BoardFile();
      boardFile.setFilename(newFilename);
      boardFile.setBoardNo(board.getNo());
      //boardFile.setBoardNo(10200); //트랜잭션 테스트 용 
      boardFileDao.insert(boardFile);
    }
    
    if (file2 != null && !file2.isEmpty()) {
      newFilename = FileUploadUtil.getNewFilename(file2.getOriginalFilename());
      file2.transferTo(new File(uploadDir + newFilename));
      BoardFile boardFile = new BoardFile();
      boardFile.setFilename(newFilename);
      boardFile.setBoardNo(board.getNo());
      boardFileDao.insert(boardFile);
    }
  }
  
  public Board getBoard(int no) throws Exception {
    return boardDao.selectOne(no);
  }
  
  public Board getBoard(int no, String password) throws Exception {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("no", no);
    paramMap.put("password", password);
    return boardDao.selectOneByPassword(paramMap);
  }
  
  @Override
  public int getTotalPage(int pageSize) throws Exception {
    int countAll = boardDao.countAll();
    int totalPage = countAll / pageSize;
    if ((countAll % pageSize) > 0) {
      totalPage++;
    }
    return totalPage;
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







