/**
 * 
 */
package rules.generator;

import java.util.Set;

import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * Represents a strategy for generating Move objects. Objects of this interface should be function
 * objects which should not hold state that affects their output.
 * 
 * @author ngales
 */
public interface MoveGenerator {
  /**
   * Generates one or more Move objects corresponding to the given Board and Nation to play. This
   * method will always return one or more moves; a lack of valid moves should be signified by the
   * method returning a "null" Move.
   * 
   * @param gameState
   * @param toPlay
   * @return Set of one or more Move objects.
   */
  Set<Move> generateMoves(Board gameState, Nation toPlay);
}
