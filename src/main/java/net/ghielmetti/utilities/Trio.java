package net.ghielmetti.utilities;

import java.io.Serializable;

/**
 * Allow the creation of a three object group that can be used at will and added to Hashtables.
 *
 * @author Leopoldo Ghielmetti
 * @param <Left> The left parameter class
 * @param <Center> The center parameter class
 * @param <Right> The right parameter class
 */
public class Trio<Left, Center, Right> implements Serializable {
  private static final long serialVersionUID = -5170715597299150616L;

  private static final int  PRIME            = 31;

  /*
   * The three values
   */
  private final Left        left;
  private final Center      center;
  private final Right       right;

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

    Trio<?, ?, ?> other = (Trio<?, ?, ?>) obj;

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
   * Return the center object
   *
   * @return The object
   */
  public Center getCenter() {
    return center;
  }

  /**
   * Return the left object
   *
   * @return The object
   */
  public Left getLeft() {
    return left;
  }

  /**
   * Return the right object
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

  /**
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "(" + left + "," + center + "," + right + ")";
  }
}
