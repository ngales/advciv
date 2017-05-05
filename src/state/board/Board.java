/**
 * 
 */
package state.board;

import java.util.Map;
import java.util.Set;

import state.area.Area;
import state.nation.Nation;
import staticstate.state.AreaInfo;
import staticstate.state.OffboardAreaInfo;

/**
 * Encloses all non-static board state of a simulation. Static state is enclosed by a corresponding
 * Structure object; each Board implementation should have a creation method which takes a Structure
 * as specification.
 * 
 * @author ngales
 */
public interface Board {

  /**
   * Default info to use for stock area. Consumers should call getStock() to retrieve a concrete
   * board's area and associated info.
   */
  public static final AreaInfo STOCK = OffboardAreaInfo.STOCK;

  /**
   * Default info to use for treasury area. Consumers should call getStock() to retrieve a concrete
   * board's area and associated info.
   */
  public static final AreaInfo TREASURY = OffboardAreaInfo.TREASURY;

  /**
   * @return true iff the game has ended.
   */
  boolean hasEnded();

  /**
   * @return set of agents in this board.
   */
  Set<Nation> getAgents();

  /**
   * Returns the set of areas present on this board, including offboard areas such as stock and
   * treasury.
   * 
   * @return set of all on-board and off-board areas.
   */
  Set<Area> getAreas();

  /**
   * @return set of all on-board areas.
   */
  Set<Area> getBoardAreas();

  /**
   * @return set of all off-board areas.
   */
  Set<Area> getOffboardAreas();

  /**
   * @return set of areas in which token pieces may be places to start a game.
   */
  Set<Area> getStartAreas();

  /**
   * Returns all areas which are linked via either land or water edges from the given area.
   * 
   * @param area from which to retrieve neighbors.
   * @return set of areas which are directly reachable from given area.
   */
  Set<Area> getAdjacentAreas(Area area);

  /**
   * Returns all areas in which any agent has one or more token pieces present, including both
   * on-board and off-board areas.
   * 
   * @return set of on- or off-board areas in which one or more token pieces exist.
   */
  Set<Area> getTokenAreas();

  /**
   * Returns all areas in which the given agent has one or more token pieces present, including both
   * on-board and off-board areas.
   * 
   * @param agent of whom to retrieve areas.
   * @return set of on- or off-board areas in which token pieces of the given agent exist.
   */
  Set<Area> getTokenAreas(Nation agent);

  /**
   * Returns all areas in which the given agent has one or more token pieces present, not including
   * off-board areas.
   * 
   * @param agent of whom to retrieve areas.
   * @return set of on-board areas in which token pieces of the given agent exist.
   */
  Set<Area> getOnboardTokenAreas(Nation agent);

  /**
   * Returns all of the tokens present in the given area under any agent. Only agents with positive
   * counts will appear in the returned map.
   * 
   * @param area of which to retrieve token piece map.
   * @return map of agents to their positive counts of token pieces in the given area.
   */
  Map<Nation, Integer> getTokens(Area area);

  /**
   * Returns a reference to the area of this board used to store tokens as money.
   * 
   * @return this board's treasury Area.
   */
  Area getTreasury();

  /**
   * Returns a reference to the area of this board used to store unused pieces.
   * 
   * @return this board's stock Area;
   */
  Area getStock();

  boolean moveToken(Nation agent, Area from, Area to);

  boolean moveToken(Nation agent, Area from, Area to, int count);

  boolean moveShip(Nation agent, Area from, Area to);

  boolean moveShip(Nation agent, Area from, Area to, int count);

  boolean moveCity(Nation agent, Area from, Area to);

  // TODO: maybe add functions for returning total count of tokens/ships/cities, total count of
  // tokens/ships/cities on board, and map of agents to both same

  int tokensIn(Area area);

  int tokensIn(Area area, Nation agent);

  int shipsIn(Area area);

  int shipsIn(Area area, Nation agent);
}
