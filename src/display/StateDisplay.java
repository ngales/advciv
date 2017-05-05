/**
 * 
 */
package display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import state.area.Area;
import state.board.Board;
import state.nation.Nation;
import state.token.Token;

/**
 * @author ngales
 */
public class StateDisplay extends JFrame {

  private static final long serialVersionUID = -7688735409914446811L;

  private StateDisplay() {
    setTitle("My empty frame");
    setSize(300, 200); // default size is 0,0
    setLocation(10, 200); // default is 0,0 (top left corner)

    // Window Listeners
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose(); // needed?
        System.exit(0);
      } // windowClosing
    });
  }

  public static StateDisplay getInstance() {
    return new StateDisplay();
  }

  public void update(Board gameState) {
    Set<Area> areas = gameState.getAreas();
    for (Area area : areas) {
      List<Token> tokens = area.getTokens();

      // Get map, use code from other class, refactor to reuse in utility class

      Map<Nation, List<Token>> nationTokenMap = new LinkedHashMap<Nation, List<Token>>();
      for (Token token : tokens) {
        Nation owner = token.getOwner();
        List<Token> nationTokenList = nationTokenMap.get(owner);
        if (nationTokenList == null) {
          nationTokenList = new ArrayList<Token>();
          nationTokenMap.put(owner, nationTokenList);
        }
        nationTokenList.add(token);
      }
    }
  }
}
