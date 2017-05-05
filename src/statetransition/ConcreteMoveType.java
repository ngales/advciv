/**
 * 
 */
package statetransition;

import com.google.common.base.Preconditions;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;

/**
 * @author ngales
 */
public enum ConcreteMoveType implements MoveType {
  TOKEN {
    @Override
    public boolean apply(Board board, Nation agent, Area from, Area to) {
      checkPreconditions(board, agent, from, to);
      return board.moveToken(agent, from, to);
    }
  },
  SHIP {
    @Override
    public boolean apply(Board board, Nation agent, Area from, Area to) {
      checkPreconditions(board, agent, from, to);
      return board.moveShip(agent, from, to);
    }
  },
  CITY {
    @Override
    public boolean apply(Board board, Nation agent, Area from, Area to) {
      checkPreconditions(board, agent, from, to);
      return board.moveCity(agent, from, to);
    }
  };

  private static void checkPreconditions(Board board, Nation agent, Area from, Area to) {
    Preconditions.checkNotNull(board);
    Preconditions.checkNotNull(agent);
    Preconditions.checkNotNull(from);
    Preconditions.checkNotNull(to);
  }
}
