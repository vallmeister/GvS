package assignment04;

import java.util.*;

public class Vector {
  private long[] vector;
  private int pid;
  private int dimension;

  public Vector(Collection<Long> C, int id) {
    vector = C.stream().mapToLong(l -> l).toArray();
    pid = id;
    dimension = vector.length;
  }

  public Vector(int size, int id){
    vector = new long[size];
    pid = id;
    dimension = size;
  }

  /**
   * Returns all times in the vector
   */
  public long[] getTime() {
    return vector.clone();
  }

  /**
   * Also returns incremented time for own processid
   */
  public long increment() {
    long tmp = ++vector[pid];
    return tmp;
  }

  /**
   * Returns time of given id
   */
  public long getTime(int id) {
    return vector[id];
  }

  public long merge(Vector b) throws IllegalArgumentException{
    if (dimension != b.size()) throw new IllegalArgumentException("Vectors are of diefferent size");

    for (int i = 0; i < dimension; i++) {
      vector[i] = Math.max(vector[i], b.getTime(i));
    }
    increment();
    return getTime(pid);
  }

  public long size() {
    return dimension;
  }

  /**
   * Greater-or-Equals Comparison
   * IllegalArgumentException is thrown when vectors are of different size.
   */
  public boolean geq(Vector b) throws IllegalArgumentException {
    if (dimension != b.dimension) throw new IllegalArgumentException("Vectors are of different size");
    for (int i = 0; i < dimension; i++) {
      if (vector[i] < b.getTime(i)) return false;
    }
    return true;
  }

  /**
   *
   * @return Positive if a>b, Negative if a<b, 0 if a==b, empty Optional if not ordered
   * @throws IllegalArgumentException If Vectors are of different size
   */
  public static Optional<Integer> compare(Vector a, Vector b) throws IllegalArgumentException {
    if (a.size() != b.size()) throw new IllegalArgumentException("Vectors are of different size");

    // don't know why the testcases get Optional instead of actual value
    if (a.equals(b)) return Optional.of(0);
    if (a.geq(b)) return Optional.of(1);
    if (b.geq(a)) return Optional.of(-1);

    return Optional.empty();
  }

  public boolean equals(Vector b) {
    for (int i = 0; i < dimension; i++) {
      if (vector[i] != b.getTime(i)) return false;
    }
    return true;
  }
}
