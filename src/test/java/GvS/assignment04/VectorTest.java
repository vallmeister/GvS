package GvS.assignment04;

import assignment04.Vector;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
  @org.junit.jupiter.api.Test
  void increment() {
    Vector a,b,c;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(0L,4L,8L),1);
    c = new Vector(Arrays.asList(0L,4L,8L),2);

    assertEquals(1,a.increment());
    assertEquals(5,b.increment());
    assertEquals(9,c.increment());
  }

  @org.junit.jupiter.api.Test
  void testGetTime() {
    Vector a,b,c;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(0L,4L,8L),1);
    c = new Vector(Arrays.asList(0L,4L,8L),2);

    long[] test = {0L, 4L, 8L};

    assertEquals(0,a.getTime(0));
    assertEquals(4,b.getTime(1));
    assertEquals(8,c.getTime(2));

    assertTrue(Arrays.equals(a.getTime(),c.getTime()));
    assertTrue(Arrays.equals(test ,c.getTime()));
  }

  @org.junit.jupiter.api.Test
  void merge() {
    Vector a,b,x;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(1L,3L,5L),0);

    long[] c = {2L,4L,8L};
    x = new Vector(5,0);

    assertEquals(2, b.merge(a));
    assertTrue(Arrays.equals(c, b.getTime()));
    assertThrows(IllegalArgumentException.class, () -> {x.merge(a);});
  }

  @org.junit.jupiter.api.Test
  void size() {
    Vector a,b;
    a = new Vector(3,2);
    b = new Vector(Arrays.asList(2L,5L,8L),2);

    assertEquals(3, a.size());
    assertEquals(3, b.size());
  }

  @org.junit.jupiter.api.Test
  void geq() {
    Vector a,c,n1,n2,x;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    c = new Vector(Arrays.asList(2L,5L,8L),2);

    n1 = new Vector(Arrays.asList(0L,0L,0L),2);
    n2 = new Vector(3,0);

    x = new Vector(5,0);

    assertTrue(n1.geq(n2) && n2.geq(n1));
    assertTrue(c.geq(a));

    assertFalse(a.geq(c));

    assertThrows(IllegalArgumentException.class, () -> {x.geq(a);});
  }

  @org.junit.jupiter.api.Test
  void compare() {
    Vector a,b,c,n1,n2,x;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(0L,4L,8L),1);
    c = new Vector(Arrays.asList(2L,5L,8L),2);

    n1 = new Vector(Arrays.asList(0L,0L,0L),2);
    n2 = new Vector(3,0);

    x = new Vector(Arrays.asList(8L,4L,0L),2);

    assertEquals(0, java.util.Optional.of(Vector.compare(a, b)).get());
    assertEquals(0, java.util.Optional.of(Vector.compare(n1, n2)).get());

    assertTrue( Vector.compare(c,a).get()>0);
    assertTrue( Vector.compare(a,c).get()<0);
    assertTrue( Vector.compare(a,x).isEmpty());
    assertTrue( Vector.compare(a,c).isPresent());
  }

  @org.junit.jupiter.api.Test
  void testEquals() {
    Vector a,b,n1,n2,x;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(0L,4L,8L),2);

    n1 = new Vector(Arrays.asList(0L,0L,0L),2);
    n2 = new Vector(3,0);

    x = new Vector(Arrays.asList(8L,4L,0L),2);

    assertTrue( a.equals(b));
    assertTrue( n2.equals(n1) && n1.equals(n2));

    assertFalse( a.equals(x));
  }
}