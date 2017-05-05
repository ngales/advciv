/**
 * 
 */
package staticstate.state;


/**
 * @author ngales
 */
public enum SimpleAreas implements AreaInfo {
  //@formatter:off
  AREA_ONE(1, 0, false, false, false, true, false, false, true, false, false),
  AREA_TWO(2, 0, false, false, false, true, false, false, true, false, false);
  //@formatter:on

  private final int populationLimit;
  private final int numCoasts;
  private final boolean hasCitySite;
  private final boolean hasFloodPlain;
  private final boolean hasFloodPlainCitySite;
  private final boolean hasLand;
  private final boolean hasWater;
  private final boolean hasVolcano;
  private final boolean isStartArea;
  private final boolean isOpenSea;
  private final boolean isIsland;

  private SimpleAreas(int populationLimit, int numCoasts, boolean hasCitySite,
      boolean hasFloodPlain, boolean hasFloodPlainCitySite, boolean hasLand, boolean hasWater,
      boolean hasVolcano, boolean isStartArea, boolean isOpenSea, boolean isIsland) {
    this.populationLimit = populationLimit;
    this.numCoasts = numCoasts;
    this.hasCitySite = hasCitySite;
    this.hasFloodPlain = hasFloodPlain;
    this.hasFloodPlainCitySite = hasFloodPlainCitySite;
    this.hasLand = hasLand;
    this.hasWater = hasWater;
    this.hasVolcano = hasVolcano;
    this.isStartArea = isStartArea;
    this.isOpenSea = isOpenSea;
    this.isIsland = isIsland;
    assert (checkFieldsCorrect());
  }

  /**
   * Helper for assert method in constructor, validates final enum fields and should theoretically
   * not be needed unless making changes.
   * 
   * @return
   */
  private boolean checkFieldsCorrect() {
    if (populationLimit < 0 || populationLimit > 5) {
      System.out.println("Population limit out of bounds");
      return false;
    }
    if (populationLimit == 0 && hasLand) {
      System.out.println("Land but no population");
      return false;
    }
    if (numCoasts < 0) {
      System.out.println("Number of numCoasts out of bounds");
      return false;
    }
    if (numCoasts != 0 && !hasLand) {
      System.out.println("Nonzero numCoasts but no land");
      return false;
    }
    if (numCoasts < 1 && hasWater && !isOpenSea) {
      System.out.println("Water but no numCoasts");
      return false;
    }
    if (!hasLand && !hasWater) {
      System.out.println("No land and no water");
      return false;
    }
    if (!hasLand && hasCitySite) {
      System.out.println("City site but no land");
      return false;
    }
    if (!hasLand && hasFloodPlain) {
      System.out.println("Flood plain but no land");
      return false;
    }
    if (hasFloodPlainCitySite && !hasFloodPlain) {
      System.out.println("Flood plain city site but no flood plain");
      return false;
    }
    if (hasFloodPlainCitySite && !hasCitySite) {
      System.out.println("Flood plain city site but no city site");
      return false;
    }
    if (isOpenSea && populationLimit != 0) {
      System.out.println("Nonzero population limit in open sea");
      return false;
    }
    if (isOpenSea && numCoasts != 0) {
      System.out.println("Nonzero numCoasts in open sea");
      return false;
    }
    if (isOpenSea
        && (hasCitySite || hasFloodPlain || hasLand || !hasWater || hasVolcano || isStartArea || isIsland)) {
      System.out.println("Open sea and other impossible attribute");
      return false;
    }
    if (isStartArea && isOpenSea) {
      System.out.println("Start area in open sea");
      return false;
    }
    if (isIsland && (!hasLand || !hasWater)) {
      System.out.println("Island but no land or water");
      return false;
    }
    if (isIsland && numCoasts != 1) {
      System.out.println("Island with more than one coast");
      return false;
    }
    return true;
  }

  @Override
  public int getPopulationLimit() {
    return populationLimit;
  }

  @Override
  public boolean hasCoast() {
    return numCoasts > 0 ? true : false;
  }

  @Override
  public int getNumberOfCoasts() {
    return numCoasts;
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
    return hasFloodPlainCitySite;
  }

  @Override
  public boolean hasLand() {
    return hasLand;
  }

  @Override
  public boolean hasVolcano() {
    return hasVolcano;
  }

  @Override
  public boolean hasWater() {
    return hasWater;
  }

  @Override
  public boolean isCoastal() {
    return (numCoasts != 0);
  }

  @Override
  public boolean isOpenSea() {
    return isOpenSea;
  }

  @Override
  public boolean isStartArea() {
    return isStartArea;
  }

  @Override
  public boolean isIsland() {
    return isIsland;
  }

  @Override
  public String getName() {
    return this.name();
  }

  /**
   * Some web description: Either name() or super.toString() may be called here. name() is final,
   * and always returns the exact name as specified in declaration; toString() is not final, and is
   * intended for presentation to the user. It seems best to call name() here.
   */
  @Override
  public String toString() {
    // String s1 = super.toString();
    // String s2 = name();
    // return (s1 + s2);
    String string = name();

    // string += "\n\tPopulation Limit: " + populationLimit;
    // string += "\n\tNumber of numCoasts: " + numCoasts;
    // string += "\n\tHas City Site: " + hasCitySite;
    // string += "\n\tHas Flood Plain: " + hasFloodPlain;
    // string += "\n\tHas Land: " + hasLand;
    // string += "\n\tHas Water: " + hasWater;
    // string += "\n\tHas Volcano: " + hasVolcano;
    // string += "\n\tIs Start Area: " + isStartArea;
    // string += "\n\tIs Open Sea: " + isOpenSea;
    // string += "\n\tIs Island: " + isIsland;
    // string += "\n";

    return string;
  }
}
