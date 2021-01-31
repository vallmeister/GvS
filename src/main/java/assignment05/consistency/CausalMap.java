package assignment05.consistency;

import assignment05.time.Vector;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementieren Sie das Interface ConsistentMap<String,String> für diese Klasse.
 * Die Semantik sollte eine Kausale Konsistenz garantieren.
 *
 * Kommunikation zwischen Instanzen sollte ausschließlich über die Communicator Komponente stattfinden.
 * Vergleichen Sie hierzu die vorgegebenen Zeilen im Konstruktor der Klasse.
 * Hierzu ist es zudem nötig die Klasse CausalUpdate zu implementieren.
 */
public class CausalMap implements ConsistentMap<String,String> {
  private final static String DEL = "DELETED";

  private Map<String, String> map;
  private Vector vectorClock;

  @Override
  public int size() {
    return Values().size();
  }

  @Override
  public boolean isEmpty() {
    Collection<String> values = map.values();
    for (String string : values) {
      if (!string.equals(DEL)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean containsKey(String s) {
    return map.containsKey(s) && get(s) != null;
  }

  @Override
  public boolean containsValue(String s) {
    return map.containsValue(s);
  }

  @Override
  public void remove(String key) throws InterruptedException {
    map.replace(key, DEL);
    vectorClock.increment();
    update(new CausalUpdate(key, DEL, false, vectorClock));
  }

  @Override
  public void put(String key, String value) throws InterruptedException {
    map.put(key, value);
    vectorClock.increment();
    update(new CausalUpdate(key, value, true, vectorClock));
  }

  @Override
  public String get(String key) {
    String string = map.get(key);
    return string.equals(DEL) ? null : string;
  }

  @Override
  public Collection<String> Values() {
    Collection<String> values = map.values();
    values.removeIf((String string) -> (string.equals(DEL)));
    return values;
  }

  @Override
  public Set<String> keySet() {
    Set<String> keySet = map.keySet();
    for (String string : keySet) {
      if (map.get(string).equals(DEL)) {
        keySet.remove(string);
      }
    }
    return keySet;
  }

  private class CausalEntry implements Runnable{
    LinkedBlockingQueue<CausalUpdate> updates;
    Map<String, String> map; // Zu verwaltende Map
    Vector lastUpdate;

    CausalEntry(LinkedBlockingQueue<CausalUpdate> causalUpdates, Map<String, String> stringMap) {
      updates = causalUpdates;
      map = stringMap;
    }
    @Override
    public void run() {
      /*
      Wartet auf Updates in der Queue, um diese anzuwenden
       */
      /*while (true) {
        /*if (!updates.isEmpty()) {
          for (int i = 0; i < updates.size(); i++) {
            CausalUpdate causalUpdate = updates.poll();
            Vector currentTime = causalUpdate.getClock();
            if (!lastUpdate.equals(currentTime) && currentTime.geq(lastUpdate)) {
              lastUpdate = currentTime;
              /*if (causalUpdate.isOperation()) {
                map.put(causalUpdate.getKeyUpdate(), causalUpdate.getValueUpdate());
              } else {
                map.replace(causalUpdate.getKeyUpdate(), DEL);
              }
            }
          }
        }
      }*/
    }
  }

  private Communicator com;

  private LinkedBlockingQueue<CausalUpdate> input;

  public CausalMap(Vector clock) throws InterruptedException {
    com = Communicator.getInstance();
    input = new LinkedBlockingQueue<CausalUpdate>();
    com.register(input);

    map = new HashMap<>();
    vectorClock = clock;

    CausalEntry entry = new CausalEntry(input, map);
    Thread thread = new Thread(entry);
    thread.run();

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        while (true) {
          for (int i = 0; i < input.size(); i++) {
            CausalUpdate causalUpdate = input.poll();
            Vector currentTime = causalUpdate.getClock();
            if (!vectorClock.equals(currentTime) && currentTime.geq(vectorClock)) {
              vectorClock.merge(currentTime);
              if (causalUpdate.isOperation()) {
                map.put(causalUpdate.getKeyUpdate(), causalUpdate.getValueUpdate());
              } else {
                map.replace(causalUpdate.getKeyUpdate(), DEL);
              }
            }
          }
        }
      }
    };
    Timer timer = new Timer();
    timer.schedule(task, 0, 100);
  }

  /**
   * Verwaltet Schreibzugriffe der Map. Vektoruhr wird inkrementiert und Aktualisierungsnachrichten werden verschickt.
   */
  private void update(CausalUpdate update) throws InterruptedException {
    vectorClock.increment();
    com.put(update);
  }
}
