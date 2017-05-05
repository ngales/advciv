/**
 * 
 */
package staticstate;

import java.util.LinkedHashMap;
import java.util.Map;

import staticstate.state.SimpleAdjacencies;
import staticstate.state.SimpleAreas;

/**
 * @author ngales
 */
public class StructureFactory {

  // TODO: remap to config structure
  private static final Map<String, Structure> structureMap;
  static {
    structureMap = new LinkedHashMap<String, Structure>();
    structureMap.put(
        "Simple",
        SimpleStructure.getInstance(SimpleAreas.values(), SimpleAdjacencies.values(),
            AdvCivNations.values()));
    // structureMap.put("Grid", GridStructure.getInstance());
    structureMap.put("Grid", GridStructureFactory.getStructure(2, 2));
  }

  private StructureFactory() {}

  // TODO: revise to use enum to signify which to use, or a file specifying files
  public static Structure getInstance(String structureString) {
    Structure structure = structureMap.get(structureString);
    return structure;
  }

  /*
   * public static Structure getInstance(Set<AreaInfo> areas, Set<AreaAdjacency> adjacencies,
   * Set<NationInfo> nations) { return SimpleStructure.getInstance(areas, adjacencies, nations); }
   */
}
