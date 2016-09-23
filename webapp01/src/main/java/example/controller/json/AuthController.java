package example.controller.json;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import example.dao.MemberDao;
import example.vo.Member;

@Controller 
@RequestMapping("/auth/") 
@SessionAttributes({"member"}) // Model 객체에 저장된 정보 중에서 로그인 회원 정보는 따로 세션으로 관리한다.
public class AuthController {
  
  @Autowired MemberDao memberDao;
  
  @RequestMapping("form")
  public String form(
      @CookieValue(name="email", defaultValue="") String email,
      Model model) throws Exception {
    model.addAttribute("email", email);
    model.addAttribute("checked", ((email.equals("")) ? "" : "checked"));
    return "/auth/LoginForm.jsp";
  }
  
  @RequestMapping("login")
  public String login(
      HttpServletResponse response,
      String email,
      String password,
      String saveEmail, 
      Model model,
      SessionStatus sessionStatus) throws Exception {
    
    Cookie cookie = new Cookie("email", email);
    if (saveEmail == null) {
      cookie.setMaxAge(0); 
    } else {
      cookie.setMaxAge(60 * 60 * 24 * 7);
    }
    response.addCookie(cookie);
    
    
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);
    Member member = memberDao.selectOneByEmailAndPassword(paramMap);
    
    if (member == null) {
      sessionStatus.setComplete(); // 스프링이 관리하는 세션 값을 무효화시킨다.
      return "/auth/LoginFail.jsp";
      
    } else {
      model.addAttribute("member", member); // Model 객체에 로그인 회원 정보를 담는다.
      return "redirect:../board/list.do";
    }
  }
  
  @RequestMapping("logout")
  public String logout(SessionStatus sessionStatus) throws Exception {
    sessionStatus.setComplete();
    return "redirect:form.do";
  }
}










