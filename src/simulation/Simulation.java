/**
 * 
 */
package simulation;

import com.google.common.base.Preconditions;

import statetransition.Move;
import util.Constants;

/**
 * Runs a game engine to completion.
 * 
 * @author ngales
 */
public class Simulation {

  private Engine engine;

  private Simulation(Engine engine) {
    Preconditions.checkNotNull(engine);
    this.engine = engine;
  }
  
  public static Simulation getInstance(Engine engine) {
    return new Simulation(engine);
  }

  /**
   * Advances the state of the engine repeatedly until the engine is stopped or is unable to apply a
   * move.
   */
  public void run() {
    System.out.println("running simulation..");
    System.out.println(Constants.TERMINAL_SEPERATOR);
    while (advance()) {
    }
    System.out.println("simulation complete");
  }

  /**
   * Advance the state of the engine once. Returns true iff state was successfully advanced and is
   * not stopped.
   * 
   * @return
   */
  private boolean advance() {
    if (engine.stopped()) {
      return false;
    }

    Move move = engine.findBestMove();
    return engine.applyMove(move);
  }

  @Override
  public String toString() {
    return "Simulation{Engine: " + engine + "}";
  }

}
