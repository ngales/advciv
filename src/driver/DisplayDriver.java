/**
 * 
 */
package driver;

import display.StateDisplay;

/**
 * @author ngales
 */
public class DisplayDriver {

  private DisplayDriver() {}

  /**
   * @param args
   */
  public static void main(String[] args) {
    String structureString = "Grid";
    // Board gameState = StandardBoard.getInstance(structureString);

    StateDisplay display = StateDisplay.getInstance();
    display.show();
  }

}
