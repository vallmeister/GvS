package assignment01;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZeroMQrequester {

  public static void main(String[] args) {
    // like tutorial
    ZContext context = new ZContext();
    ZMQ.Socket requester = context.createSocket(SocketType.REQ);
    requester.setIPv6(true);

    // Given by the exercise instead of using args
    requester.connect("tcp://gvs.lxd-vs.uni-ulm.de:27347");

    String request;
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Type in your message. End with 'exit'.");
    while (true) {
      try {
        request = stdIn.readLine();
        if (request == null || request.equals("exit")) {
          break;
        }
        requester.send(request.getBytes(ZMQ.CHARSET));
        byte[] reply = requester.recv();
        System.out.println("Received " + new String(reply, ZMQ.CHARSET));
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
}
