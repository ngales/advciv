/**
 * 
 */
package rules.phase;

import java.util.List;

import state.nation.Nation;

/**
 * @author ngales
 */
public interface PhaseType {
  Phase getInstance(List<Nation> nations);
}
