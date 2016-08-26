package example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
  FilterConfig filterConfig;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 문자집합에 대한 정보는 web.xml에서 필터의 파라미터 값을 가져오자!
    request.setCharacterEncoding(filterConfig.getInitParameter("encoding"));
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }

}
