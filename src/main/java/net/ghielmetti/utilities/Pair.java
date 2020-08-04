package net.ghielmetti.utilities;

/**
 * Allow the creation of a pair of objects that can be used at will and added to Hashtables.
 *
 * @author Leopoldo Ghielmetti
 * @param <Left> The left parameter class
 * @param <Right> The right parameter class
 * @deprecated Use org.apache.commons.lang3.tuple.ImmutablePair from commons-lang3 instead.
 */
@Deprecated
public class Pair<Left, Right> {
  private static final int PRIME = 31;

  // The two pair values
  private final Left  left;
  private final Right right;

  /**
   * Create a new object pair. You must specify a "left" and a "right" object.
   *
   * @param inLeft The left object
   * @param inRight The right object
   */
  public Pair(final Left inLeft, final Right inRight) {
    left = inLeft;
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

    Pair<?, ?> other = (Pair<?, ?>)obj;

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
   * Return the object on the left
   *
   * @return The object
   */
  public Left getLeft() {
    return left;
  }

  /**
   * Return the object on the right
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

    if (left != null) {
      result += left.hashCode();
    }

    result *= PRIME;

    if (right != null) {
      result += right.hashCode();
    }

    return result;
  }

  /**
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "(" + left + "," + right + ")";
  }
}
