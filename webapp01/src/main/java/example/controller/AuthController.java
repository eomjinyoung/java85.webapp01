package example.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import example.dao.MemberDao;
import example.vo.Member;

//@Component
@Controller // 스프링에서 페이지 컨트롤러에게 붙이라고 만들어준 애노테이션이다.
@RequestMapping("/auth/") // 이 페이지 컨트롤러의 기본 URL을 지정한다.
public class AuthController {
  
  @Autowired MemberDao memberDao;
  
  @RequestMapping("form")
  public String form(HttpServletRequest request) throws Exception {
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
    
    return "/auth/LoginForm.jsp";
  }
  
  @RequestMapping("login")
  public String login(
      HttpServletResponse response,
      HttpSession session,
      @RequestParam(name="email") String email,
      @RequestParam(name="password") String password,
      @RequestParam(name="saveEmail", defaultValue="off") String saveEmail) throws Exception {
    
    Cookie cookie = new Cookie("email", email);
    if (saveEmail.equals("off")) {
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
    
    if (member == null) {
      // 로그인 실패한다면, 기존 세션도 무효화시킨다.
      session.invalidate();
      return "/auth/LoginFail.jsp";
      
    } else {
      // 로그인 성공 했다면, 다른 서블릿이 로그인한 사용자 정보를 사용할 수 있도록 세션에 보관한다.
      session.setAttribute("member", member);
      return "redirect:../board/list.do";
    }
  }
  
  @RequestMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:form.do";
  }
}










