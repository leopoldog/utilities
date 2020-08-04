package net.ghielmetti.utilities.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * A panel that implements the Scrollable interface. This class allows you to customize the scrollable features by using
 * newly provided setter methods so you don't have to extend this class every time.<br>
 * <br>
 * Scrollable amounts can be specifed as a percentage of the viewport size or as an actual pixel value. The amount can
 * be changed for both unit and block scrolling for both horizontal and vertical scrollbars.<br>
 * <br>
 * The Scrollable interface only provides a boolean value for determining whether or not the viewport size (width or
 * height) should be used by the scrollpane when determining if scrollbars should be made visible. This class supports
 * the concept of dynamically changing this value based on the size of the viewport. In this case the viewport size will
 * only be used when it is larger than the panels size. This has the effect of ensuring the viewport is always full as
 * components added to the panel will be size to fill the area available, based on the rules of the applicable layout
 * manager of course.
 *
 * @author Leopoldo Ghielmetti
 */
public class ScrollablePanel extends JPanel implements Scrollable, SwingConstants {
  /**
   * Helper class to hold the information required to calculate the scroll amount.
   *
   * @author Leopoldo Ghielmetti
   */
  public static class IncrementInfo {
    private IncrementType type;
    private int           amount;

    /**
     * Defines an increment information.
     *
     * @param inType type on increment {@link IncrementType#PERCENT} or {@link IncrementType#PIXELS}.
     * @param inAmount amount of the increment.
     */
    public IncrementInfo(final IncrementType inType, final int inAmount) {
      type = inType;
      amount = inAmount;
    }

    /**
     * Getter for the amount.
     *
     * @return the amount.
     */
    public int getAmount() {
      return amount;
    }

    /**
     * Getter for the increment.
     *
     * @return the increment.
     */
    public IncrementType getIncrement() {
      return type;
    }

    @Override
    public String toString() {
      return "ScrollablePanel[" + type + ", " + amount + "]";
    }
  }

  /**
   * Type of increment.
   *
   * @author Leopoldo Ghielmetti
   */
  public enum IncrementType {
    /** The increment is defined as a percent of the full size. */
    PERCENT,
    /** The increment is defined in pixels. */
    PIXELS;
  }

  /**
   * How to define the size of the scrollable area.
   *
   * @author Leopoldo Ghielmetti
   */
  public enum ScrollableSizeHint {
    /***/
    NONE,
    /***/
    FIT,
    /***/
    STRETCH;
  }

  private static final String INVALID_ORIENTATION = "Invalid orientation: ";
  private static final long   serialVersionUID    = 5045389161307904568L;

  private ScrollableSizeHint scrollableHeight = ScrollableSizeHint.NONE;
  private ScrollableSizeHint scrollableWidth  = ScrollableSizeHint.NONE;
  private IncrementInfo      horizontalBlock;
  private IncrementInfo      horizontalUnit;
  private IncrementInfo      verticalBlock;
  private IncrementInfo      verticalUnit;

  /**
   * Default constructor that uses a FlowLayout
   */
  public ScrollablePanel() {
    this(new FlowLayout());
  }

  /**
   * Constructor for specifying the LayoutManager of the panel.
   *
   * @param layout the LayountManger for the panel
   */
  public ScrollablePanel(final LayoutManager layout) {
    super(layout);

    IncrementInfo block = new IncrementInfo(IncrementType.PERCENT, 100);
    IncrementInfo unit = new IncrementInfo(IncrementType.PERCENT, 10);

    setScrollableBlockIncrement(HORIZONTAL, block);
    setScrollableBlockIncrement(VERTICAL, block);
    setScrollableUnitIncrement(HORIZONTAL, unit);
    setScrollableUnitIncrement(VERTICAL, unit);
  }

  /**
   * Returns the increment value.
   *
   * @param inInfo The increment informations.
   * @param inDistance The distance to scroll.
   * @return The increment.
   */
  protected static int getScrollableIncrement(final IncrementInfo inInfo, final int inDistance) {
    if (inInfo.getIncrement() == IncrementType.PIXELS) {
      return inInfo.getAmount();
    }

    return inDistance * inInfo.getAmount() / 100;
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  /**
   * Get the block IncrementInfo for the specified orientation
   *
   * @param inOrientation
   * @return the block IncrementInfo for the specified orientation
   */
  public IncrementInfo getScrollableBlockIncrement(final int inOrientation) {
    return inOrientation == SwingConstants.HORIZONTAL ? horizontalBlock : verticalBlock;
  }

  @Override
  public int getScrollableBlockIncrement(final Rectangle visible, final int orientation, final int direction) {
    switch (orientation) {
      case SwingConstants.HORIZONTAL:
        return getScrollableIncrement(horizontalBlock, visible.width);
      case SwingConstants.VERTICAL:
        return getScrollableIncrement(verticalBlock, visible.height);
      default:
        throw new IllegalArgumentException(INVALID_ORIENTATION + orientation);
    }
  }

  /**
   * Get the height ScrollableSizeHint enum
   *
   * @return the ScrollableSizeHint enum for the height
   */
  public ScrollableSizeHint getScrollableHeight() {
    return scrollableHeight;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    if (scrollableHeight == ScrollableSizeHint.NONE) {
      return false;
    }

    if (scrollableHeight == ScrollableSizeHint.FIT) {
      return true;
    }

    // STRETCH sizing, use the greater of the panel or viewport height

    if (getParent() instanceof JViewport) {
      return ((JViewport)getParent()).getHeight() > getPreferredSize().height;
    }

    return false;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    if (scrollableWidth == ScrollableSizeHint.NONE) {
      return false;
    }

    if (scrollableWidth == ScrollableSizeHint.FIT) {
      return true;
    }

    // STRETCH sizing, use the greater of the panel or viewport width

    if (getParent() instanceof JViewport) {
      return ((JViewport)getParent()).getWidth() > getPreferredSize().width;
    }

    return false;
  }

  /**
   * Get the unit IncrementInfo for the specified orientation
   *
   * @param inOrientation
   * @return the unit IncrementInfo for the specified orientation
   */
  public IncrementInfo getScrollableUnitIncrement(final int inOrientation) {
    return inOrientation == SwingConstants.HORIZONTAL ? horizontalUnit : verticalUnit;
  }

  @Override
  public int getScrollableUnitIncrement(final Rectangle visible, final int orientation, final int direction) {
    switch (orientation) {
      case SwingConstants.HORIZONTAL:
        return getScrollableIncrement(horizontalUnit, visible.width);
      case SwingConstants.VERTICAL:
        return getScrollableIncrement(verticalUnit, visible.height);
      default:
        throw new IllegalArgumentException(INVALID_ORIENTATION + orientation);
    }
  }

  // Implement Scrollable interface

  /**
   * Get the width ScrollableSizeHint enum
   *
   * @return the ScrollableSizeHint enum for the width
   */
  public ScrollableSizeHint getScrollableWidth() {
    return scrollableWidth;
  }

  /**
   * Specify the information needed to do block scrolling.
   *
   * @param orientation specify the scrolling orientation. Must be either: SwingContants.HORIZONTAL or
   *          SwingContants.VERTICAL.
   * @param info An IncrementInfo object containing information of how to calculate the scrollable amount.
   */
  public void setScrollableBlockIncrement(final int orientation, final IncrementInfo info) {
    switch (orientation) {
      case SwingConstants.HORIZONTAL:
        horizontalBlock = info;
        break;
      case SwingConstants.VERTICAL:
        verticalBlock = info;
        break;
      default:
        throw new IllegalArgumentException(INVALID_ORIENTATION + orientation);
    }
  }

  /**
   * Specify the information needed to do block scrolling.
   *
   * @param inOrientation specify the scrolling orientation. Must be either: SwingContants.HORIZONTAL or
   *          SwingContants.VERTICAL.
   * @param inType specify how the amount parameter in the calculation of the scrollable amount. Valid values are:<br>
   *          IncrementType.PERCENT - treat the amount as a % of the viewport size<br>
   *          IncrementType.PIXEL - treat the amount as the scrollable amount
   * @param inAmount a value used with the IncrementType to determine the scrollable amount
   */
  public void setScrollableBlockIncrement(final int inOrientation, final IncrementType inType, final int inAmount) {
    IncrementInfo info = new IncrementInfo(inType, inAmount);
    setScrollableBlockIncrement(inOrientation, info);
  }

  /**
   * Set the ScrollableSizeHint enum for the height. The enum is used to determine the boolean value that is returned by
   * the getScrollableTracksViewportHeight() method. The valid values are:<br>
   * ScrollableSizeHint.NONE - return "false", which causes the height of the panel to be used when laying out the
   * children<br>
   * ScrollableSizeHint.FIT - return "true", which causes the height of the viewport to be used when laying out the
   * children<br>
   * ScrollableSizeHint.STRETCH - return "true" when the viewport height is greater than the height of the panel,
   * "false" otherwise.
   *
   * @param inScrollableHeight as represented by the ScrollableSizeHint enum.
   */
  public void setScrollableHeight(final ScrollableSizeHint inScrollableHeight) {
    scrollableHeight = inScrollableHeight;
    revalidate();
  }

  /**
   * Specify the information needed to do unit scrolling.
   *
   * @param orientation specify the scrolling orientation. Must be either: SwingContants.HORIZONTAL or
   *          SwingContants.VERTICAL.
   * @param info An IncrementInfo object containing information of how to calculate the scrollable amount.
   */
  public void setScrollableUnitIncrement(final int orientation, final IncrementInfo info) {
    switch (orientation) {
      case SwingConstants.HORIZONTAL:
        horizontalUnit = info;
        break;
      case SwingConstants.VERTICAL:
        verticalUnit = info;
        break;
      default:
        throw new IllegalArgumentException(INVALID_ORIENTATION + orientation);
    }
  }

  /**
   * Specify the information needed to do unit scrolling.
   *
   * @param inOrientation specify the scrolling orientation. Must be either: SwingContants.HORIZONTAL or
   *          SwingContants.VERTICAL.
   * @param inType specify how the amount parameter in the calculation of the scrollable amount. Valid values are:
   *          IncrementType.PERCENT - treat the amount as a % of the viewport size IncrementType.PIXEL - treat the
   *          amount as the scrollable amount
   * @param inAmount a value used with the IncrementType to determine the scrollable amount
   */
  public void setScrollableUnitIncrement(final int inOrientation, final IncrementType inType, final int inAmount) {
    IncrementInfo info = new IncrementInfo(inType, inAmount);
    setScrollableUnitIncrement(inOrientation, info);
  }

  /**
   * Set the ScrollableSizeHint enum for the width. The enum is used to determine the boolean value that is returned by
   * the getScrollableTracksViewportWidth() method. The valid values are:<br>
   * ScrollableSizeHint.NONE - return "false", which causes the width of the panel to be used when laying out the
   * children<br>
   * ScrollableSizeHint.FIT - return "true", which causes the width of the viewport to be used when laying out the
   * children<br>
   * ScrollableSizeHint.STRETCH - return "true" when the viewport width is greater than the width of the panel, "false"
   * otherwise.
   *
   * @param inScrollableWidth as represented by the ScrollableSizeHint enum.
   */
  public void setScrollableWidth(final ScrollableSizeHint inScrollableWidth) {
    scrollableWidth = inScrollableWidth;
    revalidate();
  }
}
