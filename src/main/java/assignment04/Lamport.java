package assignment04;

public class Lamport implements Comparable<Lamport>{
  private long counter;

  public Lamport() {
    counter = 0;
  }

  public Lamport(long init) {
    counter = init;
  }

  public long getTime() {
    // TODO
    return counter;
  }

  /**
   * Also returns incremented time.
   */
  public long increment() {
    counter++;
    return counter;
  }

  public long merge(Lamport b) {
    // TODO
    return 0;
  }

  public static Lamport merge(Lamport a, Lamport b) {
    // TODO
    return null;
  }

  public static int compare(Lamport a, Lamport b) {
    // TODO
    return 0;
  }

  public boolean equals(Lamport b) {
    return b.getTime() == getTime();
  }

  @Override
  public int compareTo(Lamport l) {
    if (l.getTime() > getTime()) return -1;
    else if (l.getTime() < getTime()) return 1;
    return 0;
  }
}
