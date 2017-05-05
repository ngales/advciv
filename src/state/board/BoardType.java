/**
 * 
 */
package state.board;

import staticstate.state.StateStructure;

/**
 * Represents a type of board.
 * 
 * @author ngales
 */
public interface BoardType {

  Board getInstance(StateStructure structure);
}
