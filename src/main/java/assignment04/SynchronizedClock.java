package assignment04;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Mödinger
 *
 * This Clock represents a clock synchronized to our server.
 * It should regularly synchronize.
 * It should apply the christian protocol
 * For every synchronization attempt, there should be 'requests' many requests to the server. All of them should be used.
 *
 */
public class SynchronizedClock implements Runnable, Clock {
  private long currentTime;
  private ZMQ.Context context;
  private String host;
  private int numberOfRequests;

  public SynchronizedClock(ZMQ.Context context, String host, int requests) {
    this.context = context;
    this.host = host;
    this.numberOfRequests = requests;

    ZMQ.Socket requestSocket = context.socket(SocketType.REQ);
    requestSocket.connect("tcp://gvs.lxd-vs.uni-ulm.de:3322");
    requestSocket.send("");
    byte[] reply = requestSocket.recv();
    currentTime = Long.parseLong(new String(reply, ZMQ.CHARSET));
    Thread thread = new Thread(this);
    thread.run();
  }

  public SynchronizedClock(ZMQ.Context context, String host, int requests, long start) {
    // TODO
    this.numberOfRequests = requests;
    this.context = context;
    this.host = host;
    currentTime = start;
  }

  public long getTime() {
    return currentTime;
  }

  @Override
  public void run() {
    List<Thread> threadList = new ArrayList<>(numberOfRequests);
    for (int i = 0; i < numberOfRequests; i++) {
      threadList.add(new Thread(new Runnable() {
        @Override
        public void run() {
          ZMQ.Socket requestSocket = context.socket(SocketType.REQ);
          requestSocket.connect("tcp://gvs.lxd-vs.uni-ulm.de:3322");
          long timeBefore = System.currentTimeMillis();
          requestSocket.send("");
          byte[] reply = requestSocket.recv();
          long timeAfter = System.currentTimeMillis();
          long roundTripTime = timeAfter - timeBefore;
          currentTime = Long.parseLong(new String(reply, ZMQ.CHARSET)) + roundTripTime / 2;
        }
      }));

      while (!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(1_000);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
        for (Thread thread : threadList) {
          thread.run();
        }
      }
    }
  }
}
