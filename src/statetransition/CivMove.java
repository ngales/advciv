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
public class CivMove implements StateMove {

  private final MoveType type;
  private final Board board;
  private final Nation agent;
  private final Area from;
  private final Area to;

  private CivMove(MoveType type, Board board, Nation agent, Area from, Area to) {
    Preconditions.checkNotNull(type);
    Preconditions.checkNotNull(board);
    Preconditions.checkNotNull(agent);
    Preconditions.checkNotNull(from);
    Preconditions.checkNotNull(to);
    this.type = type;
    this.board = board;
    this.agent = agent;
    this.from = from;
    this.to = to;
  }

  // TOOD: could make convenience method(s) for adding and removing tokens/ships, not necessarily
  // here

  public static CivMove getInstance(MoveType type, Board board, Nation agent, Area from, Area to) {
    return new CivMove(type, board, agent, from, to);
  }

  @Override
  public MoveType getType() {
    return type;
  }

  @Override
  public Board getBoard() {
    return board;
  }

  @Override
  public Nation getAgent() {
    return agent;
  }

  @Override
  public Area getFrom() {
    return from;
  }

  @Override
  public Area getTo() {
    return to;
  }

  @Override
  public boolean applyMove() {
    return type.apply(board, agent, from, to);
  }

  @Override
  public boolean undoMove() {
    return type.apply(board, agent, to, from);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("CivMove{");
    sb.append(type);
    sb.append(", ");
    // sb.append(board);
    // sb.append(", ");
    sb.append(agent);
    sb.append(", ");
    sb.append(from);
    sb.append(", ");
    sb.append(to);
    sb.append("}");
    return sb.toString();
  }
}
