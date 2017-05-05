/**
 * 
 */
package state.area;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import state.nation.Nation;
import state.token.Token;
import staticstate.state.AreaInfo;

/**
 * @author ngales 
 */
class StandardArea implements Area {

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
   * 
   * TODO: Replace List with MultiSet from Google Guava, or MultiMap from same.
   */
  private final List<Token> tokens;

  private StandardArea(AreaInfo areaInfo) {
    this.areaInfo = areaInfo;
    tokens = new ArrayList<Token>();
  }

  public static StandardArea getInstance(AreaInfo areaInfo) {
    return new StandardArea(areaInfo);
  }

  @Override
  public AreaInfo getAreaInfo() {
    return areaInfo;
  }

  @Override
  public List<Token> getTokens() {
    return Collections.unmodifiableList(tokens);
  }

  @Override
  public boolean addToken(Token token) {
    return tokens.add(token);
  }

  @Override
  public boolean removeToken(Token token) {
    assert (tokens.contains(token));
    return tokens.remove(token);
  }

  @Override
  public String getName() {
    return areaInfo.getName();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("StandardArea{" + areaInfo.getName() + ", ");

    Map<Nation, Integer> nationCounts = new LinkedHashMap<Nation, Integer>();
    for (Token token : tokens) {
      Nation owner = token.getOwner();
      Integer count = nationCounts.get(owner);
      if (count == null) {
        count = 0;
      }
      nationCounts.put(owner, count + 1);
    }
    sb.append(nationCounts);

    sb.append("}");
    return sb.toString();
  }
}
