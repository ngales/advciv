/**
 * 
 */
package staticstate.state;

import java.util.Set;

/**
 * @author ngales
 */
public interface AreaAdjacency {

  public static final AreaInfo[] NO_ADJACENCIES = new AreaInfo[0];

  AreaInfo getAreaInfo();

  Set<AreaInfo> getAdjacencies();
}
