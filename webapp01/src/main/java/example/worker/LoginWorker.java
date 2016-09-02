package example.worker;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.MemberDao;
import example.vo.Member;

@Component("/auth/login.do")
public class LoginWorker implements Worker {

  @Autowired
  MemberDao memberDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("POST")) {
      doPost(request, response);
    } else {
      doGet(request, response);
    }
  }
  
  private void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Cookie[] cookies = request.getCookies();
    String email = "";
    String checked = "";
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          email = cookie.getValue();
          checked = "checked";
          break;
        }
      }
    }
    
    request.setAttribute("email", email);
    request.setAttribute("checked", checked);
    
    RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginForm.jsp");
    rd.forward(request, response);
  }
  
  private void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String saveEmail = request.getParameter("saveEmail");
    
    Cookie cookie = new Cookie("email", email);
    if (saveEmail == null) {
      cookie.setMaxAge(0); // 유효기간이 0이면 웹브라우저는 "email" 이름으로 저장된 쿠키를 삭제한다.
    } else {
      cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키를 1주일 저장한다.
    }
    response.addCookie(cookie);
    
    
    // DB에서 해당 이메일과 암호가 일치하는 사용자가 있는지 조사한다.
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);
    
    Member member = memberDao.selectOneByEmailAndPassword(paramMap);
    
    HttpSession session = request.getSession();
    
    if (member == null) {
      // 로그인 실패한다면, 기존 세션도 무효화시킨다.
      session.invalidate();
      
      response.setHeader("Refresh", "2;url=login");
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>로그인 결과</title></head>");
      out.println("<body><p>이메일 또는 암호가 일치하지 않거나 존재하지 않는 사용자입니다.</p>");
      out.println("</body></html>");
      
    } else {
      // 로그인 성공 했다면, 다른 서블릿이 로그인한 사용자 정보를 사용할 수 있도록 세션에 보관한다.
      session.setAttribute("member", member);
      
      // 메인화면으로 리다이렉트한다.
      response.sendRedirect("../board/list.do");
    }
  }
}


















