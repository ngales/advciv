/**
 * 
 */
package state.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

import state.area.Area;
import state.area.AreaFactory;
import state.area.AreaType;
import state.nation.Nation;
import staticstate.state.AreaAdjacency;
import staticstate.state.AreaInfo;
import staticstate.state.StateStructure;

/**
 * Represents game board, meaning a snapshot of state.
 * <p>
 * This most likely will include cards, e.g. trade and civ, as well as AST position and treasury,
 * since they are also necessary to form a full state snapshot.
 * <p>
 * Game logic should be held by a different object, e.g. phase rules, etc.
 * 
 * @author ngales
 */
public class StandardBoard extends CivBoard {

  // Tracks whether or not state of board is finalized, i.e. game has finished.
  private boolean hasEnded;

  // TODO: use only AreaInfo, since Area is not needed for state

  private final Set<Area> boardAreas;
  private final Set<Area> offboardAreas;

  // Map from each Area on this board to each adjacent accessible Area
  private final ImmutableSetMultimap<Area, Area> adjacencyMap;

  private final Table<Area, Nation, Integer> tokens;
  private final Table<Area, Nation, Integer> ships;

  private final Set<Nation> agents;

  private StandardBoard(StateStructure structure) {
    // use shared implementation of stock and treasury areas
    super();

    Preconditions.checkNotNull(structure);

    this.hasEnded = false;

    // Create the board's Area objects using each AreaInfo
    Set<AreaInfo> areaInfos = structure.getAreaInfos();
    Map<AreaInfo, Area> areaInfoMap = new HashMap<AreaInfo, Area>(areaInfos.size());
    // use accumulator so that an immutable set can be stored in final boardAreas field
    Set<Area> boardAreasAccum = new HashSet<Area>(areaInfos.size());
    for (AreaInfo areaInfo : areaInfos) {
      Area area = AreaFactory.buildArea(AreaType.NON_TRACKING, areaInfo);
      boardAreasAccum.add(area);
      // add to conversion helper map, allowing mapping from staticstate AreaInfo to state Area
      areaInfoMap.put(areaInfo, area);
    }
    // store board areas from accumulator as immutable set
    boardAreas = ImmutableSet.copyOf(boardAreasAccum);

    // add super class's areas
    offboardAreas = new ImmutableSet.Builder<Area>().add(getStock()).add(getTreasury()).build();

    Set<AreaAdjacency> areaAdjacencies = structure.getAreaAdjacencies();
    // TODO: add method to Structure to retrieve average adjacency count and use here
    int expectedValuesPerKey = 4;
    // use accumulator so that an immutable map can be stored in final adjacencyMap field
    Multimap<Area, Area> adjacencyMapAccum =
        HashMultimap.create(boardAreas.size(), expectedValuesPerKey);
    for (AreaAdjacency areaAdjacency : areaAdjacencies) {
      AreaInfo areaInfo = areaAdjacency.getAreaInfo();
      Area area = areaInfoMap.get(areaInfo);
      // Structure guarantees that all adjacency AreaInfo objects are included in AreaInfo set
      assert (boardAreas.contains(area));
      Set<AreaInfo> adjacentAreaInfos = areaAdjacency.getAdjacencies();
      for (AreaInfo adjacentAreaInfo : adjacentAreaInfos) {
        Area adjacentArea = areaInfoMap.get(adjacentAreaInfo);
        assert (boardAreas.contains(adjacentArea));
        adjacencyMapAccum.put(area, adjacentArea);
      }
    }

    // store board areas from accumulator as immutable map
    adjacencyMap = ImmutableSetMultimap.copyOf(adjacencyMapAccum);

    /*
     * Set<NationInfo> nationInfos = structure.getNationInfos(); agents = new
     * HashSet<Nation>(nationInfos.size()); Map<NationInfo, Nation> nationInfoMap = new
     * HashMap<NationInfo, Nation>(nationInfos.size()); for (NationInfo nationInfo : nationInfos) {
     * Nation agent = StandardNation.getInstance(nationInfo); agents.add(agent); // add to
     * conversion helper map, allowing mapping from staticstate NationInfo to state Nation
     * nationInfoMap.put(nationInfo, agent); }
     */
    agents = ImmutableSet.copyOf(structure.getNations());

    tokens = HashBasedTable.create(boardAreas.size() + offboardAreas.size(), agents.size());
    ships = HashBasedTable.create(boardAreas.size() + offboardAreas.size(), agents.size());

    // Initialize the starting pieces for the board according to the structure
    Map<Nation, Integer> maxTokens = structure.getMaxTokens();
    for (Map.Entry<Nation, Integer> entry : maxTokens.entrySet()) {
      Nation agent = entry.getKey();
      int number = entry.getValue().intValue();
      addTokensToStock(agent, number);
    }

    Map<Nation, Integer> maxShips = structure.getMaxShips();
    for (Map.Entry<Nation, Integer> entry : maxShips.entrySet()) {
      Nation agent = entry.getKey();
      int number = entry.getValue().intValue();
      addShipsToStock(agent, number);
    }
  }

  public static StandardBoard getInstance(StateStructure structure) {
    return new StandardBoard(structure);
  }

  @Override
  public boolean hasEnded() {
    return hasEnded;
  }

  @Override
  public Set<Area> getAreas() {
    // TODO: consider caching this somewhere, it could also be used for slimmer precondition checks
    // below
    return new ImmutableSet.Builder<Area>().addAll(boardAreas).addAll(offboardAreas).build();
  }

  @Override
  public Set<Area> getBoardAreas() {
    return boardAreas;
  }

  @Override
  public Set<Area> getOffboardAreas() {
    return offboardAreas;
  }

  @Override
  public Set<Area> getTokenAreas() {
    return ImmutableSet.copyOf(tokens.rowKeySet());
  }

  @Override
  public Set<Area> getTokenAreas(Nation agent) {
    return ImmutableSet.copyOf(tokens.column(agent).keySet());
  }

  @Override
  public Set<Area> getOnboardTokenAreas(Nation agent) {
    // use a temporary structure to hold areas, to enable removal of offboard areas
    // a list is faster than a set
    List<Area> areas = Lists.newArrayList(tokens.column(agent).keySet());
    areas.removeAll(offboardAreas);
    return ImmutableSet.copyOf(areas);
  }

  /*
   * public Map<Area,Integer> getTokenMap(Nation agent) { return
   * ImmutableMap.copyOf(tokens.column(agent)); }
   */

  @Override
  public Map<Nation, Integer> getTokens(Area area) {
    // TODO: this really is returning a count for each agent, so a different structure might be more
    // appropriate. perhaps a multiset? or a list of entries/pairs? ideally, the returned structure
    // is also in ascending or descending order by count-- can a multiset have order specified by
    // value?
    // NOTE: if enough consumers of this method need a guaranteed iteration order, it would be
    // faster
    // to call the appropriate StaticUtil method instead of ImmutableMap here.
    return ImmutableMap.copyOf(tokens.row(area));
    // return Multisets.copyHighestCountFirst(GuavaUtil.convertToMultiset(tokens.row(area)));
  }

  @Override
  public Set<Area> getAdjacentAreas(Area area) {
    return adjacencyMap.get(area);
  }

  protected void addTokensToStock(Nation agent, int number) {
    Area stock = getStock();
    int count = tokensIn(stock, agent);
    tokens.put(stock, agent, count + number);
  }

  protected void addShipsToStock(Nation agent, int number) {
    Area stock = getStock();
    int count = shipsIn(stock, agent);
    ships.put(stock, agent, count + number);
  }

  protected void addCitiesToStock(Nation agent, int number) {
    // TODO: implement
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public boolean moveToken(Nation agent, Area from, Area to) {
    return moveToken(agent, from, to, 1);
  }

  @Override
  public boolean moveToken(Nation agent, Area from, Area to, int count) {
    checkMovePreconditions(agent, from, to);
    int fromCount = tokensIn(from, agent);
    if (fromCount < count) {
      throw new IllegalStateException("not enough tokens of " + agent + " in " + from + " {"
          + fromCount + "} to move " + count + " to " + to);
      // return false;
    }
    // TODO: is it better to remove, or leave a row at 0? currently removed for token area method
    if (fromCount - count == 0) {
      tokens.remove(from, agent);
    } else {
      tokens.put(from, agent, fromCount - count);
    }
    int toCount = tokensIn(to, agent);
    tokens.put(to, agent, toCount + count);
    return true;
  }

  @Override
  public boolean moveShip(Nation agent, Area from, Area to) {
    return moveShip(agent, from, to, 1);
  }

  @Override
  public boolean moveShip(Nation agent, Area from, Area to, int count) {
    checkMovePreconditions(agent, from, to);
    int fromCount = shipsIn(from, agent);
    if (fromCount < count) {
      throw new IllegalStateException("not enough ships of " + agent + " in " + from + " {"
          + fromCount + "} to move " + count + " to " + to);
      // return false;
    }
    // TODO: is it better to remove, or leave a row at 0? currently removed for token area method
    if (fromCount - count == 0) {
      ships.remove(from, agent);
    } else {
      ships.put(from, agent, fromCount - count);
    }
    int toCount = shipsIn(to, agent);
    ships.put(to, agent, toCount + count);
    return true;
  }

  @Override
  public boolean moveCity(Nation agent, Area from, Area to) {
    checkMovePreconditions(agent, from, to);
    // TODO: implement
    throw new UnsupportedOperationException("not yet implemented");
  }

  private void checkMovePreconditions(Nation agent, Area from, Area to) {
    if (!agents.contains(agent)) {
      throw new IllegalArgumentException("agent " + agent + " is not valid");
    }
    if (!boardAreas.contains(from) && !offboardAreas.contains(from)) {
      throw new IllegalArgumentException("from " + from + " is not valid");
    }
    if (!boardAreas.contains(to) && !offboardAreas.contains(to)) {
      throw new IllegalArgumentException("to " + to + " is not valid");
    }
  }

  @Override
  public int tokensIn(Area area) {
    int totalCount = 0;
    for (Nation agent : agents) {
      totalCount += tokensIn(area, agent);
    }
    return totalCount;
  }

  @Override
  public int tokensIn(Area area, Nation agent) {
    if (!boardAreas.contains(area) && !offboardAreas.contains(area)) {
      throw new IllegalArgumentException("area " + area + " is not valid");
    }
    if (!agents.contains(agent)) {
      throw new IllegalArgumentException("agent " + agent + " is not valid");
    }
    Integer count = tokens.get(area, agent);
    if (count == null) {
      return 0;
    }
    return count.intValue();
  }

  @Override
  public int shipsIn(Area area) {
    int totalCount = 0;
    for (Nation agent : agents) {
      totalCount += shipsIn(area, agent);
    }
    return totalCount;
  }

  @Override
  public int shipsIn(Area area, Nation agent) {
    if (!boardAreas.contains(area) && !offboardAreas.contains(area)) {
      throw new IllegalArgumentException("area " + area + " is not valid");
    }
    if (!agents.contains(agent)) {
      throw new IllegalArgumentException("agent " + agent + " is not valid");
    }
    Integer count = ships.get(area, agent);
    if (count == null) {
      return 0;
    }
    return count.intValue();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof StandardBoard)) {
      return false;
    }
    StandardBoard otherBoard = (StandardBoard) other;

    // as of the current use of this project, can assume that if the boards are being compared, that
    // their static aspects (areas, adjacencies, agents) are equivalent
    // thus, simply check mutable state objects for equivalence
    if (tokens.equals(otherBoard.tokens) && ships.equals(otherBoard.ships)
        && hasEnded == otherBoard.hasEnded) {
      assert (boardAreas.equals(otherBoard.boardAreas)
          && offboardAreas.equals(otherBoard.offboardAreas)
          && adjacencyMap.equals(otherBoard.adjacencyMap) && agents.equals(otherBoard.agents));
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(tokens, ships, hasEnded);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("StandardBoard{");
    // sb.append("Areas: ");
    // sb.append(areas);
    // sb.append("\n\tAdjacency Map: ");
    // sb.append(adjacencyMap);
    sb.append("Tokens: ");
    sb.append(tokens);
    sb.append("}");
    return sb.toString();
  }

  @Override
  public Set<Area> getStartAreas() {
    Set<Area> startAreas = new HashSet<Area>();
    for (Area area : boardAreas) {
      if (area.getAreaInfo().isStartArea()) {
        startAreas.add(area);
      }
    }
    return startAreas;
  }

  @Override
  public Set<Nation> getAgents() {
    return agents;
  }
}
