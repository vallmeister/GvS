package assignment02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Subscriber extends Remote {
  public void update(String news) throws RemoteException;

  public String getUUID() throws RemoteException;
}
