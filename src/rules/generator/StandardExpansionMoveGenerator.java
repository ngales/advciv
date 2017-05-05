/**
 * 
 */
package rules.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import statetransition.CivMove;
import statetransition.ConcreteMoveType;
import statetransition.Move;
import statetransition.Moves;

/**
 * Represents the rules phase transitions that may occur during an Expansion phase. This generator
 * will bound the maximum expansion on a per area basis. This generator also bound whether there are
 * enough tokens in an agent's pool to fulfill all issued moves.
 * 
 * @author ngales
 */
class StandardExpansionMoveGenerator implements MoveGenerator {

  public static final int MAX_EXPANSION = 2; // Integer.MAX_VALUE;

  private StandardExpansionMoveGenerator() {}

  public static StandardExpansionMoveGenerator getInstance() {
    return new StandardExpansionMoveGenerator();
  }

  @Override
  public Set<Move> generateMoves(Board board, Nation toPlay) {
    List<Move> moves = new ArrayList<Move>();
    Set<Area> areas = board.getBoardAreas();
    for (Area area : areas) {
      Map<Nation, Integer> nationCounts = board.getTokens(area);
      for (Entry<Nation, Integer> entry : nationCounts.entrySet()) {
        for (int i = 0; i < entry.getValue() && i < MAX_EXPANSION; i++) {
          Move move =
              CivMove.getInstance(ConcreteMoveType.TOKEN, board, entry.getKey(), board.getStock(),
                  area);
          moves.add(move);
        }
      }
    }

    return Collections.singleton(Moves.combine(moves, board));
  }
}
