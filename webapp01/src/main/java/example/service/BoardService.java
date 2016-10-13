package example.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import example.vo.Board;

public interface BoardService {
  List<Board> getBoardList(int pageNo, int length) throws Exception;
  void insertBoard(Board board, MultipartFile file1, MultipartFile file2, String uploadDir) throws Exception;
  Board getBoard(int no) throws Exception;
  void updateBoard(Board board) throws Exception;
  void deleteBoard(int no) throws Exception;
}







