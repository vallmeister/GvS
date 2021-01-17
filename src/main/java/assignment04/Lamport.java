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
    counter = Math.max(counter, b.getTime());
    increment();
    return counter;
  }

  public static Lamport merge(Lamport a, Lamport b) {
    return new Lamport(a.merge(b));
  }

  public static int compare(Lamport a, Lamport b) {
    return a.compareTo(b);
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
