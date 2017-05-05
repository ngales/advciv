/**
 * 
 */
package rules.phasegroup;

import staticstate.rules.RulesStructure;

/**
 * Represents a type of sequence of phases.
 * 
 * @author ngales
 */
public interface PhaseGroupType {
  PhaseGroup getInstance(RulesStructure structure);
}
