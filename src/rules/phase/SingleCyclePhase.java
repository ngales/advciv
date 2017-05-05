/**
 * 
 */
package rules.phase;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import rules.generator.MoveGenerator;
import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * Skeleton for a Phase in which there is a cycle size of 1. These types of phases usually generate
 * moves in which there are no player driven changes to the board, but rather the rules are
 * enforced, e.g. "nature" is always the player to play. These phases should not hold state,
 * including any members.
 * 
 * @author ngales
 */
class SingleCyclePhase implements Phase {

  private MoveGenerator generator;

  private SingleCyclePhase(MoveGenerator generator) {
    Preconditions.checkNotNull(generator);
    this.generator = generator;
  }

  public static SingleCyclePhase getInstance(MoveGenerator generator) {
    return new SingleCyclePhase(generator);
  }

  @Override
  public Set<Move> generateMoves(Board gameState) {
    Set<Move> moveSet = generator.generateMoves(gameState, toPlay());
    return moveSet;
  }

  @Override
  public boolean advance() {
    // Only one activity in phase, always cycles
    return true;
  }

  @Override
  public boolean retreat() {
    // Only one activity in phase, always cycles
    return true;
  }

  @Override
  public Nation toPlay() {
    return STATE_AGENT;
  }

  @Override
  public int movesInCycle() {
    return 1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("SingleCyclePhase{");
    sb.append(generator);
    sb.append(", ");
    sb.append(toPlay());
    sb.append("}");
    return sb.toString();
  }

  @Override
  public boolean atBeginningOfCycle() {
    return true;
  }

  @Override
  public boolean setOrder(List<Nation> order) {
    throw new UnsupportedOperationException("single cycle phases do not support order changes");
  }
}
