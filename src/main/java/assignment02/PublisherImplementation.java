package assignment02;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class PublisherImplementation implements Publisher {
  private List<Subscriber> subscriberList = new ArrayList<>();

  @Override
  public void subscribe(Subscriber subscriber) throws RemoteException {
    if (subscriber == null) {
      throw new IllegalArgumentException("Subscriber is NULL");
    }
    subscriberList.add(subscriber);
  }

  @Override
  public void unsubscribe(Subscriber subscriber) throws RemoteException {
    if (subscriber == null) {
      throw new IllegalArgumentException("Subscriber is NULL");
    }
    subscriberList.remove(subscriber);
  }

  @Override
  public void notifySubscribers(String news) throws RemoteException {
    for (Subscriber subscriber : subscriberList) {
      Registry registry = LocateRegistry.getRegistry();
      Subscriber availableSub = null;
      try {
        availableSub = (Subscriber) registry.lookup(subscriber.getUUID());
      } catch (NotBoundException e) {
        e.printStackTrace();
      }
      if (availableSub == null) {
        unsubscribe(subscriber);
        continue;
      }
      subscriber.update(news);
    }
  }
}
