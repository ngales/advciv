/**
 * 
 */
package statetransition;

import state.Area;
import state.Board;
import state.Token;

/**
 * @author ngales
 */
public class AddTokenMove implements Move {

  private final Board board;
  private final Token token;
  private final Area area;

  private AddTokenMove(Board board, Token token, Area area) {
    this.board = board;
    this.token = token;
    this.area = area;
  }

  public static AddTokenMove getInstance(Board board, Token token, Area area) {
    return new AddTokenMove(board, token, area);
  }

  @Override
  public Board getBoard() {
    return board;
  }

  @Override
  public boolean applyMove() {
    return board.addToken(area, token);
  }

  @Override
  public boolean undoMove() {
    return board.removeToken(area, token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("AddTokenMove{");
    sb.append(area.getName());
    sb.append(", ");
    sb.append(token);
    sb.append("}");
    return sb.toString();
  }
}
