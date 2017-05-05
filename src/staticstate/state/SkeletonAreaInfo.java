/**
 * 
 */
package staticstate.state;


/**
 * @author ngales
 */
public class SkeletonAreaInfo implements AreaInfo {

  // Booleans would be better represented more compactly as bit set
  private final String name;
  private final int popLimit;
  private boolean isStartArea;
  private boolean hasCitySite;
  private boolean hasFloodPlain;
  private boolean hasVolcano;
  private boolean hasCoast;
  private int numberCoasts;
  private boolean isCoastal;
  private boolean isOpenSea;
  private boolean hasLand;
  private boolean hasWater;
  private boolean isIsland;

  private SkeletonAreaInfo(String name, int popLimit, boolean isStartArea, boolean hasCitySite,
      boolean hasFloodPlain, boolean hasVolcano, boolean hasCoast, int numberCoasts,
      boolean isCoastal, boolean isOpenSea, boolean hasLand, boolean hasWater, boolean isIsland) {

    // TODO: Parameter checks, use Guava's methods for this

    this.name = name;
    this.popLimit = popLimit;
    this.isStartArea = isStartArea;
    this.hasCitySite = hasCitySite;
    this.hasVolcano = hasVolcano;
    this.hasCoast = hasCoast;
    this.numberCoasts = numberCoasts;
    this.isCoastal = isCoastal;
    this.isOpenSea = isOpenSea;
    this.hasLand = hasLand;
    this.hasWater = hasWater;
    this.isIsland = isIsland;
  }

  private SkeletonAreaInfo(String name, int popLimit) {
    this(name, popLimit, true, false, false, false, false, 0, false, false, true, false, false);
  }

  public static SkeletonAreaInfo getInstance(String name, int popLimit) {
    return new SkeletonAreaInfo(name, popLimit);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPopulationLimit() {
    return popLimit;
  }

  @Override
  public boolean isStartArea() {
    return isStartArea;
  }

  @Override
  public boolean hasCitySite() {
    return hasCitySite;
  }

  @Override
  public boolean hasFloodPlain() {
    return hasFloodPlain;
  }

  @Override
  public boolean hasFloodPlainCitySite() {
    return hasFloodPlain() && hasCitySite();
  }

  @Override
  public boolean hasVolcano() {
    return hasVolcano;
  }

  @Override
  public boolean hasCoast() {
    return hasCoast;
  }

  @Override
  public int getNumberOfCoasts() {
    return numberCoasts;
  }

  @Override
  public boolean isCoastal() {
    return isCoastal;
  }

  @Override
  public boolean isOpenSea() {
    return isOpenSea;
  }

  @Override
  public boolean hasLand() {
    return hasLand;
  }

  @Override
  public boolean hasWater() {
    return hasWater;
  }

  @Override
  public boolean isIsland() {
    return isIsland;
  }

}
