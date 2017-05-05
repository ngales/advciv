/**
 * 
 */
package evaluation;

import java.util.Map;

import rules.phasegroup.PhaseGroup;
import simulation.Engine;
import state.board.Board;
import state.nation.Nation;

/**
 * @author ngales
 */
public interface Evaluator {
  /**
   * Evaluates a board, assigning it a score based on how much the given nation to play is winning.
   * 
   * @param gameState the state of the game to evaluate.
   * @param phaseState
   * 
   * @return the value of the specified board.
   */
  public Map<Nation, Integer> eval(Board gameState, PhaseGroup phaseState);

  /**
   * Evaluates a board, assigning it a score based on how much the given nation to play is winning.
   * 
   * @param engine the state of the game to evaluate.
   * 
   * @return the value of the specified board.
   */
  public Map<Nation, Integer> eval(Engine engine);

  public ScoreVector evalVector(Engine engine);
}
