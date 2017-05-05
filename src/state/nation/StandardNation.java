/**
 * 
 */
package state.nation;

import com.google.common.base.Objects;

import staticstate.NationInfo;

/**
 * @author ngales
 */
public class StandardNation implements Nation {

  private final NationInfo nationInfo;

  private StandardNation(NationInfo nationInfo) {
    this.nationInfo = nationInfo;
  }

  public static StandardNation getInstance(NationInfo nationInfo) {
    return new StandardNation(nationInfo);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof StandardNation)) {
      return false;
    }
    StandardNation otherNation = (StandardNation) other;
    if (nationInfo.equals(otherNation.nationInfo)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nationInfo);
  }

  @Override
  public String toString() {
    return nationInfo.getName();
  }

  @Override
  public String getName() {
    return nationInfo.getName();
  }
}
