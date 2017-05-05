/**
 * 
 */
package statetransition;

import com.google.common.base.Preconditions;

import rules.phasegroup.PhaseGroup;

/**
 * Decorator class that modifies PhaseGroup state on top of wrapped Move effects.
 * 
 * @author ngales
 */
public class PhaseMove implements RulesMove {

  private final Move move;
  private final PhaseGroup phaseGroup;

  private PhaseMove(Move move, PhaseGroup phaseGroup) {
    Preconditions.checkNotNull(move);
    Preconditions.checkNotNull(phaseGroup);
    this.move = move;
    this.phaseGroup = phaseGroup;
  }

  public static PhaseMove getInstance(Move move, PhaseGroup phaseGroup) {
    return new PhaseMove(move, phaseGroup);
  }

  public PhaseGroup getPhaseGroup() {
    return phaseGroup;
  }

  @Override
  public boolean applyMove() {
    // TODO: Should this be all or nothing? Advances regardless of moveSuccess currently..
    boolean moveSuccess = move.applyMove();
    phaseGroup.advance(); // needs to be done after move
    return moveSuccess;
  }

  @Override
  public boolean undoMove() {
    // TODO: Should this be all or nothing? Retreats regardless of moveSuccess currently..
    phaseGroup.retreat(); // needs to be done before move
    return move.undoMove();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PhaseMove{");
    sb.append(phaseGroup);
    sb.append(", ");
    sb.append(move);
    sb.append("}");
    return sb.toString();
  }
}
