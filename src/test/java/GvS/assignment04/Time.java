package GvS.assignment04;


import assignment04.Clock;
import assignment04.SynchronizedClock;
import assignment04.Util;
import org.zeromq.ZMQ;
import java.util.Random;

/**
 * @author David Mödinger
 *
 */
public class Time {

  /**
   * Demonstrate Clock and SynchronizedClock.
   */
  public static void main(String[] args) {
    var context = ZMQ.context(1);

    var host = "gvs.lxd-vs.uni-ulm.de:3322";

    // Clock that synchronizes at the start
    Clock initil = new SynchronizedClock(context, host, 5);

    //Clock with initial time
    Clock wrong = new SynchronizedClock(context, host, 5, initil.getTime()+2000);

    Util.log(
        String.format("%10s\t%10s\t%10s","Wrong","Correct","Difference")
    );

    while(!Thread.currentThread().isInterrupted()) {
      try{
        Thread.sleep(1000);
      }catch (InterruptedException e) {
        // This is exactly what we want, isn't it?
      }

      Util.log(
          String.format("%10d\t%10d\t%10d",wrong.getTime(),initil.getTime(),(initil.getTime()-wrong.getTime()))
      );
    }

  }
}
