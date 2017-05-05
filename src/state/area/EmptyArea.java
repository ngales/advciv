/**
 * 
 */
package state.area;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

import state.token.Token;
import staticstate.state.AreaInfo;

/**
 * @author ngales
 */
public class EmptyArea implements Area {

  private final AreaInfo info;

  private EmptyArea(AreaInfo info) {
    Preconditions.checkNotNull(info);
    this.info = info;
  }

  public static EmptyArea getInstance(AreaInfo info) {
    return new EmptyArea(info);
  }

  @Override
  public AreaInfo getAreaInfo() {
    return info;
  }

  @Override
  public List<Token> getTokens() {
    return Collections.emptyList();
  }

  @Override
  public boolean addToken(Token token) {
    return false;
  }

  @Override
  public boolean removeToken(Token token) {
    return false;
  }

  @Override
  public String getName() {
    return toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("EmptyArea{" + info.getName() + "}");
    return sb.toString();
  }
}
