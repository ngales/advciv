/**
 * 
 */
package search;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Iterators;

import evaluation.Evaluator;
import evaluation.Evaluators;
import evaluation.ScoreVector;
import rules.phasegroup.PhaseGroup;
import simulation.Engine;
import state.board.Board;
import statetransition.Move;
import util.Constants;

/**
 * @author ngales
 */
public class IterativeDepthFirstSearcher implements Searcher {

  public final Evaluator evaluator;
  public final int maxDepth;

  private Map<ScoreVector, Move> moveMap;

  private IterativeDepthFirstSearcher(Evaluator evaluator, int maxDepth) {
    this.evaluator = evaluator;
    this.maxDepth = maxDepth;
    this.moveMap = new HashMap<ScoreVector, Move>();
  }

  public static IterativeDepthFirstSearcher getInstance(Evaluator evaluator, int maxDepth) {
    return new IterativeDepthFirstSearcher(evaluator, maxDepth);
  }

  @Override
  public Move findBestMove(Board gameState, PhaseGroup rulesState) {
    // copy the engine to avoid modifying its state
    // NOTE: if Engine is modified to make defensive copies of its constructor parameters this will
    // no longer generate Move objects that are applicable to the calling class.
    Engine engine = Engine.getInstance(gameState, rulesState, this);

    // if there is only one move, simply return it. if getMoves() is expensive and/or most searches
    // aren't on single move states, this might not be worth checking for explicitly.
    Set<Move> moves = engine.getMoves();
    if (moves.size() == 1) {
      return Iterators.getOnlyElement(moves.iterator());
    }

    ScoreVector bestScore = depthLimitedSearch(engine, maxDepth);
    System.out.println(moveMap);
    System.out.println("best score found: " + bestScore + "\n" + Constants.TERMINAL_SEPERATOR);
    Move bestMove = moveMap.get(bestScore);
    if (bestMove == null) {
      throw new IllegalStateException("no best move found");
    }
    moveMap.clear();
    return bestMove;
  }

  private ScoreVector depthLimitedSearch(Engine engine, int remainingDepth) {
    if (remainingDepth == 0) {
      System.out.println("SEARCH TERMINAL EVAL: " + evaluator.eval(engine) + "\n"
          + Constants.TERMINAL_SEPERATOR);
      return evaluator.evalVector(engine);
    }

    Set<Move> moves = engine.getMoves();
    ScoreVector bestScoreFound = Evaluators.lowest(engine.getAgents());

    if (moves.size() == 1) {
      // this will be the best move found; this is needed explicitly rather than falling through
      // below to avoid comparisons when state agent is toPlay, since that agent isn't present in
      // evaluator score vectors
      Move bestMove = Iterators.getOnlyElement(moves.iterator());
      bestMove.applyMove();
      bestScoreFound = depthLimitedSearch(engine, remainingDepth - 1);
      bestMove.undoMove();
      moveMap.put(bestScoreFound, bestMove);
      return bestScoreFound;
    }

    for (Move move : moves) {
      if (!move.applyMove()) {
        throw new IllegalStateException("applyMove not successfull");
      }
      System.out.println("SEARCH STATE depth remaining " + remainingDepth + " with toPlay "
          + engine.toPlay() + " after " + move + "\n" + engine);
      ScoreVector moveScore = depthLimitedSearch(engine, remainingDepth - 1);
      if (!move.undoMove()) {
        throw new IllegalStateException("undoMove not successfull");
      }
      // if (maxDepth != remainingDepth && engine.toPlay().equals(engine.stateAgent())) {
      // continue;
      // }

      // System.out.println("comparison : " + moveScore.compareScore(bestScoreFound,
      // engine.toPlay()));
      // if (moveScore.get(engine.toPlay()) > bestScoreFound.get(engine.toPlay())) {
      if (moveScore.compareScore(bestScoreFound, engine.toPlay()) > 0) {
        System.out.println("best move found");
        bestScoreFound = moveScore;
        moveMap.put(moveScore, move);
        // TODO: could implement Observer pattern by having this class extend another, like
        // AbstractSearcher or SkeletonSearcher, and call this method
        // reportBestMove(move);
      }
    }

    return bestScoreFound;
  }
}
