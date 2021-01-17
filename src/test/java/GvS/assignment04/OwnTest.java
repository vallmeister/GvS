package GvS.assignment04;

import assignment04.BaseClock;
import assignment04.SynchronizedClock;
import org.zeromq.ZMQ;

public class OwnTest {

  public static void main(String[] args) throws InterruptedException {
    BaseClock baseClock = new BaseClock(System.currentTimeMillis());
    ZMQ.Context context = ZMQ.context(1);
    SynchronizedClock synchronizedClock = new SynchronizedClock(context, "gvs.lxd-vs.uni-ulm.de:3322",
        2, System.currentTimeMillis());
    SynchronizedClock clock2 = new SynchronizedClock(context, "gvs.lxd-vs.uni-ulm.de:3322", 5);
    for (int i = 0; i < 10; i++) {
      System.out.println(synchronizedClock.getTime());
      Thread.sleep(1000);
    }
  }
}
