/**
 * 
 */
package state.board;


import staticstate.state.StateStructure;

/**
 * @author ngales
 */
public enum ConcreteBoard implements BoardType {
  STANDARD {
    @Override
    public Board getInstance(StateStructure structure) {
      return StandardBoard.getInstance(structure);
    };
  }
}
