/**
 * 
 */
package statetransition;

import com.google.common.base.Preconditions;

/**
 * Decorator class that logs information about invocations of the decorated Move to STDOUT.
 * 
 * @author ngales
 */
public class LogMove implements Move {

  private final Move wrapped;

  private LogMove(Move wrapped) {
    Preconditions.checkNotNull(wrapped);
    this.wrapped = wrapped;
  }

  // Could add another parameter, like OutputStream, that dictates where to
  // output to, and keep STDOUT as default

  public static LogMove getInstance(Move move) {
    return new LogMove(move);
  }

  @Override
  public boolean applyMove() {
    System.out.println("Applying LogMove{" + wrapped + "}");
    return wrapped.applyMove();
  }

  @Override
  public boolean undoMove() {
    System.out.println("Undoing LogMove{" + wrapped + "}");
    return wrapped.undoMove();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("LogMove{");
    sb.append(wrapped);
    sb.append("}");
    return sb.toString();
  }
}
