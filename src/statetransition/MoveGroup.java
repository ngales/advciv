/**
 * 
 */
package statetransition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorator class that bundles a List of Move objects.
 * 
 * @author ngales
 */
public class MoveGroup implements Move {

  private final List<Move> moves;

  private MoveGroup(List<Move> moves) {
    this.moves = moves;
  }

  public static MoveGroup getInstance(List<Move> moves) {
    return new MoveGroup(moves);
  }

  @Override
  public boolean applyMove() {
    // TODO: Should this be all or nothing?
    for (Move move : moves) {
      if (!move.applyMove()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean undoMove() {
    // undoMoves starting from opposite end of order as applied (ie LIFO stack)
    // TODO: is this really the best way to iterate in reverse order?
    List<Move> movesCopy = new ArrayList<Move>(moves);
    Collections.reverse(movesCopy);
    // TODO: Should this be all or nothing?
    for (Move move : movesCopy) {
      if (!move.undoMove()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("MoveGroup{");
    sb.append(moves);
    sb.append("}");
    return sb.toString();
  }
}
