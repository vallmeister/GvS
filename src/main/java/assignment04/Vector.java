package assignment04;

import java.util.*;

public class Vector {
  // TODO

  public Vector(Collection<Long> C, int id) {
    // TODO
  }

  public Vector(int size, int id){
    // TODO
  }

  /**
   * Returns all times in the vector
   */
  public long[] getTime() {
    // TODO
    return new long[1];
  }

  /**
   * Also returns incremented time for own processid
   */
  public long increment() {
    // TODO
    return 0;
  }

  /**
   * Returns time of given id
   */
  public long getTime(int id) {
    // TODO
    return 0;
  }

  public long merge(Vector b) throws IllegalArgumentException{
    // TODO
    return 0;
  }

  public long size() {
    // TODO
    return 0;
  }

  /**
   * Greater-or-Equals Comparisson
   * IllegalArgumentException is thrown when vectors are of different size.
   */
  public boolean geq(Vector b) throws IllegalArgumentException {
    // TODO
    return false;
  }

  /**
   *
   * @return Positive if a>b, Negative if a<b, 0 if a==b, empty Optional if not ordered
   * @throws IllegalArgumentException If Vectors are of different size
   */
  public static Optional<Integer> compare(Vector a, Vector b) throws IllegalArgumentException {
    // TODO
    return null;
  }

  public boolean equals(Vector b) {
    // TODO
    return false;
  }
}
