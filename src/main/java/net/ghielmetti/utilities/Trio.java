package net.ghielmetti.utilities;

/**
 * Allow the creation of a three object group that can be used at will and added to Hashtables.
 *
 * @author Leopoldo Ghielmetti
 * @param <Left> The left parameter class
 * @param <Center> The center parameter class
 * @param <Right> The right parameter class
 * @deprecated Use org.apache.commons.lang3.tuple.ImmutableTriple from commons-lang3 instead.
 */
@Deprecated
public class Trio<Left, Center, Right> {
  private static final int PRIME = 31;

  // The three values
  private final Left   left;
  private final Center center;
  private final Right  right;

  /**
   * Create a new object. You must specify a "left", a "center" and a "right" object.
   *
   * @param inLeft The left object
   * @param inCenter The center object
   * @param inRight The right object
   */
  public Trio(final Left inLeft, final Center inCenter, final Right inRight) {
    left = inLeft;
    center = inCenter;
    right = inRight;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Trio<?, ?, ?> other = (Trio<?, ?, ?>)obj;

    if (center == null) {
      if (other.center != null) {
        return false;
      }
    } else if (!center.equals(other.center)) {
      return false;
    }

    if (left == null) {
      if (other.left != null) {
        return false;
      }
    } else if (!left.equals(other.left)) {
      return false;
    }

    if (right == null) {
      if (other.right != null) {
        return false;
      }
    } else if (!right.equals(other.right)) {
      return false;
    }

    return true;
  }

  /**
   * Returns the center object
   *
   * @return The object
   */
  public Center getCenter() {
    return center;
  }

  /**
   * Returns the left object
   *
   * @return The object
   */
  public Left getLeft() {
    return left;
  }

  /**
   * Returns the right object
   *
   * @return The object
   */
  public Right getRight() {
    return right;
  }

  @Override
  public int hashCode() {
    int result = 1;

    result *= PRIME;

    if (center != null) {
      result += center.hashCode();
    }

    result *= PRIME;

    if (left != null) {
      result += left.hashCode();
    }

    result *= PRIME;

    if (right != null) {
      result += right.hashCode();
    }

    return result;
  }

  @Override
  public String toString() {
    return "(" + left + "," + center + "," + right + ")";
  }
}
