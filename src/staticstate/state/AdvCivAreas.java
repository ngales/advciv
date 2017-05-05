/**
 * 
 */
package staticstate.state;


/**
 * private Areas(int populationLimit, int numCoasts, boolean hasCitySite, boolean hasFloodPlain,
 * boolean hasLand, boolean hasWater, boolean hasVolcano, boolean isStartArea, boolean isOpenSea,
 * boolean isIsland) {
 * 
 * Should all areas with flood plain portions be counted as having flood plains? Since those areas
 * with city squares are explicitly marked as flood plain city if such, what about areas where
 * cities are constructed with no city sites?
 * 
 * Currently, have them marked as not having flood plains, (ie only flood plain city sites have that
 * marked), but could change it.
 * 
 * Should be 53 city squares, and 9 flood plain cities.
 * 
 * @author ngales
 */
public enum AdvCivAreas implements AreaInfo {

  /*
   * TO FIX:
   * 
   * 60 city sites, should be 53..
   * 
   * others not immediately wrong.
   * 
   * 
   * need to add: Coastal areas are areas which contain both land and ocean (nonlake) water.
   * 
   * Todo: Boolean attributes should really be stored as bit flags, and ints as shorts Since
   * attributes are not static, should they really be capitalized? Could condense the verify
   * attributes method. Could actually use more than one constructor type.. and cut down on initial
   * args.
   */

  //@formatter:off
  BRITANNIA	 (1, 1, false, false, false, true, true, false, false, false, false),
  BAY_OF_BISCAY(0, 0, false, false, false, false, true, false, false, true, false),
  LUSITANIA	 (1, 1, false, false, false, true, true, false, true, false, false),
  BAETICA		 (1, 0, false, false, false, true, false, false, true, false, false),
  CORDUBA		 (2, 1, true, false, false, true, true, false, true, false, false),
  WEST_MAURETANIA(2, 1, false, false, false, true, true, false, false, false, false),
  LONDINIUM	 (2, 1, false, false, false, true, true, false, false, false, false),
  LUGDUNENSIS	 (1, 1, false, false, false, true, true, false, false, false, false),
  AQUITANIA	 (3, 1, false, false, false, true, true, false, false, false, false),
  TARRACONENSIS(1, 1, false, false, false, true, true, false, false, false, false),
  HISPANIA	 (1, 0, false, false, false, true, false, false, false, false, false),
  NEW_CARTHAGE (2, 1, true, false, false, true, true, false, false, false, false),
  EAST_MAURETANIA(1, 1, false, false, false, true, true, false, false, false, false),
  IBERUS		 (1, 1, false, false, false, true, true, false, false, false, false),
  LOWER_GERMANY(3, 1, false, false, false, true, true, false, false, false, false),
  BELGICA		 (3, 1, false, false, false, true, true, false, false, false, false),
  UPPER_GERMANY(1, 0, false, false, false, true, false, false, false, false, false),
  RHINE		 (1, 0, false, false, false, true, false, false, false, false, false),
  GAUL		 (1, 0, false, true, false, true, false, false, false, false, false),
  NARBO		 (3, 1, true, true, true, true, true, false, false, false, false),
  MASSILLIA	 (2, 1, true, false, false, true, true, false, false, false, false),
  PYRENEES	 (2, 1, false, false, false, true, true, false, false, false, false),
  BALAERES	 (1, 1, false, false, false, true, true, false, false, false, true),
  EBUSUS		 (1, 1, false, false, false, true, true, false, false, false, true),
  WESTERN_MEDITERRANEAN(0, 0, false, false, false, false, true, false, false, true, false),
  NEW_AFRICA(1, 1, false, false, false, true, true, false, false, false, false),
  CIRTA(2, 1, true, false, false, true, true, false, true, false, false),
  GERMANY(5, 0, false, false, false, true, false, false, true, false, false),
  PANNONIA(4, 1, false, false, false, true, true, false, false, false, false),
  RHAETIA(1, 0, false, false, false, true, false, false, false, false, false),
  CISALPINA(1, 0, false, false, false, true, false, false, false, false, false),
  DALMATIA(2, 1, false, false, false, true, true, false, false, false, false),
  ETRURIA(2, 1, false, true, false, true, true, false, false, false, false),
  RUBICON(3, 1, false, true, false, true, true, false, false, false, false),
  ROME(2, 1, true, false, false, true, true, false, false, false, false),
  SAMNIUM(2, 1, false, false, false, true, true, false, false, false, false),
  NEAPOLIS(1, 1, true, false, false, true, true, true, false, false, false),
  CORSICA(1, 1, false, false, false, true, true, false, false, false, true),
  SARDINIA(1, 1, true, false, false, true, true, false, false, false, false),
  CARALIS(1, 1, false, false, false, true, true, false, false, false, false),
  NUMIDIA(2, 1, false, false, false, true, true, false, false, false, false),
  TUNISIA(2, 0, false, false, false, true, false, false, true, false, false),
  CARTHAGE(3, 1, true, false, false, true, true, false, false, false, false),
  THAPSUS(3, 1, true, false, false, true, true, false, false, false, false),
  SABRATA(2, 1, false, false, false, true, true, false, false, false, false),
  SAHARA(1, 0, false, false, false, true, false, false, true, false, false),
  TRIPOLI(1, 1, true, false, false, true, true, false, false, false, false),
  TRIPOLITANIA(1, 0, false, false, false, true, false, false, true, false, false),
  SYRACUSE(1, 1, true, false, false, true, true, true, false, false, false),
  PALERMO(2, 1, true, false, false, true, true, false, false, false, false),
  MILAZZO(2, 1, false, false, false, true, true, true, false, false, false),
  CAMPANIA(2, 1, false, false, false, true, true, true, false, false, false),
  TARENTUM(1, 1, true, false, false, true, true, false, false, false, false),
  ADRIATIC_SEA(0, 0, false, false, false, false, true, false, false, true, false),
  ILLYRICUM(2, 1, false, false, false, true, true, false, false, false, false),
  ITHACA(1, 1, true, false, false, true, true, false, false, false, true),
  CENTRAL_MEDITERRANEAN(0, 0, false, false, false, false, true, false, false, true, false),
  SUDETAN(1, 0, false, false, false, true, false, false, true, false, false),
  DACIA(3, 0, false, false, false, true, false, false, false, false, false),
  SCYTHIA(3, 2, false, false, false, true, true, false, true, false, false),
  DANUBE(5, 1, false, true, false, true, true, false, false, false, false),
  TYRAS(2, 1, true, true, true, true, true, false, false, false, false),
  MOESIA(4, 1, true, true, false, true, true, false, false, false, false),
  PAEONIA(1, 0, false, false, false, true, false, false, false, false, false),
  THRACE(2, 1, false, false, false, true, true, false, false, false, false),
  BYZANTIUM(3, 1, true, false, false, true, true, false, false, false, false),
  MACEDONIA(1, 0, false, false, false, true, false, false, false, false, false),
  LEMNOS(1, 1, false, false, false, true, true, false, false, false, true),
  THESSALONICA(1, 1, true, false, false, true, true, false, false, false, false),
  APPOLLONIA(1, 1, true, false, false, true, true, false, false, false, false),
  THESSALY(2, 1, false, false, false, true, true, false, false, false, false),
  CHALCIS(2, 1, true, false, false, true, true, false, false, false, false),
  EPIRUS(1, 1, false, false, false, true, true, false, false, false, false),
  DELPHI(2, 2, false, false, false, true, true, false, false, false, false),
  CORINTH(2, 1, true, false, false, true, true, false, false, false, false),
  SPARTA(1, 1, true, false, false, true, true, false, false, false, false),
  PHAESTOS(2, 1, true, false, false, true, true, false, true, false, false),
  KNOSSOS(3, 1, true, false, false, true, true, false, true, false, false),
  THERA(2, 1, true, false, false, true, true, true, false, false, true),
  ARGOS(1, 1, true, false, false, true, true, false, false, false, false),
  ATHENS(2, 1, true, false, false, true, true, false, false, false, false),
  ERETRIA(1, 1, true, false, false, true, true, false, false, false, false),
  AEGEAN_SEA(0, 0, false, false, false, false, true, false, false, true, false),
  LESBOS(1, 1, false, false, false, true, true, false, false, false, true),
  TROY(2, 1, true, false, false, true, true, false, false, false, false),
  BLACK_SEA(0, 0, false, false, false, false, true, false, false, true, false),
  CRIMEA(2, 1, false, false, false, true, true, false, false, false, false),
  KUBAN(1, 1, false, false, false, true, true, false, true, false, false),
  MIKOP(2, 1, false, false, false, true, true, false, true, false, false),
  NALCHIK(1, 0, false, false, false, true, false, false, true, false, false),
  CAPPADOCIA(1, 1, false, false, false, true, true, false, false, false, false),
  ARMENIA(3, 2, false, false, false, true, true, false, false, false, false),
  SINOPE(2, 1, true, false, false, true, true, false, false, false, false),
  PHRYGIA(1, 1, false, false, false, true, true, false, false, false, false),
  ANKARA(2, 0, true, false, false, true, false, false, false, false, false),
  BITHYNIA(2, 1, false, false, false, true, true, false, false, false, false),
  GORDIUM(2, 1, true, false, false, true, true, false, false, false, false),
  SARDES(2, 1, true, false, false, true, true, false, false, false, false),
  IONIA(3, 1, false, false, false, true, true, false, false, false, false),
  MILETUS(1, 1, true, false, false, true, true, false, false, false, false),
  RHODES(2, 1, true, false, false, true, true, false, false, false, true),
  LYCIA(1, 1, false, false, false, true, true, false, false, false, false),
  EASTERN_MEDITERRANEAN(0, 0, false, false, false, false, true, false, false, true, false),
  GALATIA(2, 2, false, false, false, true, true, false, false, false, false),
  KANESH(2, 1, true, false, false, true, true, false, false, false, false),
  CILICIA(2, 1, false, false, false, true, true, false, false, false, false),
  CYPRUS(2, 1, false, false, false, true, true, false, false, false, false),
  SALAMIS(1, 1, true, false, false, true, true, false, false, false, false),
  ANTIOCH(2, 1, true, false, false, true, true, false, false, false, false),
  ALEPPO(2, 0, true, false, false, true, false, false, false, false, false),
  ASSYRIA(4, 0, false, false, false, true, false, false, false, false, false),
  VAN(3, 1, false, false, false, true, true, false, false, false, false),
  NINEVAH(3, 2, true, false, false, true, true, false, false, false, false),
  CARRHAE(3, 0, true, false, false, true, false, false, false, false, false),
  MARI(2, 0, true, false, false, true, false, false, false, false, false),
  SYRIA(3, 0, false, false, false, true, false, false, false, false, false),
  PHOENICIA(3, 1, false, false, false, true, true, false, false, false, false),
  SIDON(1, 1, true, false, false, true, true, false, false, false, false),
  DAMASCUS(2, 0, true, false, false, true, false, false, false, false, false),
  TYRE(3, 1, true, false, false, true, true, false, false, false, false),
  JERICHO(1, 0, true, false, false, true, false, false, false, false, false),
  JERUSALEM(2, 1, true, false, false, true, true, false, false, false, false),
  PETRA(1, 1, true, false, false, true, true, false, false, false, false),
  GAZA(1, 1, false, false, false, true, true, false, false, false, false),
  SINAI(1, 1, false, false, false, true, true, false, false, false, false),
  TANIS(3, 1, true, true, true, true, true, false, false, false, false),
  EASTERN_DESERT(1, 1, false, false, false, true, true, false, false, false, false),
  ALEXANDRIA(4, 1, true, true, true, true, true, false, false, false, false),
  MEMPHIS(3, 0, true, true, true, true, false, false, false, false, false),
  FAYUM(5, 0, true, true, true, true, false, false, false, false, false),
  WESTERN_DESERT(2, 1, false, false, false, true, true, false, false, false, false),
  PTOLEMAIS(2, 1, false, false, false, true, true, false, false, false, false),
  RED_SEA(0, 0, false, false, false, false, true, false, false, true, false),
  NUBIA(1, 1, false, false, false, true, true, false, true, false, false),
  THEBES(3, 0, true, false, false, true, false, false, true, false, false),
  BUHEN(3, 0, true, false, false, true, false, false, true, false, false),
  UPPER_EGYPT(4, 0, false, true, false, true, false, false, true, false, false),
  SIWA(1, 0, false, false, false, true, false, false, true, false, false),
  CYRENACIA(2, 1, false, false, false, true, true, false, false, false, false),
  CYRENE(3, 1, true, false, false, true, true, false, false, false, false),
  JALO(1, 0, false, false, false, true, false, false, true, false, false),
  LIBYA(1, 1, false, false, false, true, true, false, false, false, false),
  MIDIAN(1, 1, false, false, false, true, true, false, false, false, false),
  ARABIA(1, 0, false, false, false, true, false, false, false, false, false),
  CHALDAEA(2, 1, false, false, false, true, true, false, false, false, false),
  SUMERIA(3, 0, false, true, false, true, false, false, false, false, false),
  CHARAX(3, 1, true, true, true, true, true, false, false, false, false),
  UR(3, 0, true, true, true, true, false, false, false, false, false),
  BABYLON(2, 1, true, true, true, true, true, false, false, false, false),
  SUSA(3, 1, true, true, false, true, true, false, true, false, false),
  MESOPOTAMIA(3, 0, false, true, false, true, false, false, false, false, false),
  BABYLONIA(4, 1, false, true, false, true, true, false, false, false, false),
  PARTHIA(2, 0, false, false, false, true, false, false, true, false, false),
  MEDIA(2, 1, false, false, false, true, true, false, true, false, false),
  ELAM(3, 1, false, false, false, true, true, false, false, false, false),
  LESSER_ARMENIA(1, 2, false, false, false, true, true, false, false, false, false),
  CAUCASUS(1, 1, false, false, false, true, true, false, true, false, false),
  PERSIAN_GULF(0, 0, false, false, false, false, true, false, false, true, false),
  CASPIAN_SEA(0, 0, false, false, false, false, true, false, false, true, false);
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

  private AdvCivAreas(int populationLimit, int numCoasts, boolean hasCitySite,
      boolean hasFloodPlain, boolean hasFloodPlainCitySite, boolean hasLand, boolean hasWater,
      boolean hasVolcano, boolean isStartArea, boolean isOpenSea, boolean isIsland) { // ,
                                                                                      // List<Area>
                                                                                      // landAdjacencies)
                                                                                      // {
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
    // this.landAdjacencies = landAdjacencies;
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
