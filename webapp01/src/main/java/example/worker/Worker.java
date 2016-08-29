package example.worker;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface Worker {
  void execute(ServletRequest request, ServletResponse response) throws Exception;
}
