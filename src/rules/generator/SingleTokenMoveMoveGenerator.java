/**
 * 
 */
package rules.generator;

import java.util.LinkedHashSet;
import java.util.Set;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import statetransition.CivMove;
import statetransition.ConcreteMoveType;
import statetransition.Move;
import statetransition.NullMove;

/**
 * @author ngales
 */
class SingleTokenMoveMoveGenerator implements MoveGenerator {

  private SingleTokenMoveMoveGenerator() {}

  public static SingleTokenMoveMoveGenerator getInstance() {
    return new SingleTokenMoveMoveGenerator();
  }

  @Override
  public Set<Move> generateMoves(Board board, Nation toPlay) {
    Set<Move> moves = new LinkedHashSet<Move>();

    // always add null move
    moves.add(NullMove.getInstance(board));

    // add all single moves from each area with a token of toPlay agent
    Set<Area> areas = board.getOnboardTokenAreas(toPlay);
    for (Area area : areas) {
      assert (board.tokensIn(area, toPlay) > 0);
      Set<Area> adjacencies = board.getAdjacentAreas(area);
      for (Area adjacency : adjacencies) {
        Move move = CivMove.getInstance(ConcreteMoveType.TOKEN, board, toPlay, area, adjacency);
        moves.add(move);
      }
    }

    return moves;
  }
}
