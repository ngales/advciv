/**
 * 
 */
package state.area;

import staticstate.state.AreaInfo;

/**
 * @author ngales
 */
public enum AreaType {
  PIECE_TRACKING {
    @Override
    protected Area constructInstance(AreaInfo info) {
      // return StandardArea.getInstance(info);
      return MapArea.getInstance(info);
    }
  },
  NON_TRACKING {
    @Override
    protected Area constructInstance(AreaInfo info) {
      return EmptyArea.getInstance(info);
    }
  };

  protected abstract Area constructInstance(AreaInfo info);
}
