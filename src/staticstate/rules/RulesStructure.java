/**
 * 
 */
package staticstate.rules;

import java.util.List;

import rules.phasegroup.ConcretePhaseGroupType;
import rules.phasegroup.PhaseGroupType;
import state.nation.Nation;

/**
 * Specifies configuration of the rules aspects of a simulation.
 * 
 * @author ngales
 */
public interface RulesStructure {

  public final PhaseGroupType DEFAULT_TYPE = ConcretePhaseGroupType.SIMPLE;

  PhaseGroupType getPhaseGroupType();

  List<Nation> getNations();
}
