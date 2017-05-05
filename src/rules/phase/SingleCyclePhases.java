/**
 * 
 */
package rules.phase;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import rules.generator.ConcreteMoveGenerator;
import rules.generator.MoveGenerator;
import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * Enumeration of various SingleCyclePhase objects.
 * <p>
 * Note that while a Phase usually is not a singleton, by the nature of SingleCyclePhase, there is
 * no state, thus using an enum is acceptable.
 * 
 * @author ngales
 */
enum SingleCyclePhases implements Phase {
  //@formatter:off
  CONFLICT(ConcreteMoveGenerator.STANDARD_CONFLICT), 
  EXPANSION(ConcreteMoveGenerator.STANDARD_EXPANSION), 
  POP_LIMIT(ConcreteMoveGenerator.POP_LIMIT);
  //@formatter:on

  /**
   * Encapsulated phase to which to forward all methods.
   */
  private final SingleCyclePhase wrapped;

  private SingleCyclePhases(MoveGenerator generator) {
    Preconditions.checkNotNull(generator);
    this.wrapped = SingleCyclePhase.getInstance(generator);
  }

  @Override
  public Set<Move> generateMoves(Board gameState) {
    return wrapped.generateMoves(gameState);
  }

  @Override
  public boolean advance() {
    return wrapped.advance();
  }

  @Override
  public boolean retreat() {
    return wrapped.retreat();
  }

  @Override
  public Nation toPlay() {
    return wrapped.toPlay();
  }

  @Override
  public int movesInCycle() {
    return wrapped.movesInCycle();
  }

  @Override
  public boolean atBeginningOfCycle() {
    return wrapped.atBeginningOfCycle();
  }

  @Override
  public boolean setOrder(List<Nation> order) {
    return wrapped.setOrder(order);
  }

  public SingleCyclePhases fromString(String str) {
    // TODO: see essential Java
    throw new UnsupportedOperationException("not yet implemented");
  }
}
