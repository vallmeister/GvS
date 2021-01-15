package GvS.assignment04;

import assignment04.Vector;

import java.util.Arrays;

public class VectorTime {
  public static String prettyVector(Vector v) {
    String res = "[";
    for(long l : v.getTime()) {
      res += l + ",";
    }
    res = res.substring(0, res.length()-1);
    res += "]";
    return res;
  }

  public static void main(String[] args) throws IllegalArgumentException {
    Vector a,b,c,d,e;
    a = new Vector(Arrays.asList(0L,4L,8L),0);
    b = new Vector(Arrays.asList(0L,1L,0L),0);
    c = new Vector(Arrays.asList(1L,1L,1L),1);
    d = new Vector(Arrays.asList(3L,2L,5L),2);
    e = new Vector(3,2);

    b.merge(a);
    System.out.println("Should be [0,4,8]: "+prettyVector(a));
    System.out.println("Should be [1,4,8]: "+prettyVector(b));
    System.out.println("Should be [1,1,1]: "+prettyVector(c));
    System.out.println("Should be [3,2,5]: "+prettyVector(d));
    System.out.println("Should be [0,0,0]: "+prettyVector(e));
    System.out.println("Should be 9: "+d.merge(a));
    System.out.println("Should be false: "+a.geq(b));
    System.out.println("Should be true:  "+b.geq(a));
    System.out.println("Should be true:  "+Vector.compare(a,c).isEmpty());
    System.out.println("Should be true:  "+e.equals(e));
    System.out.println("Should be false: "+a.equals(b));
    System.out.println("Should be false: "+a.equals(c));
  }
}
