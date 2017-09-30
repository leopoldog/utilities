package net.ghielmetti.utilities.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * A rules component that can display many independent intervals with a specific value each.<br>
 * An interval is defined with a minimum value, a maximum value and a background {@link Color}.<br>
 * The value itself is displayed with the value {@link Color}.<br>
 * <br>
 * All values that are not in the minimum<->maximum range are not displayed.
 *
 * @author Leopoldo Ghielmetti
 * @param <T> The type of the displayed values
 */
public class JMultiValueRuler<T extends Number> extends JPanel {
  private static final int         TICK_UNIT    = 5;
  private static final int         RULER_LINE   = 10;
  private static final int         REVERSE_TICK = -2;
  private static final int         NO_TICK      = 0;
  private static final int         SHORT_TICK   = 1;
  private static final int         MEDIUM_TICK  = 2;
  private static final int         LONG_TICK    = 3;
  private T                        minimum;
  private T                        maximum;
  private Map<Object, Interval<T>> intervals    = new HashMap<>();
  private List<Object>             ids          = new ArrayList<>();
  private Dimension                dimension;
  private int                      baseSizeLarge;
  private int                      baseSizeMedium;
  private int                      baseSizeSmall;

  /**
   * Creates a new ruler with the specified minimum and maximum values.
   *
   * @param inMinimum The minimum allowed value.
   * @param inMaximum The maximum allowed value.
   */
  public JMultiValueRuler(final T inMinimum, final T inMaximum) {
    setBoundaries(inMinimum, inMaximum);
  }

  /**
   * Defines or replaces an interval with the specified values.
   *
   * @param inIntervalId An {@link Object} that can be used to refer to the specific intervals.<br>
   *          The interval adding order specifies the displaying order. The fist added interval is displayed under all
   *          others intervals.
   * @param inInterval The interval to add.
   */
  public void addInterval(final Object inIntervalId, final Interval<T> inInterval) {
    intervals.put(inIntervalId, inInterval);
    ids.add(inIntervalId);
    inInterval.setOwner(this);
    revalidate();
  }

  /** Removes all the intervals. */
  public void clear() {
    for (Interval<T> i : intervals.values()) {
      i.setOwner(null);
    }
    intervals.clear();
    ids.clear();
  }

  /**
   * Returns the maximum displayed value for this ruler.
   *
   * @return The maximum value.
   */
  public T getMaximum() {
    return maximum;
  }

  /**
   * Returns the minimum displayed value for this ruler.
   *
   * @return The minimum value.
   */
  public T getMinimum() {
    return minimum;
  }

  @Override
  public void paint(final Graphics inGraphics) {
    super.paint(inGraphics);

    // Ensures that the component has a meaning
    if (minimum.doubleValue() >= maximum.doubleValue()) {
      return;
    }

    Border border = getBorder();
    Rectangle clipBounds = inGraphics.getClipBounds();
    Insets insets;

    if (border != null) {
      insets = border.getBorderInsets(this);
      clipBounds.setSize(clipBounds.width - insets.left - insets.right, clipBounds.height - insets.bottom - insets.bottom);
      clipBounds.translate(insets.left, insets.top);
    } else {
      insets = new Insets(0, 0, 0, 0);
    }

    FontMetrics fm = inGraphics.getFontMetrics();
    double width = 0;

    width = Math.max(width, drawRuler(inGraphics, clipBounds, fm));
    width = Math.max(width, drawMark(inGraphics, clipBounds, minimum.doubleValue(), false, 0, NO_TICK, getForeground(), getBackground(), getForeground(), minimum.toString(), fm));
    width = Math.max(width, drawMark(inGraphics, clipBounds, maximum.doubleValue(), false, 0, NO_TICK, getForeground(), getBackground(), getForeground(), maximum.toString(), fm));
    width = Math.max(width, drawIntervalsTicksAndValues(inGraphics, clipBounds, fm));

    if (dimension == null) {
      dimension = new Dimension((int) width + insets.left + insets.right, 20);
    } else {
      dimension.setSize((int) width + insets.left + insets.right, 20);
    }

    if ((int) getPreferredSize().getWidth() != (int) dimension.getWidth()) {
      setPreferredSize(dimension);
      setMinimumSize(dimension);
    }
  }

  /**
   * Removes the specified interval from the intervals.
   *
   * @param inIntervalId The interval id.
   */
  public void removeInterval(final Object inIntervalId) {
    ids.remove(inIntervalId);
    intervals.remove(inIntervalId);
    SwingUtilities.invokeLater(this::repaint);
  }

  /**
   * Sets the ruler boundaries.
   *
   * @param inMinimum The minimum displayed value.
   * @param inMaximum The maximum displayed value.
   */
  public void setBoundaries(final T inMinimum, final T inMaximum) {
    minimum = inMinimum;
    maximum = inMaximum;

    if (minimum.doubleValue() < maximum.doubleValue()) {
      double size = Math.log10(maximum.doubleValue() - minimum.doubleValue());
      baseSizeLarge = (int) Math.pow(10, (int) size);
      baseSizeMedium = (int) Math.pow(10, (int) (size - 0.5));
      baseSizeSmall = (int) Math.pow(10, (int) (size - 1.5));

      if (baseSizeMedium == baseSizeLarge) {
        baseSizeMedium /= 2;
      }

      if (baseSizeMedium == baseSizeSmall) {
        baseSizeSmall /= 2;
      }
    }

    SwingUtilities.invokeLater(this::repaint);
  }

  private void drawIntervalBoxes(final Graphics inGraphics, final Rectangle inClipBounds, final FontMetrics inFontMetric) {
    for (Object id : ids) {
      Interval<T> interval = intervals.get(id);

      if (interval.isVisible()) {
        T min = getBoundedValue(interval.getMinimum());
        T max = getBoundedValue(interval.getMaximum());
        double factor = getFactor(inClipBounds, inFontMetric);
        int from = (int) ((min.doubleValue() - minimum.doubleValue()) * factor);
        int to = (int) ((max.doubleValue() - minimum.doubleValue()) * factor);

        inGraphics.setColor(interval.getBackground());
        inGraphics.fillRect(inClipBounds.x + 3, inClipBounds.y + inClipBounds.height - to - inFontMetric.getHeight() / 2, 3, to - from);
      }
    }
  }

  private double drawIntervalsTicksAndValues(final Graphics inGraphics, final Rectangle inClipBounds, final FontMetrics inFontMetric) {
    double width = 0;
    Font fontNormal = inGraphics.getFont();
    Font fontBold = fontNormal.deriveFont(Font.BOLD);

    drawIntervalBoxes(inGraphics, inClipBounds, inFontMetric);

    for (Object id : ids) {
      Interval<T> interval = intervals.get(id);

      if (interval.isVisible()) {
        T min = getBoundedValue(interval.getMinimum());
        T max = getBoundedValue(interval.getMaximum());
        width = Math.max(width, drawMark(inGraphics, inClipBounds, min.doubleValue(), false, 0, REVERSE_TICK, interval.getForeground(), getBackground(), interval.getForeground(), min.toString(), inFontMetric));
        width = Math.max(width, drawMark(inGraphics, inClipBounds, max.doubleValue(), false, 0, REVERSE_TICK, interval.getForeground(), getBackground(), interval.getForeground(), max.toString(), inFontMetric));

        if (interval.hasValidValue()) {
          inGraphics.setFont(fontBold);
          width = Math.max(width, drawMark(inGraphics, inClipBounds, interval.getValue().doubleValue(), true, 0, REVERSE_TICK, interval.getBackgroundValueColor(), interval.getBackgroundValueColor(), interval.getForegroundValueColor(),
              interval.hasValidValue() ? interval.getValue().toString() : null, inFontMetric));
        }

        inGraphics.setFont(fontNormal);
      }
    }

    return width;
  }

  private int drawMark(final Graphics inGraphics, final Rectangle inClipBounds, final double inValue, final boolean inWithArrow, final int inLineHeigth, final int inLineWidth, final Color inLineColor, final Color inBackgroundLabelColor,
      final Color inForegroundLabelColor, final String inLabel, final FontMetrics inFontMetric) {
    int width = 0;

    if (inValue >= minimum.doubleValue() && inValue <= maximum.doubleValue()) {
      int topMargin = inFontMetric.getHeight() / 2;
      double factor = getFactor(inClipBounds, inFontMetric);
      double val = (inValue - minimum.doubleValue()) * factor;
      int lineHalfHeight = inLineHeigth / 2;
      int posY = (int) (inClipBounds.y + inClipBounds.height - topMargin - val) + lineHalfHeight;

      width = Math.abs(inLineWidth) * TICK_UNIT;
      int sub = Math.max(-inLineWidth * TICK_UNIT, 0);

      inGraphics.setColor(inLineColor);
      inGraphics.fillRect(inClipBounds.x + RULER_LINE - sub, posY - lineHalfHeight, width, inLineHeigth + 1);

      if (inLabel != null) {
        int stringWidth = inFontMetric.stringWidth(inLabel);
        int textX = RULER_LINE + TICK_UNIT * LONG_TICK;
        int textXPos = inClipBounds.x + textX;

        if (inBackgroundLabelColor != null) {
          inGraphics.setColor(inBackgroundLabelColor);
          int altezza = inFontMetric.getMaxAscent() - 5;
          inGraphics.fillRect(textXPos, posY - topMargin, inClipBounds.width - textX, inFontMetric.getHeight());

          if (inWithArrow) {
            int[] px = new int[]{inClipBounds.x + RULER_LINE, //
                textXPos, //
                textXPos};
            int[] py = new int[]{posY, //
                posY - altezza, //
                posY + altezza};
            inGraphics.fillPolygon(px, py, 3);
          }
        }

        inGraphics.setColor(inForegroundLabelColor);
        inGraphics.drawChars(inLabel.toCharArray(), 0, inLabel.length(), textXPos, posY + topMargin - inFontMetric.getMaxDescent());
        width = Math.max(width, textX + stringWidth);
      }
    }

    return width + TICK_UNIT;
  }

  private double drawRuler(final Graphics inGraphics, final Rectangle inClipBounds, final FontMetrics inFontMetric) {
    double width = 0.0;

    if (inClipBounds.height >= 5) {
      int topMargin = inFontMetric.getHeight() / 2;
      int[] size = getRulerSizes(inClipBounds, inFontMetric.getHeight());

      inGraphics.drawLine(inClipBounds.x + RULER_LINE, inClipBounds.y + topMargin, inClipBounds.x + RULER_LINE, inClipBounds.y + inClipBounds.height - inFontMetric.getHeight() + topMargin);

      for (int i = (int) (minimum.doubleValue() / size[0]) * size[0]; i <= maximum.doubleValue(); i += size[0]) {
        int lineWidth;
        String label = null;

        if (i % size[2] == 0) {
          lineWidth = LONG_TICK;
          label = Integer.toString(i);
        } else if (i % size[1] == 0) {
          lineWidth = MEDIUM_TICK;
          label = Integer.toString(i);
        } else {
          lineWidth = SHORT_TICK;
        }

        width = Math.max(width, drawMark(inGraphics, inClipBounds, i, false, 0, lineWidth, getForeground(), getBackground(), getForeground(), label, inFontMetric));
      }
    }

    return width;
  }

  /** Returns a value included between the allowed minimum and maximum. */
  private T getBoundedValue(final T inValue) {
    if (inValue.doubleValue() < minimum.doubleValue()) {
      return minimum;
    }
    if (inValue.doubleValue() > maximum.doubleValue()) {
      return maximum;
    }
    return inValue;
  }

  private double getFactor(final Rectangle inClipBounds, final FontMetrics inFontMetric) {
    return (inClipBounds.height - inFontMetric.getHeight()) / (maximum.doubleValue() - minimum.doubleValue());
  }

  private int[] getRulerSizes(final Rectangle inClipBounds, final int inHeight) {
    int sizeLarge = baseSizeLarge;
    int sizeMedium = baseSizeMedium;
    int sizeSmall = baseSizeSmall;

    boolean mul = false;
    while (inClipBounds.height / (maximum.doubleValue() - minimum.doubleValue()) * sizeSmall <= inHeight / 10) {
      sizeSmall *= mul ? 2 : 5;
      mul = !mul;
    }

    sizeMedium = Math.max(sizeMedium, sizeSmall * (mul ? 2 : 5));
    mul = !mul;

    while (inClipBounds.height / (maximum.doubleValue() - minimum.doubleValue()) * sizeMedium <= inHeight) {
      sizeMedium = sizeMedium * (mul ? 2 : 5);
      mul = !mul;
    }

    sizeLarge = Math.max(sizeLarge, sizeMedium * (mul ? 2 : 5));
    mul = !mul;

    while (inClipBounds.height / (maximum.doubleValue() - minimum.doubleValue()) * sizeLarge <= inHeight) {
      sizeLarge = sizeLarge * (mul ? 2 : 5);
      mul = !mul;
    }

    return new int[]{sizeSmall, sizeMedium, sizeLarge};
  }
}
