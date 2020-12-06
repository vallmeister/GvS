package assignment02;

import java.rmi.RemoteException;

public class SubscriberImplementation implements Subscriber {
  private final String UUID;

  public SubscriberImplementation() {
    this.UUID = java.util.UUID.randomUUID().toString();
  }

  @Override
  public void update(String news) throws RemoteException {
    System.out.println("Subscriper " + UUID + " received new publication: " + news);
  }

  public String getUUID(){
    return UUID;
  }
}
