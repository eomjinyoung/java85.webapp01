package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import example.vo.Member;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<div style='background-color:navy;font-size:150%;color:white;position:relative;'>자바85기");
    
    HttpSession session = request.getSession();
    Member member = (Member)session.getAttribute("member");
    if (member != null) {
      out.printf("<div style='position:absolute;right:0px;top:0px;font-size:15px;'>%s "
          + "<a href='%s/auth/logout' style='color:yellow;'>로그아웃</a></div>", 
          member.getName(), request.getContextPath());
    } else {
      out.printf("<div style='position:absolute;right:0px;top:0px;font-size:15px;'>"
          + "<a href='%s/auth/login' style='color:yellow;'>로그인</a></div>", 
          request.getContextPath());
    }
    
    out.println("</div>");
  }
}











