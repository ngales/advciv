/**
 * 
 */
package staticstate.state;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ngales
 */
public enum SimpleAdjacencies implements AreaAdjacency {
  //@formatter:off
  AREA_ONE(SimpleAreas.AREA_ONE, new AreaInfo[] {SimpleAreas.AREA_TWO}), 
  AREA_TWO(SimpleAreas.AREA_TWO, new AreaInfo[] {SimpleAreas.AREA_ONE});
  //@formatter:on

  // AreaInfo is immutable, therefore final Collections of AreaInfo are immutable
  private final AreaInfo area;
  private final Set<AreaInfo> adjacencies;

  private SimpleAdjacencies(AreaInfo area) {
    this(area, AreaAdjacency.NO_ADJACENCIES);
  }

  private SimpleAdjacencies(AreaInfo area, AreaInfo[] adjacencies) {
    this.area = area;
    this.adjacencies = new LinkedHashSet<AreaInfo>(Arrays.<AreaInfo>asList(adjacencies));;
  }

  @Override
  public AreaInfo getAreaInfo() {
    return area;
  }

  @Override
  public Set<AreaInfo> getAdjacencies() {
    return Collections.unmodifiableSet(adjacencies);
  }

}
