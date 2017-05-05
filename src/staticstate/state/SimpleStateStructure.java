/**
 * 
 */
package staticstate.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import state.board.BoardType;
import state.nation.Nation;

/**
 * @author ngales
 */
public class SimpleStateStructure implements StateStructure {

  private final BoardType type;

  private final Set<AreaInfo> areas;
  private final Set<AreaAdjacency> adjacencies;
  private final Set<Nation> nations;

  private final Map<Nation, Integer> maxTokens;
  private final Map<Nation, Integer> maxShips;
  private final Map<Nation, Integer> maxCities;

  private SimpleStateStructure(BoardType type, Set<AreaInfo> areas, Set<AreaAdjacency> adjacencies,
      Collection<Nation> nations, Map<Nation, Integer> maxTokens, Map<Nation, Integer> maxShips,
      Map<Nation, Integer> maxCities) {
    this.type = type;
    this.areas = ImmutableSet.copyOf(areas);
    this.adjacencies = ImmutableSet.copyOf(adjacencies);
    this.nations = ImmutableSet.copyOf(nations);
    this.maxTokens = ImmutableMap.copyOf(maxTokens);
    this.maxShips = ImmutableMap.copyOf(maxShips);
    this.maxCities = ImmutableMap.copyOf(maxCities);
  }

  public static SimpleStateStructure getInstance(Set<AreaInfo> areas,
      Set<AreaAdjacency> adjacencies, Collection<Nation> nations) {
    Map<Nation, Integer> maxTokens = fillMap(nations, StateStructure.DEFAULT_MAX_TOKENS);
    Map<Nation, Integer> maxShips = fillMap(nations, StateStructure.DEFAULT_MAX_SHIPS);
    Map<Nation, Integer> maxCities = fillMap(nations, StateStructure.DEFAULT_MAX_CITIES);
    return getInstance(DEFAULT_TYPE, areas, adjacencies, nations, maxTokens, maxShips, maxCities);
  }

  public static SimpleStateStructure getInstance(Set<AreaInfo> areas,
      Set<AreaAdjacency> adjacencies, Collection<Nation> nations, Map<Nation, Integer> maxTokens,
      Map<Nation, Integer> maxShips, Map<Nation, Integer> maxCities) {
    return getInstance(DEFAULT_TYPE, areas, adjacencies, nations, maxTokens, maxShips, maxCities);
  }

  public static SimpleStateStructure getInstance(BoardType type, Set<AreaInfo> areas,
      Set<AreaAdjacency> adjacencies, Collection<Nation> nations, Map<Nation, Integer> maxTokens,
      Map<Nation, Integer> maxShips, Map<Nation, Integer> maxCities) {
    return new SimpleStateStructure(type, areas, adjacencies, nations, maxTokens, maxShips,
        maxCities);
  }

  private static Map<Nation, Integer> fillMap(Collection<Nation> nations, int max) {
    Map<Nation, Integer> maxTokens = new HashMap<Nation, Integer>(nations.size());
    for (Nation nation : nations) {
      maxTokens.put(nation, max);
    }
    return maxTokens;
  }

  @Override
  public BoardType getBoardType() {
    return type;
  }

  @Override
  public Set<AreaInfo> getAreaInfos() {
    return areas;
  }

  @Override
  public Set<AreaAdjacency> getAreaAdjacencies() {
    return adjacencies;
  }

  @Override
  public Map<Nation, Integer> getMaxTokens() {
    return maxTokens;
  }

  @Override
  public Map<Nation, Integer> getMaxShips() {
    return maxShips;
  }

  @Override
  public Map<Nation, Integer> getMaxCities() {
    return maxCities;
  }

  @Override
  public Set<Nation> getNations() {
    return nations;
  }
}
