package example.vo;

public class JsonResult {
  public static final String SUCCESS = "success";
  public static final String FAIL = "fail";
  public static final String ERROR = "error";
  
  String state;
  Object data;
  
  public static JsonResult success() {
    return new JsonResult(SUCCESS);
  }
  
  public static JsonResult success(Object data) {
    return new JsonResult(SUCCESS, data);
  }
  
  public static JsonResult fail() {
    return new JsonResult(FAIL);
  }
  
  public static JsonResult fail(Object data) {
    return new JsonResult(FAIL, data);
  }
  
  public static JsonResult error() {
    return new JsonResult(ERROR);
  }
  
  public static JsonResult error(Object data) {
    return new JsonResult(ERROR, data);
  }
  
  public JsonResult(String state) {
    this(state, null);
  }

  public JsonResult(String state, Object data) {
    this.state = state;
    this.data = data;
  }
  
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public Object getData() {
    return data;
  }
  public void setData(Object data) {
    this.data = data;
  }
  
 
}
