/**
 * 
 */
package search;

import java.util.Map;
import java.util.Set;

import evaluation.Evaluator;
import evaluation.TokenEvaluator;
import rules.phase.Phase;
import rules.phasegroup.PhaseGroup;
import state.board.Board;
import state.nation.Nation;
import statetransition.LogMove;
import statetransition.Move;

/**
 * @author ngales
 */
public class SingleDepthSearcher implements Searcher {

  private SingleDepthSearcher() {}

  public static SingleDepthSearcher getInstance() {
    return new SingleDepthSearcher();
  }

  @Override
  public Move findBestMove(Board gameState, PhaseGroup phaseState) {
    return singleDepthSearch(gameState, phaseState);
  }

  private Move singleDepthSearch(Board gameState, PhaseGroup phaseState) {
    Set<Move> moves = phaseState.generateMoves(gameState);
    int numberMoves = moves.size();

    // Check for next move being game agent move (rules move)
    // Currently just do depth == 1, so no further processing
    if (phaseState.toPlay() == Phase.STATE_AGENT) {
      assert (numberMoves == 1);
      return moves.iterator().next();
    }

    // Added constraint that MoveGenerator must return at least one move,
    // no longer needed here
    // if (numberMoves < 1) {
    // // No available moves
    // Move nullMove = NullMove.getInstance(gameState);
    // return PhaseMove.getInstance(nullMove, phaseState);
    // }

    Evaluator evaluator = TokenEvaluator.getInstance();

    Nation toPlay = phaseState.toPlay();

    int bestMoveScore = Integer.MIN_VALUE; // or Evaluator's min
    Move bestMove = null;
    for (Move move : moves) {
      // For now, just depth == 1 (not even, really, since isn't full game turn..)

      Move logMove = LogMove.getInstance(move);

      if (!logMove.applyMove()) {
        throw new IllegalStateException("applyMove() failed!");
      }

      Map<Nation, Integer> scoreMap = evaluator.eval(gameState, phaseState);

      Integer moveScore = scoreMap.get(toPlay);

      System.out.println(toPlay + " score " + moveScore);

      // make sure that bestMoveMap is filled, even if doesn't help toPlay
      if (moveScore == null) {
        moveScore = Integer.MIN_VALUE + 1;
      } // or Evaluator's min

      if (moveScore > bestMoveScore) {
        bestMoveScore = moveScore;
        bestMove = move;
      }

      if (!logMove.undoMove()) {
        throw new IllegalStateException("undoMove() failed!");
      }
    }

    if (bestMove == null) {
      throw new IllegalStateException("best move not found from search!");
    }

    return bestMove;
  }

}
