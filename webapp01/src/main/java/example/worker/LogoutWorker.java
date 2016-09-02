package example.worker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component("/auth/logout.do")
public class LogoutWorker implements Worker {
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate();
    
    response.sendRedirect("login.do");
  }
}











