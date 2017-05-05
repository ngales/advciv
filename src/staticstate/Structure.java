/**
 * 
 */
package staticstate;


import java.util.Set;

import state.nation.Nation;
import staticstate.rules.RulesStructure;
import staticstate.state.StateStructure;

/**
 * Represents the physical aspects of a board structure, not including intellectual aspects such as
 * rules and turn orders, unless they are physical parts of the board.
 * 
 * @author ngales
 */
public interface Structure {

  /**
   * Get NationInfo for each possible nation in this board structure.
   * 
   * @return
   */
  // Set<NationInfo> getNationInfos();

  // TODO: is this best?
  // List<Nation> getNations();

  /**
   * Get Set of AreaInfo for each possible area in this board structure.
   * <p>
   * Returned set should include all AreaInfo objects that are used in this structure's
   * AreaAdjacency set.
   * 
   * @return
   */
  // Set<AreaInfo> getAreaInfos();

  /**
   * Get AreaAdjacency for each possible area in this board structure.
   * <p>
   * All AreaAdjacency objects in returned set should use only objects that are returned by this
   * structure's AreaInfo set.
   * 
   * @return
   */
  // Set<AreaAdjacency> getAreaAdjacencies();

  /**
   * Get number of tokens in the game for each nation.
   * 
   * @return
   */
  // Map<NationInfo,Integer> getMaxTokens();

  /**
   * Get number of ships in the game for each nation.
   * 
   * @return
   */
  // Map<NationInfo,Integer> getMaxShips();

  /**
   * Get number of cities in the game for each nation.
   * 
   * @return
   */
  // Map<NationInfo,Integer> getMaxCities();

  /**
   * Get Set of AreaInfo in which to start a simulation.
   * 
   * @return
   */
  // Set<AreaInfo> getStartingAreaInfos();

  /**
   * Get mapping of nations to areas.
   * 
   * @return
   */
  // Map<NationInfo,AreaInfo> getStartingMap();

  StateStructure getStateStructure();

  RulesStructure getRulesStructure();

  /**
   * Returns immutable set consisting of this structure's agents.
   * 
   * @return set of agents.
   */
  Set<Nation> getAgents();
}
