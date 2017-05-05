/**
 * 
 */
package staticstate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import state.nation.Nation;
import staticstate.rules.RulesStructure;
import staticstate.rules.SimpleRulesStructure;
import staticstate.state.AreaAdjacency;
import staticstate.state.AreaInfo;
import staticstate.state.SimpleStateStructure;
import staticstate.state.SkeletonAreaAdjacency;
import staticstate.state.SkeletonAreaInfo;
import staticstate.state.StateStructure;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author ngales
 */
public class GridStructureFactory {

  private GridStructureFactory() {}

  public static Structure getStructure(int size, int popLimit) {
    Nation[] agents = AdvCivNations.values();
    StateStructure stateStructure = getStateStructure(2, 2, (Set) Sets.newHashSet(agents));
    RulesStructure rulesStructure =
        SimpleRulesStructure.getInstance((List) Lists.newArrayList(agents));
    assert (stateStructure != null);
    assert (rulesStructure != null);
    return SimpleStructure.getInstance(stateStructure, rulesStructure);
  }

  public static StateStructure getStateStructure(int size, int popLimit, Set<Nation> agents) {
    Preconditions.checkArgument(size > 0);
    Preconditions.checkArgument(popLimit > 0);

    Set<AreaInfo> areas = new HashSet<AreaInfo>((int) Math.pow(size, 2));

    // fill a 2-dimensional grid structure with newly created AreaInfo objects
    AreaInfo[][] grid = new AreaInfo[size][size];
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        String name = String.valueOf(row) + "," + String.valueOf(column);
        AreaInfo area = SkeletonAreaInfo.getInstance(name, popLimit);
        areas.add(area);
        grid[row][column] = area;
      }
    }

    // TODO: initialize with number of edges in the grid
    Set<AreaAdjacency> adjacencies = new HashSet<AreaAdjacency>();

    // link the created AreaInfo objects from the grid
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        AreaInfo area = grid[row][column];
        Set<AreaInfo> neighbors = new HashSet<AreaInfo>(4);
        // north
        if (row > 0) {
          neighbors.add(grid[row - 1][column]);
        }
        // south
        if (row < size - 1) {
          neighbors.add(grid[row + 1][column]);
        }
        // west
        if (column > 0) {
          neighbors.add(grid[row][column - 1]);
        }
        // east
        if (column < size - 1) {
          neighbors.add(grid[row][column + 1]);
        }
        AreaAdjacency adjacency = SkeletonAreaAdjacency.getInstance(area, neighbors);
        adjacencies.add(adjacency);
      }
    }

    return SimpleStateStructure.getInstance(areas, adjacencies, agents);
  }

}
