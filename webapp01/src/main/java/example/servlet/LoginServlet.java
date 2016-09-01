package example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import example.dao.MemberDao;
import example.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  MemberDao memberDao;
  
  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) 호출 ---> init() 호출
    // => 따라서 이 메서드를 오버라이딩하는 것은 init(ServletConfig)를 재정의하는 것과 같은 효과를 가진다.
    ServletContext sc = this.getServletContext();
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
    memberDao = applicationContext.getBean(MemberDao.class);
  
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>로그인</h1>");
    out.println("<form action='login' method='post'>");
    out.println("이메일: <input type='text' name='email' size='40'><br>");
    out.println("암호: <input type='password' name='password'><br>");
    out.println("<button>로그인</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    
    System.out.println(email);
    System.out.println(password);
    
    // DB에서 해당 이메일과 암호가 일치하는 사용자가 있는지 조사한다.
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);
    
    Member member = memberDao.selectOneByEmailAndPassword(paramMap);
    
    if (member == null) {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>로그인 결과</title></head>");
      out.println("<body><p>이메일 또는 암호가 일치하지 않거나 존재하지 않는 사용자입니다.</p>");
      out.println("</body></html>");
    } else {
      // 있다면, 메인화면으로 리다이렉트한다.
      response.sendRedirect("../board/list.do");
    }
  }
}


















