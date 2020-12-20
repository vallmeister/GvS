package assignment03;

import java.security.NoSuchAlgorithmException;

public class Worker implements Runnable{
  private ControllerSubmitter controllerSubmitter;
  private String challenge;
  private String solution;
  private int searchSpace;
  private static int number = 0;

  public Worker(ControllerSubmitter controllerSubmitter, String challenge) {
    this.challenge = challenge;
    this.solution = this.challenge;
    this.controllerSubmitter = controllerSubmitter;
    searchSpace = number;
    number++;
  }
  @Override
  public void run() {
    Util util;
    
    try {
      util = new Util();
      
      while (!util.validate(challenge, solution)) {
        solution = challenge + Exercise01.intToHexstring(
            (int) (16 / ControllerSubmitter.SIZE_OF_THREADPOOL
                * (searchSpace +  Math.random())));
        double digits = 15 * Math.random();
        int upperBound = (int) digits;

        for (int i = 0; i < upperBound; i++) {
          double tmp = 15.9d * Math.random();
          solution += Exercise01.intToHexstring((int) tmp);
          if (util.validate(challenge, solution)) {
          }
        }
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    // give solution to Controller
    controllerSubmitter.sendSolution(solution);
  }
}
