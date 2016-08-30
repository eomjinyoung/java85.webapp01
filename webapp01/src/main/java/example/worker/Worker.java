package example.worker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Worker {
  void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
