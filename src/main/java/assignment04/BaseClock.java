package assignment04;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author David Mödinger
 *
 */
public class BaseClock implements Runnable, Clock {
  private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
  private volatile long counter;
  private volatile ScheduledFuture<?> timerfuture;
  private volatile long tickrate;

  public BaseClock() {
    this(0);
  }

  public BaseClock(long start) {
    this(start,100);
  }

  public BaseClock(long start, int tickrate) {
    this.counter = start;
    this.tickrate = tickrate;
    this.timerfuture = this.timer.scheduleAtFixedRate(this, tickrate, tickrate, TimeUnit.MILLISECONDS);
  }

  private synchronized void update_tickspeed(long newspeed) {
    // Only allow tickrates of +-10x
    if(newspeed <10)
      newspeed  = 10;
    if(newspeed >1000)
      newspeed = 1000;
    tickrate = newspeed;
    // Update Clock
    long delay = timerfuture.getDelay(TimeUnit.MILLISECONDS);
    timerfuture.cancel(true);
    this.timerfuture = this.timer.scheduleAtFixedRate(this, delay, tickrate, TimeUnit.MILLISECONDS);
  }

  public void speed_decrease() {
    update_tickspeed((long)(1.1*tickrate));
  }

  public void speed_increase() {
    update_tickspeed((long)(0.9*tickrate));
  }

  public void speed_very_fast() {  update_tickspeed(10);}
  public void speed_fast() {       update_tickspeed(50);}
  public void speed_normal() {    update_tickspeed(100);}
  public void speed_slow() {      update_tickspeed(200);}
  public void speed_very_slow() {update_tickspeed(1000);}

  // Can only set time to a future date
  public void set_time_to_future(long future) {
    if(future > counter)
      counter = future;
  }

  public long getTickrate() { return tickrate; }

  public long getTime() {
    return counter;
  }

  public void stop() { timerfuture.cancel(true); }
  public void restart() { update_tickspeed(tickrate); }

  // Use shutdown once you finished using your Clock.
  public void shutdown() { timer.shutdown(); }

  @Override
  public void run() {
    counter+=100;
  }
}
