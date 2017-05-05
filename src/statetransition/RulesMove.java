/**
 * 
 */
package statetransition;

import rules.phasegroup.PhaseGroup;

/**
 * Move which affects rules classes.
 * 
 * @author ngales
 */
public interface RulesMove extends Move {
  PhaseGroup getPhaseGroup();
}
