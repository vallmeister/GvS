package assignment02;

/**
 * Assignment 2 exercise 1.
 *
 * Client serves as invoking stub-object.
 * Uses Java Sockets for communication.
 */
public class Client implements KVStore{

  @Override
  public void put(String key, String value) {

  }

  @Override
  public String get(String key) {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Opens socket connection to localhost:8080
   */
  public Client() {
  }
}
