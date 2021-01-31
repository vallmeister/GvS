package assignment05.consistency;

import assignment05.time.Vector;

import java.util.Map;

/**
 * Implementieren Sie die Hilfsklasse CausalUpdate, diese wird genutzt um Updates in der Map zwischen verschiedenen
 * Instanzen zu kommunizieren.
 */
public class CausalUpdate {

  private String keyUpdate;
  private String valueUpdate;
  private boolean operation;
  private Vector clock;

  public CausalUpdate(String key, String value, boolean op, Vector vector) {
    keyUpdate = key;
    valueUpdate = value;
    clock = vector;
    operation = op;
  }

  /*
  Gibt Uhrzeit des Updates zurueck
   */
  public Vector getClock() {
    return clock;
  }

  public String getKeyUpdate() {
    return keyUpdate;
  }

  public String getValueUpdate() {
    return valueUpdate;
  }

  /*
  Gibt Operation des Updates zurueck. Entdeweder false bei remove() oder true bei put()
   */
  public boolean isOperation() {
    return operation;
  }
}
