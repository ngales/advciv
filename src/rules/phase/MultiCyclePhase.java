/**
 * 
 */
package rules.phase;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import rules.generator.MoveGenerator;
import state.board.Board;
import state.nation.Nation;
import statetransition.Move;

/**
 * @author ngales
 */
class MultiCyclePhase implements Phase {

  private List<Nation> nationOrder;
  private int nationOrderIndex;
  private MoveGenerator generator;

  private MultiCyclePhase(List<Nation> order, MoveGenerator generator) {
    Preconditions.checkNotNull(order);
    if (order.isEmpty()) {
      throw new IllegalArgumentException("order cannot be empty");
    }
    Preconditions.checkNotNull(generator);
    this.nationOrder = ImmutableList.copyOf(order);
    this.nationOrderIndex = 0;
    this.generator = generator;
  }

  public static MultiCyclePhase getInstance(List<Nation> order, MoveGenerator generator) {
    return new MultiCyclePhase(order, generator);
  }

  @Override
  public Set<Move> generateMoves(Board board) {
    return generator.generateMoves(board, toPlay());
  }

  @Override
  public boolean advance() {
    nationOrderIndex++;
    if (nationOrderIndex > nationOrder.size() - 1) {
      nationOrderIndex = 0;
      return true;
    }
    return false;
  }

  @Override
  public boolean retreat() {
    nationOrderIndex--;
    if (nationOrderIndex < 0) {
      nationOrderIndex = nationOrder.size() - 1;
      return true;
    }
    return false;
  }

  @Override
  public Nation toPlay() {
    return nationOrder.get(nationOrderIndex);
  }

  @Override
  public int movesInCycle() {
    return nationOrder.size();
  }

  @Override
  public boolean atBeginningOfCycle() {
    return nationOrderIndex == 0;
  }

  @Override
  public boolean setOrder(List<Nation> order) {
    Preconditions.checkNotNull(order);
    if (order.isEmpty()) {
      throw new IllegalArgumentException("order cannot be empty");
    }
    if (!atBeginningOfCycle()) {
      throw new IllegalStateException("cannot change phase ordering beyond start of cycle");
    }
    nationOrder = ImmutableList.copyOf(order);
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("MultiCyclePhase{");
    sb.append(generator);
    sb.append(", ");
    sb.append(toPlay());
    sb.append("}");
    return sb.toString();
  }
}
