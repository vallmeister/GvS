package assignment05.consistency;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This implements a communicator that may scramble messages.
 * All communication should run through this thread.
 *
 * If the thread dies the program is useless, so we don't capture any errors.
 */
public class Communicator {
  private class MessageDistributor implements Runnable {
    private ArrayList<LinkedBlockingQueue<CausalUpdate>> recipients = new ArrayList<LinkedBlockingQueue<CausalUpdate>>();

    public LinkedBlockingQueue<LinkedBlockingQueue<CausalUpdate>> newrecipients = new LinkedBlockingQueue<LinkedBlockingQueue<CausalUpdate>>();

    public LinkedBlockingQueue<CausalUpdate> input = new LinkedBlockingQueue<CausalUpdate>();

    public AtomicBoolean keep_running = new AtomicBoolean(true);

    @Override
    public void run() {
      // Do magic to distribute stuff
      while(keep_running.get()) {
        try {
          var update = input.take();
          while(newrecipients.peek() != null) {
            recipients.add(newrecipients.take());
          }
          for(var recipient: recipients) {
            recipient.offer(update);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static Communicator instance;

  private MessageDistributor md;
  private Thread runner;

  private Communicator() {
    md = new MessageDistributor();
    runner = new Thread(md);
    runner.start();
  }

  public static synchronized Communicator getInstance() {
    if(Communicator.instance == null) {
      Communicator.instance = new Communicator();
    }
    return Communicator.instance;
  }

  public void put(CausalUpdate update) throws InterruptedException {
    md.input.put(update);
  }

  public void terminate() throws InterruptedException {
    md.keep_running.set(false);
    runner.interrupt();
    runner.join();
  }

  public void restart() {
    // Simple but unsafe.
    if(!md.keep_running.getAndSet(true))
    {
      runner = new Thread(md);
      runner.start();
    }
  }

  public void register(LinkedBlockingQueue<CausalUpdate> box) throws InterruptedException {
    md.newrecipients.put(box);
  }
}
