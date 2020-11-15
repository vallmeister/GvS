package SocketWorkshop.service;

import SocketWorkshop.Italiana;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static SocketWorkshop.Italiana.PORT_NUMBER;
/*
 * Aufgabe 2
 * Single-Threaded Server
 */
public class PizzaService {
  public static void main(String[] args) {

    // create a Socket and take orders
    try {
      ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
      InputStream inputStream;
      OutputStream outputStream;
      while (true) {
        Socket socket;
        try {
          socket = serverSocket.accept();
          inputStream = socket.getInputStream();
          String orderedPizza = new String(inputStream.readAllBytes());

          outputStream = socket.getOutputStream();
          outputStream.write(("Your order '" + orderedPizza + "' is being prepared...\n").getBytes());
          Thread.sleep(Italiana.menu.get(orderedPizza) * 1000);
          outputStream.write(("Your order '" + orderedPizza + "' is ready!\n").getBytes());

          outputStream.close();
          inputStream.close();
        } catch (IOException | InterruptedException ioException) {
          ioException.printStackTrace();
        }
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
