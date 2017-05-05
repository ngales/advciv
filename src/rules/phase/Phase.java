/**
 * 
 */
package rules.phase;

import java.util.List;
import java.util.Set;

import state.board.Board;
import state.nation.Nation;
import state.nation.StandardNation;
import statetransition.Move;
import staticstate.GameAgentNations;

/**
 * State representation of a complete game "phase" in terms of a sequence of one or more agent
 * transitions, and not including any physical "board" state. Transitions within an agent ordering
 * are handled by moves, which can be obtained via the appropriate method from the current agent.
 * <p>
 * The sequence of agents in a phase is not guaranteed to remain constant, and may change. These
 * changes will not occur during the middle of a sequence.
 * <p>
 * A Phase will typically act as the rules component in a PhaseGroup, and not be directly accessed
 * by a consumer.
 * 
 * @author ngales
 */
public interface Phase {
  /**
   * Nation representing the rules of the game itself.
   */
  public static final Nation STATE_AGENT = StandardNation.getInstance(GameAgentNations.RULES_AGENT);

  /**
   * Generates the next set of possible, legal moves, according to this Phase's rules.
   * 
   * @param gameState state from which to generate moves.
   * @return set of all legal moves from given state.
   */
  Set<Move> generateMoves(Board gameState);

  /**
   * Advance the state of this rules state phase.
   * <p>
   * This method should generally not be called explicitly by this phase's consumer, but instead by
   * generated move transitions.
   * 
   * @return true iff this phase's state was moved forward.
   */
  boolean advance();

  /**
   * Retreat the state of this rules state phase.
   * <p>
   * This method should generally not be called explicitly by this phase's consumer, but instead by
   * generated move transitions.
   * 
   * @return true iff this phase's state was moved backward.
   */
  boolean retreat();

  /**
   * Returns the agent corresponding to the next transition of this phase.
   * 
   * @return agent who is next in terms of the current rules state.
   */
  Nation toPlay();

  /*
   * Phase contains a cycle consisting of Nation. PhaseGroup contains a cycle consisting of Phase.
   * 
   * Phase needs to signal when its cycle ends, so the enclosing PhaseGroup can advance its own
   * cycle.
   * 
   * Phase also needs a way to advance its cycle.
   * 
   * PhaseGroup needs same as above as Phase.
   * 
   * Advancing could be done either by explicit method, or by adding a second receiver to generated
   * moves that acts on Phase/PhaseGroup. Method would be called on PhaseGroup, then passed to
   * Phase, then Phase cycle would be checked to advance PhaseGroup cycle. Command pattern would
   * mean PhaseGroup would receive command, then do same. Command pattern would be easier, since
   * whatever encloses PhaseGroup and applies Moves wouldn't have to know to call other methods.
   * 
   * So, need to add PhaseGroup to Move. Means Phase should take additional parameter to
   * generateMoves, (this) i.e. the PhaseGroup that calls the Phase generateMoves() method. Phase
   * should then pass that to MoveGenerator, which should take the same addition. Finally, the
   * generator should pass that same to the move, and the move should encode the call to the
   * PhaseGroup's advance() method (which needs to be added as well).
   * 
   * Or could just have boolean return value of Phase advance signal whether wrapped, and similarly
   * for PhaseGroup. Will those ever fail? If so, could just throw exception..
   */

  /**
   * Note: The number returned by this method is not guaranteed to remain static, and may change
   * depending on past (or future) transitions.
   * 
   * @return number of transitions needed to complete one full cycle, e.g. "phase".
   */
  int movesInCycle();

  /**
   * This method will always return true if the cycle consists of a single agent.
   * 
   * @return true iff the phase is at the beginning of its cycle.
   */
  boolean atBeginningOfCycle();

  /**
   * Sets this phase to the given ordering of agents. Order must contain one or more agents.
   * 
   * @param order new ordering of phase.
   * @return true iff order was successfully set.
   * @throws UnsupportedOperationException if this phase does not support order changes.
   * @throws IllegalStateException if order is set when phase is not at beginning of cycle.
   */
  boolean setOrder(List<Nation> order);
}
