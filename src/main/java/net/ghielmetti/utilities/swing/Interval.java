package net.ghielmetti.utilities.swing;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Defines an interval element for the {@link JMultiValueRuler} class.<br>
 * An interval contains a value that's included between a minimum and a maximum values.<br>
 * Three colors can be specified, one for the backgroud (used to display a rectangle), one for the foreground (used to
 * display the limits indicators) and one for the value (used to display the value indicator).
 *
 * @author Leopoldo Ghielmetti
 * @param <T> The type of values
 */
public class Interval<T extends Number> {
  private T          minimum;
  private T          maximum;
  private T          value;
  private Color      fgColor    = Color.BLACK;
  private Color      bgColor    = Color.LIGHT_GRAY;
  private Color      fgValColor = Color.BLACK;
  private Color      bgValColor = Color.YELLOW;
  private JComponent owner;
  private boolean    visible;

  /** Creates a new Interval object. */
  public Interval() {
    // nothing to do
  }

  /**
   * Creates a new Interval object with specified minimum and maximum values.
   *
   * @param inMinimum The minimum value.
   * @param inMaximum The maximum value.
   */
  public Interval(final T inMinimum, final T inMaximum) {
    minimum = inMinimum;
    maximum = inMaximum;
  }

  /**
   * Get the background color.
   *
   * @return The {@link Color}.
   */
  public Color getBackground() {
    return bgColor;
  }

  /**
   * Get the background value color (<code>null</code> if transparent).
   *
   * @return The {@link Color}.
   */
  public Color getBackgroundValueColor() {
    return bgValColor;
  }

  /**
   * Get the foreground color.
   *
   * @return The {@link Color}.
   */
  public Color getForeground() {
    return fgColor;
  }

  /**
   * Get the foreground value color.
   *
   * @return The {@link Color}.
   */
  public Color getForegroundValueColor() {
    return fgValColor;
  }

  /**
   * Get the maximum limit.
   *
   * @return The minimum
   */
  public T getMaximum() {
    return maximum;
  }

  /**
   * Get the minimum limit.
   *
   * @return The maximum
   */
  public T getMinimum() {
    return minimum;
  }

  /**
   * Returns the owner swing component.
   *
   * @return The owner of this limit.
   */
  public JComponent getOwner() {
    return owner;
  }

  /**
   * Get the current value.
   *
   * @return The current value.
   */
  public T getValue() {
    return value;
  }

  /**
   * Returns true if this limit has a valid value (a value located between the minimum and the maximum.
   *
   * @return <code>true</code> if the value is valid.
   */
  public boolean hasValidValue() {
    return value != null && value.doubleValue() >= minimum.doubleValue() && value.doubleValue() <= maximum.doubleValue();
  }

  /**
   * Returns true if this limit is visible on the ruler.
   *
   * @return <code>true</code> if visible.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Set the new background Color.
   *
   * @param inColor The new {@link Color}.
   */
  public void setBackground(final Color inColor) {
    bgColor = inColor;

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Set the new background value Color (<code>null</code> if transparent).
   *
   * @param inColor The new {@link Color}.
   */
  public void setBackgroundValueColor(final Color inColor) {
    bgValColor = inColor;

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Set the new foreground Color.
   *
   * @param inColor The new {@link Color}.
   */
  public void setForeground(final Color inColor) {
    fgColor = inColor;

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Set the new foreground value Color (<code>null</code> to use container's background).
   *
   * @param inColor The new {@link Color}.
   */
  public void setForegroundValueColor(final Color inColor) {
    fgValColor = inColor;

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Set the new maximum. If the current value is bigger than the maximum it is set to null.
   *
   * @param inMaximum The new maximum.
   */
  public void setMaximum(final T inMaximum) {
    maximum = Objects.requireNonNull(inMaximum);

    if (minimum == null || minimum.doubleValue() > maximum.doubleValue()) {
      minimum = maximum;
      visible = false;
    }

    if (!hasValidValue()) {
      value = null;
    }

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Set the new minimum. If the current value is lesser than the minimum it is set to null.
   *
   * @param inMinimum The new minimum.
   */
  public void setMinimum(final T inMinimum) {
    minimum = Objects.requireNonNull(inMinimum);

    if (maximum == null || minimum.doubleValue() > maximum.doubleValue()) {
      maximum = minimum;
      visible = false;
    }

    if (!hasValidValue()) {
      value = null;
    }

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Sets the value.
   *
   * @param inValue The new value to set.
   */
  public void setValue(final T inValue) {
    value = inValue;

    if (!hasValidValue()) {
      value = null;
    }

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Sets the visibility mode for this interval.
   *
   * @param inVisible <code>true</code> if this interval should be displayed.
   */
  public void setVisible(final boolean inVisible) {
    visible = inVisible;

    if (owner != null) {
      owner.invalidate();
    }
  }

  /**
   * Sets the owner swing component.
   *
   * @param inOwner The owner of this limit.
   */
  void setOwner(final JComponent inOwner) {
    owner = inOwner;
  }
}
