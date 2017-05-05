/**
 * 
 */
package rules.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * Represents a move by the board agent
 * <p>
 * Implementation of this class via enum is done to guarantee that only a single class instance
 * exists, and is subject to change.
 * 
 * @author ngales
 */
enum PopulationLimitMoveGenerator implements MoveGenerator {
  /**
   * Holds the sole instance of this class. Accesses should be made through getInstance() and not
   * through this member.
   */
  INSTANCE;

  /**
   * Returns the sole instance of this class.
   * <p>
   * Access class instances should be exclusively through this method, as accesses through other
   * means subject to change.
   * 
   * @return sole instance of this class.
   */
  public static PopulationLimitMoveGenerator getInstance() {
    return INSTANCE;
  }

  @Override
  public Set<Move> generateMoves(Board gameState, Nation toPlay) {
    // Generate a single MoveGroup, containing all of the moves for
    // this agent move generator rule invariant

    List<Move> moves = new ArrayList<Move>();

    // Perform population limiting in each area
    Set<Area> areas = gameState.getAreas();
    for (Area area : areas) {
      AreaInfo areaInfo = area.getAreaInfo();
      int areaPopLimit = areaInfo.getPopulationLimit();
      assert (areaPopLimit >= 0);

      List<Token> areaTokens = area.getTokens();
      int areaPop = areaTokens.size();
      assert (areaPop >= 0);

      // If total population meets or is below the area's limit, no limiting
      // takes place
      if (areaPop <= areaPopLimit) {
        continue;
      }

      // Calculate which nations from which to remove tokens

      // Get the total number of tokens for each nation in this area
      // TODO: Could instead use a MultiMap from Google Guava
      Map<Nation, List<Token>> nationTokenMap = new HashMap<Nation, List<Token>>();
      // Multimap<Nation, Token> nationTokenMultimap = ArrayListMultimap.create();
      for (Token token : areaTokens) {
        Nation owner = token.getOwner();
        List<Token> tokenList = nationTokenMap.get(owner);
        if (tokenList == null) {
          tokenList = new ArrayList<Token>();
        }
        tokenList.add(token);
        nationTokenMap.put(owner, tokenList);
      }

      // Remove tokens from the area until meeting limit

      // All tokens should be of the same owner (no conflicts)
      assert (nationTokenMap.size() == 1);

      Nation areaOwner = nationTokenMap.keySet().iterator().next();
      List<Token> tokenList = nationTokenMap.get(areaOwner);

      int numberRemoved = 0;
      while (areaPop - numberRemoved > areaPopLimit) {
        assert (!tokenList.isEmpty());

        // Remove from list so is not removed more than once
        Token removedToken = tokenList.remove(tokenList.size() - 1);

        Move removeMove =
            CivMove.getInstance(ConcreteMoveType.TOKEN, gameState, toPlay, area,
                gameState.getStock());
        moves.add(removeMove);

        numberRemoved++;
      }
    }

    Move agentMove = null;
    if (moves.isEmpty()) {
      agentMove = NullMove.getInstance(gameState);
    } else {
      agentMove = MoveGroup.getInstance(moves);
    }

    // Set<Move> moveSet = new LinkedHashSet<Move>(1);
    // moveSet.add(agentMove);
    // return Collections.unmodifiableSet(moveSet);

    return Collections.singleton(agentMove);
  }
}
