/**
 * 
 */
package rules.phase;

import java.util.List;

import rules.generator.ConcreteMoveGenerator;
import state.nation.Nation;

/**
 * Enumeration of different Phase objects available. Instances returned may or may not be unique.
 * 
 * @author ngales
 */
public enum ConcretePhaseType implements PhaseType {
  PLACEMENT {
    @Override
    public Phase getInstance(List<Nation> nations) {
      return MultiCyclePhase.getInstance(nations, ConcreteMoveGenerator.PLACEMENT);
    }
  },
  MOVEMENT {
    @Override
    public Phase getInstance(List<Nation> nations) {
      return MultiCyclePhase.getInstance(nations, ConcreteMoveGenerator.SINGLE_TOKEN_MOVE);
    }
  },
  CONFLICT {
    @Override
    public Phase getInstance(List<Nation> nations) {
      return SingleCyclePhases.CONFLICT;
    }
  },
  EXPANSION {
    @Override
    public Phase getInstance(List<Nation> nations) {
      return SingleCyclePhases.EXPANSION;
    }
  },
  POP_LIMIT {
    @Override
    public Phase getInstance(List<Nation> nations) {
      return SingleCyclePhases.POP_LIMIT;
    }
  };
}
