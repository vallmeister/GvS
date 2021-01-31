package assignment05.time;

public class Lamport implements Comparable<Lamport>{
  private long time;

  public Lamport() {
    this(0);
  }

  public Lamport(long init) {
    this.time = init;
  }

  public long getTime() {
    return time;
  }

  public long increment() {
    return ++time;
  }

  public long merge(Lamport b) {
    time = Long.max(time, b.time)+1;
    return time;
  }

  public static Lamport merge(Lamport a, Lamport b) {
    return new Lamport(Long.max(a.time, b.time)+1);
  }

  public static int compare(Lamport a, Lamport b) {
    return Long.compare(a.time, b.time);
  }

  public boolean equals(Lamport b) {
    return b.time == time;
  }

  @Override
  public int compareTo(Lamport l) {
    return Lamport.compare(this, l);
  }
}
