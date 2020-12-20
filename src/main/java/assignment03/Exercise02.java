package assignment03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exercise02 {

  public static void main(String[] args) {
    ControllerSubmitter controllerSubmitter = ControllerSubmitter.ControllerSubmitterFactory();
    BufferedReader bufferedReader;


    while (true) {
      System.out.println("Press e for exit. Otherwise it will continue with next challenge");
      try {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        if (bufferedReader.readLine().equals("e")) {
          break;
        }
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      System.out.println("Current challenge is: " + controllerSubmitter.getCurrentChallenge());
      controllerSubmitter.computeSolution();
    }
  }
}
