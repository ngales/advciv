/**
 * 
 */
package staticstate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import rules.phasegroup.PhaseGroupType;
import state.board.BoardType;
import state.nation.Nation;
import staticstate.rules.RulesStructure;
import staticstate.rules.SimpleRulesStructure;
import staticstate.state.AreaAdjacency;
import staticstate.state.AreaInfo;
import staticstate.state.SimpleStateStructure;
import staticstate.state.StateStructure;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Simple class allowing complete specification to create Structure;
 * 
 * @author ngales
 */
class SimpleStructure implements Structure {

  private final StateStructure stateStructure;
  private final RulesStructure rulesStructure;
  private final Set<Nation> agents;

  private SimpleStructure(StateStructure stateStructure, RulesStructure rulesStructure,
      Set<Nation> agents) {
    this.stateStructure = stateStructure;
    this.rulesStructure = rulesStructure;
    this.agents = ImmutableSet.copyOf(agents);
  }

  public static SimpleStructure getInstance(AreaInfo[] areas, AreaAdjacency[] adjacencies,
      Nation[] nations) {
    return getInstance(Sets.newHashSet(areas), Sets.newHashSet(adjacencies),
        Lists.newArrayList(nations));
  }

  public static SimpleStructure getInstance(Set<AreaInfo> areas, Set<AreaAdjacency> adjacencies,
      List<Nation> nations) {
    StateStructure stateStructure = SimpleStateStructure.getInstance(areas, adjacencies, nations);
    RulesStructure rulesStructure = SimpleRulesStructure.getInstance(nations);
    return getInstance(stateStructure, rulesStructure);
  }

  public static SimpleStructure getInstance(BoardType boardType, PhaseGroupType phaseGroupType,
      Set<AreaInfo> areas, Set<AreaAdjacency> adjacencies, List<Nation> nations,
      Map<Nation, Integer> maxTokens, Map<Nation, Integer> maxShips, Map<Nation, Integer> maxCities) {
    StateStructure stateStructure =
        SimpleStateStructure.getInstance(areas, adjacencies, nations, maxTokens, maxShips,
            maxCities);
    RulesStructure rulesStructure = SimpleRulesStructure.getInstance(nations);
    return getInstance(stateStructure, rulesStructure);
  }

  public static SimpleStructure getInstance(StateStructure stateStructure,
      RulesStructure rulesStructure) {
    Preconditions.checkNotNull(stateStructure);
    Preconditions.checkNotNull(rulesStructure);
    Set<Nation> agents = stateStructure.getNations();
    assert (agents != null);
    if (!HashMultiset.create(agents).equals(HashMultiset.create(rulesStructure.getNations()))) {
      throw new IllegalArgumentException("agents of state and rules structures do not match");
    }
    return new SimpleStructure(stateStructure, rulesStructure, agents);
  }

  @Override
  public StateStructure getStateStructure() {
    return stateStructure;
  }

  @Override
  public RulesStructure getRulesStructure() {
    return rulesStructure;
  }

  @Override
  public Set<Nation> getAgents() {
    return agents;
  }
}
