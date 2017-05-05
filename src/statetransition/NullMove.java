/**
 * 
 */
package statetransition;

import state.board.Board;

/**
 * Move which does nothing when invoked.
 * 
 * @author ngales
 */
public class NullMove implements Move {

  /**
   * All instances of this class are equal, so use a canonical single instance.
   */
  private static final NullMove INSTANCE = new NullMove();

  private NullMove() {}

  public static NullMove getInstance(Board board) {
    return INSTANCE;
  }

  @Override
  public boolean applyMove() {
    // Do nothing
    return true;
  }

  @Override
  public boolean undoMove() {
    // Do nothing
    return true;
  }

  @Override
  public String toString() {
    return "NullMove";
  }
}
