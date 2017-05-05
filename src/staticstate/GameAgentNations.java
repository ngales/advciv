/**
 * 
 */
package staticstate;


/**
 * @author ngales
 */
public enum GameAgentNations implements NationInfo {
  RULES_AGENT,
  PIRATES;

  @Override
  public String getName() {
    return name();
  }
}
