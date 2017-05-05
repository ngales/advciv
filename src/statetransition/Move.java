/**
 * 
 */
package statetransition;

/**
 * @author ngales
 */
public interface Move {
  boolean applyMove();

  boolean undoMove();
}
