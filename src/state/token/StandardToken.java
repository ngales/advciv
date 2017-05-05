/**
 * 
 */
package state.token;

import state.nation.Nation;

/**
 * @author ngales
 */
public class StandardToken implements Token {

  private final Nation owner;

  private StandardToken(Nation owner) {
    this.owner = owner;
  }

  public static StandardToken getInstance(Nation owner) {
    return new StandardToken(owner);
  }

  @Override
  public Nation getOwner() {
    // Or should it send back NationInfo?
    return owner;
  }

  @Override
  public String toString() {
    return "StandardToken::" + owner;
  }

}
