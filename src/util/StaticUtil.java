/**
 * 
 */
package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;

/**
 * Noninstantiable utility class for working with Guava collections objects.
 * 
 * @author ngales
 */
public class StaticUtil {

  // Suppress default constructor for non-instantiability
  private StaticUtil() {
    throw new AssertionError();
  }

  /**
   * Returns a Multiset corresponding to the given counting map.
   * 
   * @param countingMap associating key with Integer values.
   * @return Multiset corresponding to given counting map.
   */
  public static <K> Multiset<K> convertToMultiset(Map<K, Integer> countingMap) {
    Multiset<K> multiset = HashMultiset.create(countingMap.size());
    for (Entry<K, Integer> entry : countingMap.entrySet()) {
      multiset.add(entry.getKey(), entry.getValue());
    }
    return multiset;
  }

  /**
   * Returns a Map with the same key-value pairs as the given map and with iteration order
   * corresponding to the descending natural ordering of that map's values. The returned map is
   * immutable, and changes to the input map after calling this method will not affect the map which
   * this method outputs.
   * <p>
   * The given Map may not contain null keys or values. The returned map may behave unexpectedly if
   * the keys or values in it change after the map has been created.
   * 
   * @param map containing entries to be added to output.
   * @return map with descending iteration order corresponding to input map's value ordering.
   */
  public static <K, V extends Comparable<? super V>> Map<K, V> descendingValueOrderedMap(
      Map<K, V> map) {
    List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(map.entrySet());
    // TODO: I don't like having to instantiate a new comparator each method call, but couldn't get
    // a suitable class version to compile.
    Comparator<Map.Entry<K, V>> reverse_comparator = new Comparator<Map.Entry<K, V>>() {
      @Override
      public int compare(Entry<K, V> o1, Entry<K, V> o2) {
        return o2.getValue().compareTo(o1.getValue());
      }
    };
    Collections.sort(entries, reverse_comparator);
    ImmutableMap.Builder<K, V> builder = new ImmutableMap.Builder<K, V>();
    for (Entry<K, V> entry : entries) {
      builder.put(entry);
    }
    return builder.build();
  }

  /**
   * Returns a Map with the same key-value pairs as the given map and with iteration order
   * corresponding to the ascending natural ordering of that map's values. The returned map is
   * immutable, and changes to the input map after calling this method will not affect the map which
   * this method outputs.
   * <p>
   * The given Map may not contain null keys or values. The returned map may behave unexpectedly if
   * the keys or values in it change after the map has been created.
   * 
   * @param map containing entries to be added to output.
   * @return map with ascending iteration order corresponding to input map's value ordering.
   */
  public static <K, V extends Comparable<? super V>> Map<K, V> ascendingValueOrderedMap(
      Map<K, V> map) {
    List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(map.entrySet());
    // TODO: I don't like having to instantiate a new comparator each method call, but couldn't get
    // a suitable class version to compile.
    Comparator<Map.Entry<K, V>> reverse_comparator = new Comparator<Map.Entry<K, V>>() {
      @Override
      public int compare(Entry<K, V> o1, Entry<K, V> o2) {
        return o2.getValue().compareTo(o1.getValue());
      }
    };
    Collections.sort(entries, reverse_comparator);
    ImmutableMap.Builder<K, V> builder = new ImmutableMap.Builder<K, V>();
    for (Entry<K, V> entry : entries) {
      builder.put(entry);
    }
    return builder.build();
  }

  // TODO: make the working non-generic anonymous Comparator below have the generics of this static
  // class, and remove this class-- see attempt at doing so below non-generic anonymous version
  /*
   * static class ValueComparator<V extends Comparable<? super V>> implements
   * Comparator<Map.Entry<?, V>> { private ValueComparator() {
   * 
   * }
   * 
   * @Override public int compare(Map.Entry<?, V> o1, Map.Entry<?, V> o2) { // Call compareTo() on
   * V, which is known to be a Comparable<V> return o1.getValue().compareTo(o2.getValue()); } }
   * 
   * // problem is, that you can't declare generics when instantiating an anonymous class like this
   * // and it doesn't seem to like it as-is private static Comparator<? extends Comparable<?>>
   * VALUE_COMPARATOR = new Comparator<Comparable<?>>() {
   * 
   * @Override public int compare(Comparable o1, Comparable o2) { return o1.compareTo(o2); } };
   */

  /**
   * Copies each key-value mapping from map into an ordered map, with keys and values reversed.
   * Ordering of returned map will be natural ordering of the passed map's values.
   * 
   * @param map any Map.
   * @return the given Map with key-value mappings reversed.
   */
  public static <K, V extends Comparable<? super V>> TreeMap<V, Set<K>> invertFrom(Map<K, V> map) {
    SetMultimap<V, K> inverseMultimap =
        Multimaps.invertFrom(Multimaps.forMap(map), HashMultimap.<V, K>create());
    // guaranteed to be safe by contract of Guava SetMultimap
    @SuppressWarnings("unchecked")
    TreeMap<V, Set<K>> inverse =
        (TreeMap<V, Set<K>>) (Map<?, ?>) new TreeMap<V, Collection<K>>(inverseMultimap.asMap());
    return inverse;
  }

  /**
   * Treat an {@link Iterator} as an {@link Iterable} so it can be used in an enhanced for-loop.
   * Bear in mind that the iterator is "consumed" by the loop and so should be used only once.
   * Generally it is best to put the code which obtains the iterator inside the loop header. <div
   * class="nonnormative"> Example of correct usage:
   * 
   * <pre>
   * String text = ...;
   * for (String token : NbCollections.iterable(new {@link java.util.Scanner}(text))) {
   *     // ...
   * }
   * </pre>
   * 
   * </div>
   * 
   * @param iterator an iterator
   * @return an iterable wrapper which will traverse the iterator once
   * @throws NullPointerException if the iterator is null
   * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6312085">Java bug #6312085</a>
   * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6360734">Java bug #6360734</a>
   * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4988624">Java bug #4988624</a>
   * @since org.openide.util 7.5
   */
  public static <E> Iterable<E> iterable(final Iterator<E> iterator) {
    Preconditions.checkNotNull(iterator);
    return new Iterable<E>() {
      @Override
      public Iterator<E> iterator() {
        return iterator;
      }
    };
  }
}
