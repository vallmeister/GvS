package assignment04;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

  public static String time(){
    return format.format(new Date());
  }

  public static void log(String s) { System.out.println(time()+": "+s); }
}
