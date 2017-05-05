/**
 * 
 */
package statetransition;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rules.phasegroup.PhaseGroup;
import state.board.Board;

/**
 * Noninstantiable utility class.
 * 
 * @author ngales
 */
public class Moves {

  // Suppress default constructor for non-instantiability
  private Moves() {
    throw new AssertionError();
  }

  /**
   * Returns a Java Collections unmodifiable set view of each PhaseMove, which is created from the
   * pairing of each move in given moveSet and the given phaseGroup.
   * 
   * @param moveSet
   * @param phaseGroup
   * @return
   */
  public static Set<Move> mapToPhaseMoves(Set<Move> moves, PhaseGroup phaseGroup) {
    Set<Move> wrappedMoveSet = new LinkedHashSet<Move>(moves.size());
    for (Move move : moves) {
      Move wrappedMove = PhaseMove.getInstance(move, phaseGroup);
      wrappedMoveSet.add(wrappedMove);
    }
    return Collections.unmodifiableSet(wrappedMoveSet);
  }

  /**
   * Combines the given sequence of moves into a single move, handling the empty sequence
   * appropriately.
   * 
   * @param moves to be combined.
   * @param board receiver of move commands.
   * @return single move corresponding to combined sequence of moves.
   */
  public static Move combine(List<Move> moves, Board board) {
    Move move = null;
    if (moves.isEmpty()) {
      move = NullMove.getInstance(board);
    } else {
      move = MoveGroup.getInstance(moves);
    }
    return move;
  }
}
