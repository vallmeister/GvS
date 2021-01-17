package assignment04;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author David Mödinger
 *
 * This Clock represents a clock synchronized to our server.
 * It should regularly synchronize.
 * It should apply the christian protocol
 * For every synchronization attempt, there should be 'requests' many requests to the server. All of them should be used.
 *
 */
public class SynchronizedClock implements Clock, Runnable {
  private long currentTime;
  private ZMQ.Context context;
  private int numberOfRequests;

  // using BaseClock for own time
  private BaseClock baseClock;

  public SynchronizedClock(ZMQ.Context context, String host, int requests) {
    this.context = context;
    this.numberOfRequests = requests;
    getServerTime(true);
    baseClock = new BaseClock(currentTime);
    (new Thread(this)).start();
  }

  public SynchronizedClock(ZMQ.Context context, String host, int requests, long start) {
    // TODO
    this.numberOfRequests = requests;
    this.context = context;
    currentTime = start;
    baseClock = new BaseClock(currentTime);
    (new Thread(this)).start();
  }

  public long getTime() {
    getServerTime(false);
    return currentTime;
  }

   // Requests time from server and adjusts internal clock speed
  private void getServerTime(boolean initial) {
    ZMQ.Socket requestSocket = context.socket(SocketType.REQ);
    requestSocket.connect("tcp://gvs.lxd-vs.uni-ulm.de:3322");
    long startTime = System.currentTimeMillis();
    requestSocket.send("");
    byte[] reply = requestSocket.recv();
    long endTime = System.currentTimeMillis();
    long temp = Long.parseLong(new String(reply, ZMQ.CHARSET)) + endTime - startTime;

    if (temp > currentTime && !initial) {
      baseClock.speed_increase();
      currentTime = temp;
    } else if (temp < currentTime && !initial) {
      // never set the currentTime back
      baseClock.speed_decrease();
    }
  }

  @Override
  public void run() {
    while (true) {
      if (currentTime < baseClock.getTime()) {
        currentTime = baseClock.getTime();
      }
    }
  }
}
