/**
 * 
 */
package staticstate;

import state.nation.Nation;

/**
 * @author ngales
 */
public enum AdvCivNations implements Nation {
  // TODO: should this be NationInfo, as it was originally, or Nation, and should it be moved?..
  //@formatter:off
  AFRICA(),
  IBERIA(),
  ILLYRIA(),
  THRACE(),
  CRETE(),
  ASIA(),
  ASSYRIA(),
  BABYLON(),
  EGYPT();
  //@formatter:on

  @Override
  public String getName() {
    return name();
  }
}
