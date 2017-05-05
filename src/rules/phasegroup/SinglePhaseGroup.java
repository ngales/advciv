/**
 * 
 */
package rules.phasegroup;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import rules.phase.Phase;
import rules.phase.PhaseType;
import state.board.Board;
import state.nation.Nation;
import statetransition.Move;
import statetransition.Moves;

/**
 * Represents a PhaseGroup with a single Phase.
 * 
 * @author ngales
 */
class SinglePhaseGroup implements PhaseGroup {

  private Phase phase;

  private SinglePhaseGroup(PhaseType type, List<Nation> nations) {
    Preconditions.checkNotNull(type);
    Phase phase = type.getInstance(nations);
    if (phase == null) {
      throw new IllegalStateException("phase of type " + type + " cannot be null");
    }
    this.phase = phase;
  }

  public static SinglePhaseGroup getInstance(PhaseType type, List<Nation> nations) {
    return new SinglePhaseGroup(type, nations);
  }

  @Override
  public Set<Move> generateMoves(Board gameState) {
    Set<Move> moveSet = phase.generateMoves(gameState);
    return Moves.mapToPhaseMoves(moveSet, this);
  }

  @Override
  public boolean advance() {
    // System.out.println("Advancing PhaseGroup.. current Phase: " + phase);
    // Only one phase, so no advancing in phase list..
    // Send advance to composed phase
    // only one phase in phasegroup, always cycles when phase cycles
    return phase.advance();
  }

  @Override
  public boolean retreat() {
    // Only one phase, so no decreasing in phase list..
    // Send retreat to composed phase
    // only one phase in phasegroup, always cycles when phase cycles
    return phase.retreat();
  }

  @Override
  public Nation toPlay() {
    return phase.toPlay();
  }

  @Override
  public Nation stateAgent() {
    return phase.STATE_AGENT;
  }

  @Override
  public int movesInCycle() {
    return phase.movesInCycle();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("SinglePhaseGroup{");
    sb.append(phase);
    sb.append("}");
    return sb.toString();
  }

}
