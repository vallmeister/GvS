package assignment03;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerSubmitter {

  public final static int SIZE_OF_THREADPOOL = 4;

  // Singleton
  private static ControllerSubmitter controllerSubmitter;
  private static ZContext context = new ZContext();
  private static ExecutorService pool;
  private static String currentChallenge;

  private ControllerSubmitter() {
    // subscribe
    ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
    subscriber.connect("tcp://gvs.lxd-vs.uni-ulm.de:27341");
    subscriber.subscribe("");
    currentChallenge = new String(subscriber.recv(0), ZMQ.CHARSET);
    pool = Executors.newFixedThreadPool(SIZE_OF_THREADPOOL);
  }

  public void computeSolution() {
    pool.execute(new Worker(this, currentChallenge));
  }

  public void sendSolution(String solution) {
    pool.shutdown();
    ZMQ.Socket submitter = context.createSocket(SocketType.REQ);
    submitter.connect("tcp://gvs.lxd-vs.uni-ulm.de:27349");
    submitter.send(solution);
    String reply = new String(submitter.recv(0), ZMQ.CHARSET);
    System.out.println(reply);
  }

  public static ControllerSubmitter ControllerSubmitterFactory() {
    if (controllerSubmitter == null) {
      controllerSubmitter = new ControllerSubmitter();
    }
    return controllerSubmitter;
  }

  public String getCurrentChallenge() {
    return currentChallenge;
  }
}
