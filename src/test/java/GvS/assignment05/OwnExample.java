package GvS.assignment05;

import assignment05.consistency.CausalMap;
import assignment05.time.Vector;

import java.util.Timer;
import java.util.TimerTask;

public class OwnExample implements Runnable {
  private CausalMap causalMap;
  private Vector vector;

  private OwnExample(Vector vectorClock) {
    vector = vectorClock;
  }

  @Override
  public void run() {
    while (true) {
      System.out.println("test");
    }
  }

  public static volatile boolean isCancelled = false;

  public static void main(String[] args) {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        while (!isCancelled){
          System.out.println("test");
        }
      }
    };
    timer.schedule(task, 0l, 1000l);

    for (int i = 0; i < 10000; i++) {
      System.out.println(i);
    }
    isCancelled = true;
  }
}
