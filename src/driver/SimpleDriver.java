/**
 * 
 */
package driver;

import simulation.Engine;
import simulation.Simulation;

/**
 * @author ngales
 */
public class SimpleDriver {

  private SimpleDriver() {}

  /**
   * @param args
   */
  public static void main(String[] args) {
    // Create and start a new simulation with default arguments
    Engine engine = Engine.getInstance();
    Simulation sim = Simulation.getInstance(engine);
    sim.run();

    System.out.println("SimpleDriver finished!");
  }
}
