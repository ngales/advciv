/**
 * 
 */
package search;

import rules.phasegroup.PhaseGroup;
import state.board.Board;
import statetransition.Move;

/**
 * Represents a search strategy.
 * 
 * @author ngales
 */
public interface Searcher {

  /**
   * Returns the best move found by this searcher according to its search criteria and evaluation
   * strategy.
   * <p>
   * This method should be idempotent, that is, it should not change its passed states during its
   * execution.
   * 
   * @param gameState starting game state.
   * @param phaseState starting rules state.
   * @return best move according to this searcher.
   */
  Move findBestMove(Board gameState, PhaseGroup phaseState);
}
