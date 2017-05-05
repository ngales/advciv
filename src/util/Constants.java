/**
 * 
 */
package util;

/**
 * Noninstantiable utility class.
 * 
 * @author ngales
 * 
 */
public class Constants {

  public static final String TERMINAL_SEPERATOR =
      "--------------------------------------------------------------------------------";

  // Suppress default constructor for non-instantiability
  private Constants() {
    throw new AssertionError();
  }

  // System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
}
