/**
 * 
 */
package staticstate.rules;

import java.util.List;

import com.google.common.collect.ImmutableList;

import rules.phasegroup.PhaseGroupType;
import state.nation.Nation;

/**
 * @author ngales
 */
public class SimpleRulesStructure implements RulesStructure {

  private final PhaseGroupType type;
  private final List<Nation> nations;

  private SimpleRulesStructure(PhaseGroupType type, List<Nation> nations) {
    this.type = type;
    this.nations = ImmutableList.copyOf(nations);
  }

  public static SimpleRulesStructure getInstance(List<Nation> nations) {
    return getInstance(DEFAULT_TYPE, nations);
  }

  public static SimpleRulesStructure getInstance(PhaseGroupType type, List<Nation> nations) {
    return new SimpleRulesStructure(type, nations);
  }

  @Override
  public PhaseGroupType getPhaseGroupType() {
    return type;
  }

  @Override
  public List<Nation> getNations() {
    return nations;
  }
}
