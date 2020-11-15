package SocketWorkshop.restaurant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Aufgabe 1
 */
public class ExecuteOrder {

  // add your code to take orders
  public static void main(String[] args) {

    // create FileInputStream to read into file
    try (FileInputStream fileInputStream = new FileInputStream("./src/main/resources/bestellungen.txt")){
      byte pizzaByte[] = fileInputStream.readAllBytes();
      String pizzaString = new String(pizzaByte);
      System.out.println(pizzaString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
