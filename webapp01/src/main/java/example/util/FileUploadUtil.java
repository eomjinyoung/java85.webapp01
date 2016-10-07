package example.util;

public class FileUploadUtil {
  static int count = 0;
  public static String getNewFilename(String originFilename) {
    if (count > 100) {
      count = 0;
    }
    return System.currentTimeMillis() + "_" + (++count) + extractFileExt(originFilename);
  }
  
  public static String extractFileExt(String filename) {
    return filename.substring(filename.lastIndexOf("."));
  }
}
