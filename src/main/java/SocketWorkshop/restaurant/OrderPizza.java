package SocketWorkshop.restaurant;

import SocketWorkshop.Italiana;

import java.io.*;

/**
 * Aufgabe 1
 * Simple Java-Stream I/O
 */
public class OrderPizza {

  public static void main(String[] args) {
    if (args.length < 1 || !Italiana.menu.containsKey(args[0])) {
      System.err.println("Please specify a valid pizza.");
      System.err.println();
      System.err.println("Italiana Menu:");

      for (String pizza : Italiana.menu.keySet()) {
        System.err.println(" * " + pizza);
      }
      System.exit(1);
    }
    // add your code
    try (FileOutputStream fileOutputStream
             = new FileOutputStream("./resources/bestellungen.txt", false)) {
      String pizzaString = args[0] + "\n";
      byte[] pizzaBytes = pizzaString.getBytes();
      fileOutputStream.write(pizzaBytes);
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
