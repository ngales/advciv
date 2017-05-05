/**
 * 
 */
package rules.phasegroup;

import java.util.ArrayList;
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
 * Represents a PhaseGroup with one or more Phases.
 * 
 * @author ngales
 */
class StandardPhaseGroup implements PhaseGroup {

  private List<Phase> phases;
  private int phasesIndex;

  private StandardPhaseGroup(List<PhaseType> phaseTypes, List<Nation> nations) {
    Preconditions.checkNotNull(phaseTypes);
    if (phaseTypes.isEmpty()) {
      throw new IllegalArgumentException("phaseTypes cannot be empty");
    }
    Preconditions.checkNotNull(nations);
    this.phases = new ArrayList<Phase>(phaseTypes.size());
    for (PhaseType type : phaseTypes) {
      Phase phase = type.getInstance(nations);
      if (phase == null) {
        throw new IllegalStateException("phase of type " + type + " cannot be null");
      }
      phases.add(phase);
    }
    this.phasesIndex = 0;
  }

  public static StandardPhaseGroup getInstance(List<PhaseType> phaseTypes, List<Nation> nations) {
    return new StandardPhaseGroup(phaseTypes, nations);
  }

  private Phase getCurrentPhase() {
    return phases.get(phasesIndex);
  }

  @Override
  public Set<Move> generateMoves(Board gameState) {
    Set<Move> moveSet = getCurrentPhase().generateMoves(gameState);
    return Moves.mapToPhaseMoves(moveSet, this);
  }

  @Override
  public boolean advance() {
    // send advance to phase, record if wrapped from end of phase cycle
    boolean endOfPhaseCycle = getCurrentPhase().advance();
    if (endOfPhaseCycle) { // advance PhaseGroup pointer
      phasesIndex++;
      if (phasesIndex > phases.size() - 1) {
        phasesIndex = 0;
        // System.out.println("Advancing phase group index from " + oldIndex +" to " + phasesIndex);
        return true;
      }
    }
    // System.out.println("Advancing phase group index from " + oldIndex +" to " + phasesIndex);
    return false;
  }

  @Override
  public boolean retreat() {
    // System.out.println("Retreating phase group called..");
    // int oldIndex = phasesIndex;

    Phase currentPhase = getCurrentPhase();
    // retreat current phase, just to see if at the beginning of its cycle
    // TODO: better, more explicit and safer way of checking
    boolean atBeginningOfPhase = currentPhase.retreat();
    currentPhase.advance(); // set back to correct state

    if (atBeginningOfPhase) { // handle phase group index change
      phasesIndex--;
      boolean phaseGroupCycled = false;

      // check if phase group cycled
      if (phasesIndex < 0) {
        phasesIndex = phases.size() - 1;
        phaseGroupCycled = true;
      }

      Phase newCurrentPhase = phases.get(phasesIndex);
      // new current phase was presumably previous cycled to beginning,
      // cycle backwards
      boolean cycledPhase = newCurrentPhase.retreat();
      assert (cycledPhase);
      // System.out.println("Retreating phase group index from " + oldIndex +" to " + phasesIndex);
      return phaseGroupCycled;
    }

    // simply retreat phase
    // no need to check return, as already was checked above
    currentPhase.retreat();
    // System.out.println("Retreating phase group index from " + oldIndex +" to " + phasesIndex);
    return false;
  }

  @Override
  public Nation toPlay() {
    return getCurrentPhase().toPlay();
  }

  @Override
  public Nation stateAgent() {
    return Phase.STATE_AGENT;
  }

  @Override
  public int movesInCycle() {
    int phaseSum = 0;
    for (Phase phase : phases) {
      phaseSum += phase.movesInCycle();
    }
    return phaseSum;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("StandardPhaseGroup{");
    sb.append(getCurrentPhase());
    sb.append("}");
    return sb.toString();
  }
}
