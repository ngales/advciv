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
public class StandardMove implements Move {

  // TODO: Determine object responsibilities
  private final Board board;
  private final Token token;

  private final Area from;
  private final Area to;

  private StandardMove(Board board, Token token, Area from, Area to) {
    this.board = board;
    this.token = token;
    this.from = from;
    this.to = to;
  }

  public static StandardMove getInstance(Board board, Token token, Area from, Area to) {
    return new StandardMove(board, token, from, to);
  }

  @Override
  public Board getBoard() {
    return board;
  }

  /*
   * @Override public Token getToken() { return token; }
   * 
   * @Override public Area getFrom() { return from; }
   * 
   * @Override public Area getTo() { return to; }
   */

  @Override
  public boolean applyMove() {
    // System.out.println("Applying move: " + this);
    return (board.removeToken(from, token) && board.addToken(to, token));
  }

  @Override
  public boolean undoMove() {
    return (board.removeToken(to, token) && board.addToken(from, token));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("StandardMove{");
    sb.append(from.getName());
    sb.append("->");
    sb.append(to.getName());
    sb.append(", ");
    sb.append(token);
    sb.append("}");
    return sb.toString();
  }
}
