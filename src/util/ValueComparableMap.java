/**
 * 
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;

/**
 * NOTE: This class has not been thoroughly tested.
 * 
 * @author ngales
 */
public class ValueComparableMap<K extends Comparable<K>, V> extends TreeMap<K, V> {
  // A map for doing lookups on the keys for comparison so we don't get infinite loops
  private final Map<K, V> valueMap;

  private ValueComparableMap(final Ordering<? super V> partialValueOrdering) {
    this(partialValueOrdering, new HashMap<K, V>());
  }

  private ValueComparableMap(Ordering<? super V> partialValueOrdering, HashMap<K, V> valueMap) {
    // Apply the value ordering
    super(partialValueOrdering
    // On the result of getting the value for the key from the map
        .onResultOf(Functions.forMap(valueMap))
        // as well as ensuring that the keys don't get clobbered
        .compound(Ordering.natural()));
    this.valueMap = valueMap;
  }

  /**
   * Should be called as
   * <p>
   * ValueComparableMap.getInstance(Ordering.natural());
   * <p>
   * or
   * <p>
   * ValueComparableMap.getInstance(Ordering.from(comparator));
   * 
   * @param partialValueOrdering
   * @return
   */
  public static <K extends Comparable<K>, V> ValueComparableMap<K, V> getInstance(
      Ordering<? super V> partialValueOrdering) {
    return new ValueComparableMap<K, V>(partialValueOrdering);
  }

  public V put(K k, V v) {
    if (valueMap.containsKey(k)) {
      // remove the key in the sorted set before adding the key again
      remove(k);
    }
    valueMap.put(k, v); // To get "real" unsorted values for the comparator
    return super.put(k, v); // Put it in value order
  }
}
