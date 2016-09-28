package example.controller.json;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;

import example.dao.MemberDao;
import example.vo.Member;

@Controller 
@RequestMapping("/auth/") 
@SessionAttributes({"member"}) // Model 객체에 저장된 정보 중에서 로그인 회원 정보는 따로 세션으로 관리한다.
public class AuthController {
  
  @Autowired MemberDao memberDao;
  
  @RequestMapping(path="login", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String login(
      HttpServletResponse response,
      String email,
      String password,
      String saveEmail, 
      Model model,
      SessionStatus sessionStatus) throws Exception {
    
    HashMap<String,Object> result = new HashMap<>();
    try {
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
        result.put("state", "fail");
        
      } else {
        model.addAttribute("member", member); // Model 객체에 로그인 회원 정보를 담는다.
        result.put("state", "success");
      }
      
    } catch (Exception e) {
      result.put("state", "error");
      result.put("data", e.getMessage());
    }
    return new Gson().toJson(result);  
  }
  
  @RequestMapping(path="logout", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String logout(HttpSession session, SessionStatus sessionStatus) throws Exception {
    HashMap<String,Object> result = new HashMap<>();
    try {
      sessionStatus.setComplete();
      session.invalidate();
      result.put("state", "success");
    } catch (Exception e) {
      result.put("state", "error");
      result.put("data", e.getMessage());
    }
    return new Gson().toJson(result);   
  }
  
  @RequestMapping(path="loginUser", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public String loginUser(HttpSession session) throws Exception {
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      Member member = (Member)session.getAttribute("member");
      if (member == null) {
        throw new Exception("로그인이 되지 않았습니다.");
      }
      result.put("state", "success");
      result.put("data", member);
    } catch (Exception e) {
      result.put("state", "error");
      result.put("data", e.getMessage());
    }
    return new Gson().toJson(result);  
  }
  
}










