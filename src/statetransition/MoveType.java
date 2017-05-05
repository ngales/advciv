/**
 * 
 */
package statetransition;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;

/**
 * @author ngales
 */
public interface MoveType {
  boolean apply(Board board, Nation agent, Area from, Area to);
}
