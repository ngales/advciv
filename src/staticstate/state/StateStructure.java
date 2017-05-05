/**
 * 
 */
package staticstate.state;

import java.util.Map;
import java.util.Set;

import state.board.BoardType;
import state.board.ConcreteBoard;
import state.nation.Nation;

/**
 * Specifies configuration of the state aspects of a simulation.
 * 
 * @author ngales
 */
public interface StateStructure {

  public final BoardType DEFAULT_TYPE = ConcreteBoard.STANDARD;

  public static final int DEFAULT_MAX_TOKENS = 52;
  public static final int DEFAULT_MAX_SHIPS = 5;
  public static final int DEFAULT_MAX_CITIES = 9;

  BoardType getBoardType();

  /**
   * Get Set of AreaInfo for each possible area in this board structure.
   * <p>
   * Returned set should include all AreaInfo objects that are used in this structure's
   * AreaAdjacency set.
   * 
   * @return
   */
  Set<AreaInfo> getAreaInfos();

  /**
   * Get AreaAdjacency for each possible area in this board structure.
   * <p>
   * All AreaAdjacency objects in returned set should use only objects that are returned by this
   * structure's AreaInfo set.
   * 
   * @return
   */
  Set<AreaAdjacency> getAreaAdjacencies();

  /**
   * Get number of tokens in the game for each nation.
   * 
   * @return
   */
  Map<Nation, Integer> getMaxTokens();

  /**
   * Get number of ships in the game for each nation.
   * 
   * @return
   */
  Map<Nation, Integer> getMaxShips();

  /**
   * Get number of cities in the game for each nation.
   * 
   * @return
   */
  Map<Nation, Integer> getMaxCities();

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

  Set<Nation> getNations();
}
