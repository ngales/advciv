/**
 * 
 */
package state.area;

import java.util.List;

import state.token.Token;
import staticstate.state.AreaInfo;

/**
 * Includes tokens on this area, as well as AreaInfo.
 * 
 * @author ngales
 */
public interface Area {

  Area NULL = null;

  AreaInfo getAreaInfo();

  List<Token> getTokens();

  // Map<Nation,Integer> getTokens();

  boolean addToken(Token token);

  boolean removeToken(Token token);

  String getName();
}
