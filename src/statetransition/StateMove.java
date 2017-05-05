/**
 * 
 */
package statetransition;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;

/**
 * Move which affects state classes.
 * 
 * @author ngales
 */
public interface StateMove extends Move {
  Board getBoard();

  Nation getAgent();

  MoveType getType();

  Area getFrom();

  Area getTo();
}
