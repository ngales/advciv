/**
 * 
 */
package rules.phasegroup;

import java.util.Set;

import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * State representation of a complete game "turn" in terms of a sequence of rules components, and
 * not including any physical "board" state. Transitions between components are handled by moves,
 * which can be obtained via the appropriate methods from the current component.
 * <p>
 * The sequence of rules components in a group will normally remain constant, and not change between
 * cycles. However, the sequence within rules components is not guaranteed to remain constant, and
 * may change. Thus, the overall sequence of transitions may change within a group. These changes
 * will not occur during the middle of a rules component sequence.
 * 
 * @author ngales
 */
public interface PhaseGroup {

  /**
   * Generates the set of possible, legal transitions from the current rules component.
   * 
   * @param state board from which to generate moves.
   * @return set of moves from the current rules component and given board state.
   */
  Set<Move> generateMoves(Board state);

  /**
   * Advance the state of this rules state group.
   * <p>
   * This method should generally not be called explicitly by this group's consumer, but instead by
   * generated move transitions.
   * 
   * @return true iff this group's state was moved forward.
   */
  boolean advance();

  /**
   * Retreat the state of this rules state group.
   * <p>
   * This method should generally not be called explicitly by this group's consumer, but instead by
   * generated move transitions.
   * 
   * @return true iff this group's state was moved backward.
   */
  boolean retreat();

  /**
   * Returns the agent corresponding to the next transition of this group.
   * 
   * @return agent who is next in terms of the current rules state.
   */
  Nation toPlay();

  /**
   * Returns the agent corresponding to nature for transitions that involve no other game agents.
   * 
   * @return the rules agent for this group.
   */
  Nation stateAgent();

  /**
   * Note: The number returned by this method is not guaranteed to remain static, and may change
   * depending on past (or future) transitions.
   * 
   * @return number of transitions needed to complete one full cycle, e.g. "turn".
   */
  int movesInCycle();
}
