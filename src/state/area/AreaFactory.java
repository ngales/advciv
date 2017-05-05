/**
 * 
 */
package state.area;

import staticstate.state.AreaInfo;

import com.google.common.base.Preconditions;

/**
 * @author ngales
 */
public class AreaFactory {

  private final AreaType type;

  private AreaFactory(AreaType type) {
    Preconditions.checkNotNull(type);
    this.type = type;
  }

  public static AreaFactory getFactory(AreaType type) {
    // precondition will be checked by construction call
    return new AreaFactory(type);
  }

  public Area buildArea(AreaInfo info) {
    // precondition will be checked by buildArea call
    return buildArea(type, info);
  }

  public AreaType getType() {
    return type;
  }

  public static Area buildArea(AreaType type, AreaInfo info) {
    Preconditions.checkNotNull(type);
    Preconditions.checkNotNull(info);
    return type.constructInstance(info);
  }
}
