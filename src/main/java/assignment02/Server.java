package assignment02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Assignment 2 exercise 1.
 *
 * Receives requests from stub object and invokes the actual KV store. Returns replies to stub object.
 * Uses Java Sockets for communication.
 */
public class Server implements Runnable{
  final static String HOST = "localhost";
  final static int PORT_NUMBER = 8080;

  private RemoteKVStore remoteKVStore;

  /**
   * Opens socket connection to localhost:8080 and manages requests regarding KV store.
   */
  public Server() {
    remoteKVStore = RemoteKVStore.remoteKVStoreFactory();
  }

  public void run() {
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
      clientSocket = serverSocket.accept();
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));
      String input;


      while ((input = in.readLine())!= null){
        if (input.equals("exit")) {
          System.out.println("Server shutdown");
          break;
        }
        String[] message = input.split(" ");
        switch (message[0]) {
          case "put":
            remoteKVStore.put(message[1], message[2]);
            break;
          case "get":
            String value = remoteKVStore.get(message[1]);
            out.println(value);
            break;
          case "isEmpty":
            out.println(remoteKVStore.isEmpty());
            break;
          default:
            System.out.println("Wrong message");
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

  }
}
