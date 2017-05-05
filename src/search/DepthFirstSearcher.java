/**
 * 
 */
package search;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import evaluation.Evaluator;
import evaluation.TokenEvaluator;
import rules.phasegroup.PhaseGroup;
import state.board.Board;
import state.nation.Nation;
import statetransition.LogMove;
import statetransition.Move;
import util.Constants;

/**
 * @author ngales
 */
public class DepthFirstSearcher implements Searcher {

  private static final int LOG_LEVEL = 1;

  Map<Map<Nation, Integer>, Move> moveMap;

  private DepthFirstSearcher() {
    moveMap = new LinkedHashMap<Map<Nation, Integer>, Move>();
  }

  public static DepthFirstSearcher getInstance() {
    return new DepthFirstSearcher();
  }

  @Override
  public Move findBestMove(Board gameState, PhaseGroup phaseState) {
    // TODO: there is a bug somewhere here.. find it.

    log(1,
        "DepthFirstSearcher, searching game tree for best move for player " + phaseState.toPlay()
            + "..");

    // TODO: Move search depth to parameter or class variable
    // Search depth is usually multiple of phase cycles
    int phaseCycleLength = phaseState.movesInCycle(); // one full turn
    int depthToSearch = phaseCycleLength * 2;
    Map<Nation, Integer> bestScoreMap = dfs(gameState, phaseState, depthToSearch);
    Move bestMove = moveMap.get(bestScoreMap);
    if (bestMove == null) {
      throw new IllegalStateException("no best move found!");
    }

    log(1, "Move map: " + moveMap);

    moveMap.clear();

    log(1, "Best move for player " + phaseState.toPlay() + " found: " + bestScoreMap);
    log(1, Constants.TERMINAL_SEPERATOR);

    return bestMove;
  }

  private Map<Nation, Integer> dfs(Board gameState, PhaseGroup phaseState, int pliesToGo) {
    assert (pliesToGo >= 0);

    if (pliesToGo == 0) {
      // reached terminal leaf node, get evaluation and return
      Evaluator evaluator = TokenEvaluator.getInstance();
      Map<Nation, Integer> scoreMap = evaluator.eval(gameState, phaseState);
      return scoreMap;
    }

    Set<Move> moves = phaseState.generateMoves(gameState);

    // Added constraint that MoveGenerator must return at least one move,
    // no longer needed here
    // if (numberMoves < 1) {
    // // No available moves
    // Move nullMove = NullMove.getInstance(gameState);
    // return PhaseMove.getInstance(nullMove, phaseState);
    // }

    // DEBUG
    // Record some state info to verify consistency after move branching
    String stateString = gameState.toString() + phaseState.toString();

    Map<Nation, Integer> bestMoveMap = null;
    Integer bestMoveScore = Integer.MIN_VALUE; // or Evaluator min
    Nation toPlay = phaseState.toPlay();

    for (Move move : moves) {
      // wrap in logger for debugging
      Move logMove = null;
      if (LOG_LEVEL >= 3) {
        logMove = LogMove.getInstance(move);
      } else {
        logMove = move;
      }

      if (!logMove.applyMove()) {
        throw new IllegalStateException("applyMove() failed!");
      }

      log(3, "State in pliesToGo " + pliesToGo + " after applying move..\n" + gameState + "\n"
          + phaseState);

      Map<Nation, Integer> moveScoreMap = dfs(gameState, phaseState, pliesToGo - 1);

      log(3, "Score vector for move: " + moveScoreMap);

      log(2, pliesToGo + " {" + toPlay + "}, {" + moveScoreMap + "}");

      // Store in map for retrieval by parent function
      // this will overwrite any other move already stored with the same score vector key
      // meaning, the move returned if this is the best score vector is
      // the most recent (aka last) move stored
      // TODO: make this better..
      moveMap.put(moveScoreMap, move);

      // Track best move for this node and nation
      Integer toPlayScore = moveScoreMap.get(toPlay); // //
      // make sure that bestMoveMap is filled, even if doesn't help toPlay
      if (toPlayScore == null) {
        toPlayScore = Integer.MIN_VALUE + 1;
      } // or Evaluator min
      if (toPlayScore > bestMoveScore) {
        bestMoveMap = moveScoreMap;
        bestMoveScore = toPlayScore;
      }

      if (!logMove.undoMove()) {
        throw new IllegalStateException("undoMove() failed!");
      }

      log(3, "State in pliesToGo " + pliesToGo + " after undoing move..\n" + gameState + "\n"
          + phaseState);
    }

    // DEBUG
    String verifyStateString = gameState.toString() + phaseState.toString();
    assert (stateString.equals(verifyStateString)) : "search altered state..\noriginal: "
        + stateString + "\nafter search: " + verifyStateString;

    return bestMoveMap;
  }

  private static void log(int logLevel, String s) {
    if (logLevel <= LOG_LEVEL) {
      System.out.println(s);
    }
  }
}
