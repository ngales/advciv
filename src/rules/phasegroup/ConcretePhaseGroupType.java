/**
 * 
 */
package rules.phasegroup;

import java.util.List;

import com.google.common.collect.Lists;

import rules.phase.ConcretePhaseType;
import rules.phase.PhaseType;
import staticstate.rules.RulesStructure;

/**
 * @author ngales
 * 
 */
public enum ConcretePhaseGroupType implements PhaseGroupType {
  STANDARD() {
    @Override
    public PhaseGroup getInstance(RulesStructure structure) {
      List<PhaseType> phaseTypes =
          Lists.<PhaseType>newArrayList(ConcretePhaseType.EXPANSION, ConcretePhaseType.MOVEMENT,
              ConcretePhaseType.CONFLICT, ConcretePhaseType.POP_LIMIT);

      return StandardPhaseGroup.getInstance(phaseTypes, structure.getNations());
    }
  },
  MOVEMENT_ONLY {
    @Override
    public PhaseGroup getInstance(RulesStructure structure) {
      return SinglePhaseGroup.getInstance(ConcretePhaseType.MOVEMENT, structure.getNations());
    }
  },
  SIMPLE() {
    @Override
    public PhaseGroup getInstance(RulesStructure structure) {
      List<PhaseType> phaseTypes =
          Lists.<PhaseType>newArrayList(ConcretePhaseType.EXPANSION, ConcretePhaseType.MOVEMENT,
              ConcretePhaseType.CONFLICT);

      PhaseGroup g = StandardPhaseGroup.getInstance(phaseTypes, structure.getNations());
      System.out.println(g);
      return g;
    }
  };
}
