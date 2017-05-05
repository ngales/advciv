/**
 * 
 */
package rules.generator;

import java.util.HashSet;
import java.util.Set;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import statetransition.CivMove;
import statetransition.ConcreteMoveType;
import statetransition.Move;

/**
 * @author ngales
 */
class PlacementMoveGenerator implements MoveGenerator {

  private PlacementMoveGenerator() {}

  public static PlacementMoveGenerator getInstance() {
    return new PlacementMoveGenerator();
  }

  @Override
  public Set<Move> generateMoves(Board board, Nation toPlay) {
    // TODO: implement properly
    Set<Area> startingAreas = board.getStartAreas();
    Set<Move> moves = new HashSet<Move>(startingAreas.size());
    for (Area area : startingAreas) {
      Move move =
          CivMove.getInstance(ConcreteMoveType.TOKEN, board, toPlay, board.getStock(), area);
      moves.add(move);
    }
    return moves;
  }

}
