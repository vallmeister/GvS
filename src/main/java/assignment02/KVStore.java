package assignment02;

/**
 * Assignment 2 exercise 1 interface for String Map.
 */
public interface KVStore {

  public void put(String key, String value);

  public String get(String key);

  public boolean isEmpty();
}
