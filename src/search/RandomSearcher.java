/**
 * 
 */
package search;

import java.util.Random;
import java.util.Set;

import rules.phasegroup.PhaseGroup;
import state.board.Board;
import statetransition.Move;

/**
 * @author ngales
 * 
 */
public class RandomSearcher implements Searcher {

  private RandomSearcher() {}

  /**
   * Returns a RandomSearcher in its initial state.
   * 
   * @return
   */
  public static RandomSearcher getInstance() {
    // Could be a static final member of class, since doesn't change
    return new RandomSearcher();
  }

  @Override
  public Move findBestMove(Board gameState, PhaseGroup phaseState) { // MoveGenerator generator) {
    // Set<Move> moves = generator.generateMoves(gameState); //gameState.generateMoves(generator);
    Set<Move> moves = phaseState.generateMoves(gameState);
    int numberMoves = moves.size();

    Random random = new Random(); // Should move to somewhere more static

    // Added constraint that MoveGenerator must return at least one move,
    // no longer needed here
    // if (numberMoves < 1) {
    // // No available moves
    // Move nullMove = NullMove.getInstance(gameState);
    // return PhaseMove.getInstance(nullMove, phaseState);
    // }

    int randomSelection = random.nextInt(numberMoves);
    // Java has no easy way to select from Set.. could implement such.
    // In the meantime, this takes N/2 time on average to complete,
    // and isn't perfect since iteration order of Set isn't specified,
    // which may or may not matter to true randomness.
    int i = 0;
    for (Move move : moves) {
      if (i == randomSelection) {
        return move;
      }
      i++;
    }
    throw new AssertionError("Random selection not in set!");
  }

}
