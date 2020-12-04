package assignment02;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Assignment 2 exercise 1.
 *
 * RemoteKVStore serves as actual RMI object.
 */
public class RemoteKVStore implements KVStore {

  private Map<String, String> store;

  private static RemoteKVStore remoteKVStore;

  @Override
  public void put(String key, String value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("Key or Value is NULL");
    }
    store.put(key, value);
  }

  @Override
  public String get(String key) {
    if (!store.containsKey(key)) {
      throw new NoSuchElementException("Key has no value");
    }
    return store.get(key);
  }

  @Override
  public boolean isEmpty() {
    return store.size() == 0;
  }

  private RemoteKVStore(){
    store = new HashMap<>();
  }

  public static RemoteKVStore remoteKVStoreFactory() {
    if (remoteKVStore == null) {
      remoteKVStore = new RemoteKVStore();
    }
    return remoteKVStore;
  }
}
