package assignment01;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.net.Socket;

public class ZeroMQrequester {

  /*
  Host and port for connection are given as constants instead of reading arguments
   */
  private static final String HOST = "gvs.lxd-vs.uni-ulm.de";
  private static final int PORT = 27347;

  public static void main(String[] args) {
    // like tutorial
    ZContext context = new ZContext();
    ZMQ.Socket requester = context.createSocket(SocketType.REQ);
    requester.setIPv6(true);
    requester.connect("tcp://gvs.lxd-vs.uni-ulm.de:27347");

    String request = "hey";
    requester.send(request.getBytes(ZMQ.CHARSET));
    byte[] reply = requester.recv();
    System.out.println("Received " + new String(reply, ZMQ.CHARSET));
  }
}
