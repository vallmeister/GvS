package GvS.assignment04;

import assignment04.Lamport;

import java.util.ArrayList;
import java.util.Arrays;

public class LamportTime {
  public static class Process{
    private Lamport clock;
    public int pid;

    public Process(int pid) {
      this.pid = pid;
      clock = new Lamport();
    }

    public Process(int pid, long init) {
      this.pid = pid;
      clock = new Lamport(init);
    }

    public void Event(Process from) {
      clock.merge(from.clock);
    }

    public long getTime() {
      return clock.getTime();
    }
  }

  public static void main(String[] args) {
    Process p1,p2,p3;

    p1 = new Process(0);
    p2 = new Process(1);
    p3 = new Process(2, 10);

    Process[] to =   {p1, p2, p1, p3, p1, p2, p1, p3, p1, p2, p1, p3};
    Process[] from = {p1, p2, p3, p1, p2, p3, p1, p2, p3, p1, p2, p3};

    System.out.println(String.format("pid%6d\t%9d\t%9d\tevents", p1.pid,p2.pid,p3.pid));
    System.out.println("---------------------------------------------");
    for(int i=0; i<from.length && i<to.length; i+=1) {
      System.out.println(String.format("%9d\t%9d\t%9d",p1.getTime(),p2.getTime(),p3.getTime()));
      to[i].Event(from[i]);
      System.out.println(String.format("\t\t\t\t\t\t\t\t\t %1d -> %1d",from[i].pid, to[i].pid));
    }
    System.out.println(String.format("%9d\t%9d\t%9d", p1.getTime(),p2.getTime(),p3.getTime()));
  }
}
