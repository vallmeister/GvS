package assignment03;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test implementation for exercise 1
 */
public class Exercise01 implements Runnable{
  // number of cores we use
  public final static int CORES = 4;

  public static ZContext context = new ZContext();
  private static boolean noSolutionFoundYet = true;

  // ith search space when we divide the space by CORES
  private int searchSpace;

  private String challenge;

  public static void main(String[] args) {
    // subscribe
    ZMQ.Socket subscriberSocket = context.createSocket(SocketType.SUB);
    // Given by the exercise instead of using args
    subscriberSocket.connect("tcp://gvs.lxd-vs.uni-ulm.de:27341");
    subscriberSocket.subscribe("");

    // receive
    String challenge = new String(subscriberSocket.recv(0), ZMQ.CHARSET);
    System.out.println(challenge);

    Util util;
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < CORES; i++) {
      threads.add(new Thread(new Exercise01(i, challenge)));
    }
    for (Thread thread : threads) {
      thread.start();
    }


  }

  @Override
  public void run() {
    // find solution up to 15 digits
    ZMQ.Socket replySocket = context.createSocket(SocketType.REQ);
    replySocket.connect("tcp://gvs.lxd-vs.uni-ulm.de:27349");


    Util util;
    try {
      util = new Util();
      while (noSolutionFoundYet) {
        // Partitioning the search space
        String solution = challenge + intToHexstring(
            (int) (16 / CORES *(searchSpace +  Math.random())));
        double digits = 15 * Math.random();
        int upperBound = (int) digits;

        for (int i = 0; i < upperBound; i++) {
          double tmp = 15.9d * Math.random();
          solution += intToHexstring((int) tmp);
          if (util.validate(challenge, solution)) {
            noSolutionFoundYet = false;
            System.out.println(solution);

            // reply
            replySocket.send(solution);
            String reply = new String(replySocket.recv(0), ZMQ.CHARSET);
            System.out.println(reply);
          }
        }
      }

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private Exercise01(int searchSpace, String challenge) {
    this.searchSpace = searchSpace;
    this.challenge = challenge;
  }

  // code is ugly af, but didn't come up with a simpler solution
  public static String intToHexstring(int digit) {
    if (digit < 10) return "" + digit;
    char tmp = 'W'; // ASCII 87
    tmp += digit;
    String solution = "" + tmp;
    return solution;
  }
}
