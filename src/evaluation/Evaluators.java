/**
 * 
 */
package evaluation;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import state.nation.Nation;

/**
 * Noninstantiable utility class for working with Evaluator objects and their score vectors.
 * 
 * @author ngales
 */
public class Evaluators {

  // Suppress default constructor for non-instantiability
  private Evaluators() {
    throw new AssertionError();
  }

  /**
   * Creates an immutable score vector corresponding to the minimum possible score for each of the
   * given agents.
   * 
   * @param agents to be included in score vector.
   * @return score vector mapping agents to minimum scores.
   */
  public static ScoreVector lowest(Set<Nation> agents) {
    Map<Nation, Integer> lowest = Maps.toMap(agents, new Function<Nation, Integer>() {
      @Override
      public Integer apply(Nation input) {
        // ignore input
        return Integer.MIN_VALUE;
      }
    });
    return ScoreVector.copyOf(lowest);
  }

}
