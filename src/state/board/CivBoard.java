/**
 * 
 */
package state.board;


import state.area.Area;
import state.area.AreaFactory;
import state.area.AreaType;

/**
 * Adds functionality shared across implementations of Advanced Civilization Board objects.
 * 
 * @author ngales
 */
public abstract class CivBoard implements Board {

  private final Area treasury;
  private final Area stock;

  public CivBoard() {
    this.treasury = AreaFactory.buildArea(AreaType.NON_TRACKING, Board.TREASURY);
    this.stock = AreaFactory.buildArea(AreaType.NON_TRACKING, Board.STOCK);
  }

  @Override
  public Area getTreasury() {
    return treasury;
  }

  @Override
  public Area getStock() {
    return stock;
  }
}
