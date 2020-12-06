package assignment02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Assignment 2 exercise 1.
 *
 * Client serves as invoking stub-object.
 * Uses Java Sockets for communication.
 */
public class Client implements KVStore{
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  @Override
  public void put(String key, String value) {
    String message = "put " + key + " " + value;
    out.println(message);
  }

  @Override
  public String get(String key) {
    String message = "get " + key;
    out.println(message);
    String value = "";
    try {
      value = in.readLine();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    return value;
  }

  @Override
  public boolean isEmpty() {
    out.println("isEmpty");
    try {
      return in.readLine().equals("true");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    return false;
  }

  public void exit() {
    out.println("exit");
  }

  /**
   * Opens socket connection to localhost:8080
   */
  public Client() {
    try {
      clientSocket = new Socket(Server.HOST, Server.PORT_NUMBER);
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
