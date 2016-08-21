package net.ghielmetti.utilities;

/**
 * A utility class that can contains about everything that has no place
 * elsewhere.
 * 
 * @author Leopoldo Ghielmetti
 */
public final class Tools {
  /**
   * Check if two objects are equals. Same as the *.equals method but works also
   * if one or both objects are null.
   * 
   * @param inObject1
   *          The first object to check
   * @param inObject2
   *          The second object to check
   * @return true If the objects are the same one or answers true to the
   *         *.equals method. True also if both objects are null.
   */
  public static boolean equals(final Object inObject1, final Object inObject2) {
    if (inObject1 == inObject2) {
      return true;
    }

    return ((inObject1 != null) && inObject1.equals(inObject2));
  }

  /**
   * An empty constructor.
   */
  private Tools() {
    // nothing to do
  }
}
