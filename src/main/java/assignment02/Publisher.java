package assignment02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Publisher extends Remote {
  public void subscribe(Subscriber subscriber) throws RemoteException;

  public void unsubscribe(Subscriber subscriber) throws RemoteException;

  public void notifySubscribers(String news) throws RemoteException;
}
