package assignment02;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Assignment 2 exercise 1.
 *
 * Receives requests from stub object and invokes the actual KV store. Returns replies to stub object.
 * Uses Java Sockets for communication.
 */
public class Server {
  final static String HOST = "localhost";
  final static int PORT_NUMBER = 8080;

  private RemoteKVStore remoteKVStore;

  /**
   * Opens socket connection to localhost:8080 and manages requests regarding KV store.
   */
  public Server() {
    remoteKVStore = RemoteKVStore.remoteKVStoreFactory();

    try {
      ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
