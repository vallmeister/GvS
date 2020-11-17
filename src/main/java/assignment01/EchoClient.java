package assignment01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

  /*
  Host and port for connection are given as constants instead of reading arguments
   */
  private static final String HOST = "gvs.lxd-vs.uni-ulm.de";
  private static final int PORT = 3211;

  public static void main(String[] args) {
    // Like the given tutorial
    try {
      Socket socket= new Socket(HOST, PORT);
      PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

      String userInput;
      System.out.println("Type in your echo message. End with 'exit'");
      while ((userInput = stdIn.readLine()) != null) {
        if (userInput.equals("exit")) {
          break;
        }
        printWriter.println(userInput);
        System.out.println("echo: " + in.readLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
