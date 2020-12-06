package assignment02;

/**
 * Main class for assignment 2 exercise 1.
 */
public class Exercise01 {

  public static void main(String[] args) {
    Server server = new Server();
    Thread thread = new Thread(server);
    thread.start();
    Client client = new Client();

    System.out.println("Test, if map is empty");
    System.out.println(client.isEmpty());

    System.out.println();

    System.out.println("Add and get KV-pair \"GvS\", \"macht_Spass\"");
    client.put("GvS", "macht_Spass");
    System.out.println(client.get("GvS"));

    System.out.println();

    System.out.println("Test again if it's empty");
    System.out.println(client.isEmpty());


    System.out.println("exit");
    client.exit();
  }
}
