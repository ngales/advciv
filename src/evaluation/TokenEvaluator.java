/**
 * 
 */
package evaluation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import rules.phasegroup.PhaseGroup;
import simulation.Engine;
import state.area.Area;
import state.board.Board;
import state.nation.Nation;

/**
 * @author ngales
 */
public class TokenEvaluator implements Evaluator {

  private TokenEvaluator() {}

  public static TokenEvaluator getInstance() {
    return new TokenEvaluator();
  }

  @Override
  public Map<Nation, Integer> eval(Board gameState, PhaseGroup rulesState) {
    Preconditions.checkNotNull(gameState);
    Preconditions.checkNotNull(rulesState);

    Set<Area> areas = gameState.getTokenAreas();
    Set<Nation> agents = gameState.getAgents();
    Multiset<Nation> nationCounts = HashMultiset.create(agents.size());
    for (Area area : areas) {
      if (area.equals(gameState.getStock())) {
        continue;
      }
      for (Nation agent : agents) {
        int count = gameState.tokensIn(area, agent);
        nationCounts.add(agent, count);
      }
    }

    // convert to return type
    Map<Nation, Integer> nationMap = new LinkedHashMap<Nation, Integer>();
    for (Nation agent : agents) {
      if (agent.equals(rulesState.stateAgent())) {
        continue;
      }
      Integer count = nationCounts.count(agent);
      nationMap.put(agent, count);
    }

    return nationMap;
  }

  @Override
  public Map<Nation, Integer> eval(Engine engine) {
    return eval(engine.getState(), engine.getRules());
  }

  @Override
  public ScoreVector evalVector(Engine engine) {
    return ScoreVector.copyOf(eval(engine));
  }
}
