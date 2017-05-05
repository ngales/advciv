/**
 * 
 */
package state.area;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import state.nation.Nation;
import state.token.StandardToken;
import state.token.Token;
import staticstate.state.AreaInfo;

/**
 * @author ngales
 */
class MapArea implements Area {

  private final AreaInfo areaInfo;

  /*
   * List isn't the most appropriate Collection here.. order does not matter for the tokens in this
   * area. A more appropriate collection would be a set; however, that would introduce the
   * requirement of uniqueness of elements, which is not necessarily needed. A MultiSet (as provided
   * by eg Google Guava) might be best, since in those ordering does not matter but uniqueness is
   * not needed. Or perhaps, the concept of a token is not needed as independant objects, or is
   * needed only as flyweight objects. In that case, perhaps the most appropriate is a map, of token
   * to int, or even of nation to int.
   * 
   * With a token equals() based on object reference, a set would most likely work as things stand
   * now, but not be clear. A multiset would be better. A map might be best; are unique tokens even
   * needed?
   */
  private final Map<Nation, Integer> tokens;

  private MapArea(AreaInfo areaInfo) {
    Preconditions.checkNotNull(areaInfo);
    this.areaInfo = areaInfo;
    tokens = new HashMap<Nation, Integer>();
  }

  public static MapArea getInstance(AreaInfo areaInfo) {
    return new MapArea(areaInfo);
  }

  @Override
  public AreaInfo getAreaInfo() {
    return areaInfo;
  }

  @Override
  public List<Token> getTokens() {
    List<Token> tokenList = new ArrayList<Token>();
    for (Nation owner : tokens.keySet()) {
      Integer ownerCount = tokens.get(owner);
      assert (ownerCount != null);
      for (int i = 0; i < ownerCount; i++) {
        // TODO: Move this dependency elsewhere
        Token token = StandardToken.getInstance(owner);
        tokenList.add(token);
      }
    }
    return Collections.unmodifiableList(tokenList);
  }

  @Override
  public boolean addToken(Token token) {
    Nation owner = token.getOwner();
    Integer ownerCount = tokens.get(owner);
    if (ownerCount == null) {
      ownerCount = 0;
    }
    try {
      tokens.put(owner, ownerCount + 1);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean removeToken(Token token) {
    Nation owner = token.getOwner();
    Integer ownerCount = tokens.get(owner);
    if (ownerCount == null || ownerCount == 0) {
      return false;
    }

    try {
      if (ownerCount == 1) {
        tokens.remove(owner);
      } else {
        tokens.put(owner, ownerCount - 1);
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public String getName() {
    return areaInfo.getName();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("StandardArea{" + areaInfo.getName() + ", ");
    sb.append(tokens);
    sb.append("}");
    return sb.toString();
  }
}
