package assignment02;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Showing that implementation for exercise 2 works. Binds to port 8080.
 */
public class Exercise02 {
  final static int PORT = 8080;

  public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
    System.out.println("Showing my implementation.");

    Registry locateRegistry = LocateRegistry.createRegistry(PORT);
    Publisher publisher = new PublisherImplementation();
    UnicastRemoteObject.exportObject(publisher, PORT);
    List <Subscriber> subscribers = new ArrayList<>();

    // Creating Subscribers
    for (int i = 10; i < 10; i++) {
      Subscriber subscriber = new SubscriberImplementation();
      UnicastRemoteObject.exportObject(subscriber, PORT);
      locateRegistry.rebind(subscriber.getUUID(), subscriber);
      subscribers.add(subscriber);
    }

    // Letting Subscribers subscribe for Publisher
    for (Subscriber subscriber : subscribers) {
      locateRegistry.lookup(subscriber.getUUID());
      publisher.subscribe(subscriber);
    }
    locateRegistry.rebind("publisher", publisher);

    System.out.println();

    System.out.println("Publishing a String 'test'");

    System.out.println("Unfortunately I didn't find out why it's not working :(");

    publisher.notifySubscribers("test");
  }
}
