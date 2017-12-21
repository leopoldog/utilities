package net.ghielmetti.utilities;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * Casting utilities to perform unsafe casts without warnings
 */
public final class Caster {
  /**
   * Performs an unsafe type cast.
   *
   * @param <T>
   *          cast target type
   * @param inCollection
   *          A collection of T objects.
   * @return The same collection.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Collection<T> cast(Collection inCollection) {
    return inCollection;
  }

  /**
   * Performs an unsafe type cast.
   *
   * @param <T>
   *          cast target type
   * @param inEnumeration
   *          An enumeration of T objects
   * @return The same enumeration
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Enumeration<T> cast(Enumeration inEnumeration) {
    return inEnumeration;
  }

  /**
   * Performs an unsafe type cast.
   *
   * @param <T>
   *          cast target type
   * @param inList
   *          A list of T objects.
   * @return The same list.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> List<T> cast(List inList) {
    return inList;
  }

  /**
   * Performs an unsafe type cast.
   *
   * @param <T>
   *          cast target type
   * @param inSet
   *          A set of T objects.
   * @return The same set.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Set<T> cast(Set inSet) {
    return inSet;
  }

  /**
   * Casts an arbitrary object to the specified type (and hides the warning).
   * This method is highly unsafe, use only if you know what you are doing.
   *
   * @param <T>
   *          cast target type
   * @param inObject
   *          The object to be casted.
   * @return The casted object.
   */
  @SuppressWarnings("unchecked")
  public static <T> T castAny(Object inObject) {
    return (T) inObject;
  }

  /** Disallow instantiation. */
  private Caster() {
  }
}