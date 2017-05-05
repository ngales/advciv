/**
 * 
 */
package rules.generator;

import java.util.Set;

import com.google.common.base.Preconditions;

import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * Enumeration of various MoveGenerator objects.
 * 
 * @author ngales
 */
public enum ConcreteMoveGenerator implements MoveGenerator {
  // TODO: ensure each implemented generator is a singleton, via enum or lazy loading
  //@formatter:off
  PLACEMENT(PlacementMoveGenerator.getInstance()),
  SINGLE_TOKEN_MOVE(SingleTokenMoveMoveGenerator.getInstance()),
  POP_LIMIT(PopulationLimitMoveGenerator.getInstance()), 
  RANDOM_CONFLICT(RandomConflictMoveGenerator.getInstance()),  
  STANDARD_CONFLICT(StandardConflictMoveGenerator.getInstance()), 
  STANDARD_EXPANSION(StandardExpansionMoveGenerator.getInstance());
  //@formatter:on

  /**
   * Encapsulated MoveGenerator to which to forward all methods.
   */
  private final MoveGenerator wrapped;

  ConcreteMoveGenerator(MoveGenerator generator) {
    Preconditions.checkNotNull(generator);
    this.wrapped = generator;
  }

  @Override
  public Set<Move> generateMoves(Board gameState, Nation toPlay) {
    return wrapped.generateMoves(gameState, toPlay);
  }
}
