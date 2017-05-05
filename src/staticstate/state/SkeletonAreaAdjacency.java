/**
 * 
 */
package staticstate.state;

import java.util.Collections;
import java.util.Set;

/**
 * @author ngales
 */
public class SkeletonAreaAdjacency implements AreaAdjacency {

  private final AreaInfo areaInfo;
  private final Set<AreaInfo> areaAdjacencies;

  private SkeletonAreaAdjacency(AreaInfo areaInfo, Set<AreaInfo> areaAdjacencies) {
    this.areaInfo = areaInfo;
    this.areaAdjacencies = areaAdjacencies;
  }

  public static SkeletonAreaAdjacency getInstance(AreaInfo areaInfo, Set<AreaInfo> areaAdjacencies) {
    return new SkeletonAreaAdjacency(areaInfo, areaAdjacencies);
  }

  @Override
  public AreaInfo getAreaInfo() {
    return areaInfo;
  }

  @Override
  public Set<AreaInfo> getAdjacencies() {
    return Collections.unmodifiableSet(areaAdjacencies);
  }

}
