package example.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextHelper {
  static ApplicationContext applicationContext;
  
  public static ApplicationContext getApplicationContext() {
    if (applicationContext == null) {
      applicationContext = new ClassPathXmlApplicationContext(
          "conf/application-context.xml");
    }
    return applicationContext;
  }
}
