package GvS.assignment05;

import assignment05.consistency.CausalMap;
import assignment05.time.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

public class CausalMapExample {
  public static void main(String[] args) throws InterruptedException {
    var c1 = new Vector(3,0);
    var c2 = new Vector(3,1);
    var c3 = new Vector(3,2);

    var ps = new ArrayList<CausalMap>(3);

    ps.add(new CausalMap(c1)); // p1
    ps.add(new CausalMap(c2)); // p2
    ps.add(new CausalMap(c3)); // p3

    for(int i=0; i<12; i+=1) {
      var r = new Random();
      if(i<6)
        ps.get(i%3).put(Integer.toString(i%4), Integer.toHexString(r.nextInt()));
      if(i==7)
        ps.get(0).remove(Integer.toString(0));
      for(var p: ps){
        StringJoiner joiner = new StringJoiner(",");
        p.Values().stream().forEach(e->joiner.add("\""+e+"\""));
        System.out.print("["+joiner.toString()+"] ");
      }
      System.out.print("\n");
      if(i>8)
        Thread.sleep(1000);
    }
    System.exit(0);
  }
}
