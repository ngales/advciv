/**
 * 
 */
package staticstate.state;


/**
 * @author ngales
 */
public enum OffboardAreaInfo implements AreaInfo {
  //@formatter:off
  STOCK, 
  TREASURY;
  //@formatter:on

  public static final int POP_LIMIT = Integer.MAX_VALUE;

  @Override
  public String getName() {
    return toString();
  }

  @Override
  public int getPopulationLimit() {
    return POP_LIMIT;
  }

  @Override
  public boolean isStartArea() {
    return false;
  }

  @Override
  public boolean hasCitySite() {
    return false;
  }

  @Override
  public boolean hasFloodPlain() {
    return false;
  }

  @Override
  public boolean hasFloodPlainCitySite() {
    return false;
  }

  @Override
  public boolean hasVolcano() {
    return false;
  }

  @Override
  public boolean hasCoast() {
    return false;
  }

  @Override
  public int getNumberOfCoasts() {
    return 0;
  }

  @Override
  public boolean isCoastal() {
    return false;
  }

  @Override
  public boolean isOpenSea() {
    return false;
  }

  @Override
  public boolean hasLand() {
    return false;
  }

  @Override
  public boolean hasWater() {
    return false;
  }

  @Override
  public boolean isIsland() {
    return false;
  }
}
