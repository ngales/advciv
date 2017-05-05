/**
 * 
 */
package rules.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Iterables;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import statetransition.CivMove;
import statetransition.ConcreteMoveType;
import statetransition.Move;
import statetransition.Moves;
import staticstate.state.AreaInfo;
import util.StaticUtil;

/**
 * @author ngales
 */
class StandardConflictMoveGenerator implements MoveGenerator {

  private StandardConflictMoveGenerator() {}

  public static StandardConflictMoveGenerator getInstance() {
    return new StandardConflictMoveGenerator();
  }

  @Override
  public Set<Move> generateMoves(Board board, Nation toPlay) {
    List<Move> moves = new ArrayList<Move>();
    Set<Area> areas = board.getBoardAreas();
    for (Area area : areas) {
      AreaInfo info = area.getAreaInfo();
      int popLimit = info.getPopulationLimit();
      int pop = board.tokensIn(area);
      if (pop <= popLimit) {
        continue;
      }
      Map<Nation, Integer> agentCounts = board.getTokens(area);
      // If there is only a single nation in the area, no conflict takes place, population will be
      // reduced in different pop check phase
      if (agentCounts.size() < 2) {
        continue;
      }

      Map<Integer, Set<Nation>> inverseCounts = StaticUtil.invertFrom(agentCounts);

      Map<Nation, Integer> removedCounts = new HashMap<Nation, Integer>(agentCounts);
      for (Nation agent : agentCounts.keySet()) {
        removedCounts.put(agent, 0);
      }

      // Tokens are removed starting from the agents with the lowest tokens, removing tokens
      // simultaneously from each lowest set until pop limit is met, cycling through agent sets
      // repeatedly if necessary
      int popRemoved = 0;
      for (Entry<Integer, Set<Nation>> entry : Iterables.cycle(inverseCounts.entrySet())) {
        Iterator<Nation> it = entry.getValue().iterator();
        while (it.hasNext()) {
          Nation agent = it.next();
          Move move =
              CivMove.getInstance(ConcreteMoveType.TOKEN, board, agent, area, board.getStock());
          moves.add(move);
          removedCounts.put(agent, 1 + removedCounts.get(agent));
          popRemoved++;
          if (removedCounts.get(agent) >= agentCounts.get(agent)) {
            it.remove();
          }
        }

        if (pop - popRemoved <= popLimit) {
          break;
        }
      }

      // TODO: this and above are equivalent, but above use remote on iterator to avoid few extra
      // calls to removed map. this doesn't need modification, it simply skips. which is faster?
      // for (Set<Nation> agents : Iterables.cycle(inverseCounts.values())) {
      // for (Nation agent : agents) {
      // if (removedCounts.get(agent) >= agentCounts.get(agent)) {
      // continue;
      // }
      // Move move =
      // CivMove.getInstance(ConcreteMoveType.TOKEN, board, agent, area, board.getStock());
      // moves.add(move);
      // removedCounts.put(agent, 1 + removedCounts.get(agent));
      // popRemoved++;
      // }
      //
      // if (pop - popRemoved <= popLimit) {
      // break;
      // }
      // }
    }

    return Collections.singleton(Moves.combine(moves, board));
  }
}
