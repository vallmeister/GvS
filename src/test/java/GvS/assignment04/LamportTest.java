package GvS.assignment04;

import assignment04.Lamport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LamportTest {

  @Test
  void getTime() {
    Lamport a,b;
    a = new Lamport();
    b = new Lamport(12);

    assertEquals(0, a.getTime());
    assertEquals(12, b.getTime());
  }

  @Test
  void increment() {
    Lamport a,b;
    a = new Lamport();
    b = new Lamport(12);

    assertEquals(1, a.increment());
    assertEquals(13, b.increment());
  }

  @Test
  void merge() {
    Lamport a,b,c;
    a = new Lamport();
    b = new Lamport(6);
    c = new Lamport(12);

    assertEquals(7, a.merge(b));
    assertEquals(13, c.merge(b));
    assertEquals(7, b.merge(b));

    a = new Lamport();
    b = new Lamport(6);
    c = new Lamport(12);
    assertEquals(7, Lamport.merge(a,b).getTime());
    assertEquals(13, Lamport.merge(c,b).getTime());
    assertEquals(7, Lamport.merge(b,b).getTime());
  }

  @Test
  void compare() {
    Lamport a,b,c;
    a = new Lamport();
    b = new Lamport(6);
    c = new Lamport(12);

    assertEquals(7, a.merge(b));
    assertEquals(13, c.merge(b));
    assertEquals(7, b.merge(b));
  }

  @Test
  void testEquals() {
    Lamport a,b,c;
    a = new Lamport();
    b = new Lamport(0);
    c = new Lamport(12);

    assertTrue(a.equals(a));
    assertTrue(c.equals(c));
    assertTrue(a.equals(b));

    assertFalse(a.equals(c));
    assertFalse(c.equals(a));
  }

  @Test
  void compareTo() {
    Lamport a,b,c;
    a = new Lamport();
    b = new Lamport(0);
    c = new Lamport(12);

    assertEquals(Long.compare(a.getTime(), a.getTime()), a.compareTo(a));
    assertEquals(Long.compare(a.getTime(), b.getTime()), a.compareTo(b));
    assertEquals(Long.compare(a.getTime(), c.getTime()), a.compareTo(c));
    assertEquals(Long.compare(c.getTime(), a.getTime()), c.compareTo(a));
  }
}