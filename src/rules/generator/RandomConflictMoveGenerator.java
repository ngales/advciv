/**
 * 
 */
package rules.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import state.token.Token;
import statetransition.CivMove;
import statetransition.ConcreteMoveType;
import statetransition.Move;
import statetransition.MoveGroup;
import statetransition.NullMove;
import staticstate.state.AreaInfo;

/**
 * @author ngales
 */
class RandomConflictMoveGenerator implements MoveGenerator {
  // TODO: Make this deterministic; consistent/correct searching behavior
  // can't happen while the same set of moves might result in different
  // outcomes. might be able to keep this random, but the sequence of
  // random numbers must be consistent through application/removal of moves,
  // which would be complicated to do.

  private RandomConflictMoveGenerator() {}

  public static RandomConflictMoveGenerator getInstance() {
    return new RandomConflictMoveGenerator();
  }

  @Override
  public Set<Move> generateMoves(Board board, Nation toPlay) {
    // Generate a single MoveGroup, containing all of the moves for
    // this agent move generator rule invariant

    Set<Area> areas = board.getAreas();

    List<Move> moves = new ArrayList<Move>();

    // Enforce population limits
    for (Area area : areas) {
      AreaInfo areaInfo = area.getAreaInfo();
      int areaPopLimit = areaInfo.getPopulationLimit();
      assert (areaPopLimit >= 0);

      List<Token> areaTokens = area.getTokens();
      int areaPop = areaTokens.size();

      // For now randomly choose tokens to remove until area meets pop limit
      // Easiest way to do that is to randomly permute list of tokens,
      // then select difference of pop limit and actual pop (if actual > limit)
      if (areaPop > areaPopLimit) {
        // Copy list for mutation
        List<Token> areaTokensCopy = new ArrayList<Token>(areaTokens);
        Collections.shuffle(areaTokensCopy);

        int numSelectWithoutReplacement = areaPop - areaPopLimit;
        for (int i = 0; i < numSelectWithoutReplacement; i++) {
          Token token = areaTokensCopy.get(i);
          Move removeMove =
              CivMove.getInstance(ConcreteMoveType.TOKEN, board, toPlay, area, board.getStock());
          moves.add(removeMove);
        }
      }
    }

    Move cleanupMove = null;
    if (moves.isEmpty()) {
      cleanupMove = NullMove.getInstance(board);
    } else {
      cleanupMove = MoveGroup.getInstance(moves);
    }

    // Set<Move> moveSet = new LinkedHashSet<Move>(1);
    // moveSet.add(cleanupMove);
    // return Collections.unmodifiableSet(moveSet);

    return Collections.singleton(cleanupMove);
  }
  
}
