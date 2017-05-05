/**
 * 
 */
package staticstate.state;


/**
 * Represents the static aspects of an Area of a Board.
 * 
 * @author ngales
 */
public interface AreaInfo {

  String getName();

  int getPopulationLimit();

  boolean isStartArea();

  boolean hasCitySite();

  boolean hasFloodPlain();

  boolean hasFloodPlainCitySite();

  boolean hasVolcano();

  boolean hasCoast();

  int getNumberOfCoasts();

  boolean isCoastal();

  boolean isOpenSea();

  boolean hasLand();

  boolean hasWater();

  // boolean hasLandAndWater();

  boolean isIsland();

  // int getNumberOfAreas();

  // int getAreaNumber();

  // Area getAreaByNumber(int number);

  // List<Area> getLandAdjacencies();
}
