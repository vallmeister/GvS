package assignment05.time;

import java.util.Collection;
import java.util.Optional;

public class Vector {
  private long[] clocks;
  private int id;

  public Vector(Collection<Long> C, int id) {
    this.id = id;
    clocks = new long[C.size()];
    int i = 0;
    for(Long l : C)
      clocks[i++] = l;
  }

  public Vector(Vector clock) {
    this.id = id;
    clocks = new long[clock.clocks.length];
    int i = 0;
    for(Long l : clock.clocks)
      clocks[i++] = l;
  }

  public Vector(int size, int id){
    this.id = id;
    clocks = new long[size];
  }

  public long[] getTime() {
    return clocks.clone();
  }

  public long increment() {
    return ++clocks[id];
  }

  public long getTime(int id) {
    return clocks[id];
  }

  public long merge(Vector b) throws IllegalArgumentException{
    if(b.size() == size()) {
      for(int i=0; i<size(); i+=1) {
        clocks[i] = Long.max(clocks[i],b.clocks[i]);
      }
    }else{
      throw new IllegalArgumentException("VectorClocks must be of same size to be merged.");
    }
    return increment();
  }

  public long size() {
    return clocks.length;
  }

  public boolean geq(Vector b) throws IllegalArgumentException {
    var comp = Vector.compare(this, b);
    if(comp.isEmpty())
      return false;
    return comp.get() >= 0;
  }

  /**
   *
   * @return Positive if a>b, Negative if a<b, 0 if a==b, empty Optional if not ordered
   * @throws IllegalArgumentException If Vectors are of different size
   */
  public static Optional<Integer> compare(Vector a, Vector b) throws IllegalArgumentException {
    if(a.size() != b.size())
      throw new IllegalArgumentException("VectorClocks must be of same size to compare.");

    boolean pos = false;
    boolean neg = false;

    for(int i=0; i<a.size() && (!pos || !neg); i+=1){
      var comp = a.clocks[i] - b.clocks[i];
      pos |= comp > 0;
      neg |= comp < 0;
    }
    if(pos && neg)
      return Optional.empty();
    return Optional.of(pos?1:(neg?-1:0));
  }

  public boolean equals(Vector b) {
    try {
      return Vector.compare(this, b).orElse(1) == 0;
    }catch (IllegalArgumentException e) {
      return false;
    }
  }
}
