package net.ghielmetti.utilities.awt.layout;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;

/**
 * Defines an oriented Gridlayout.
 *
 * @author Leopoldo Ghielmetti
 */
public class OrientedGridLayout extends GridLayout {
  /**
   * The orientation possibilities.
   *
   * @author Leopoldo Ghielmetti
   */
  public enum VOrientation {
    /** The Components are placed in as top to bottom order. */
    TOP_BOTTOM,

    /** The Components are placed in as bottom to top order. */
    BOTTOM_TOP
  }

  private static final long serialVersionUID = -6065966057531121688L;
  private VOrientation      vOrientation     = VOrientation.TOP_BOTTOM;

  /** Creates an oriented grid layout with a default of one column per component, in a single row. */
  public OrientedGridLayout() {
    super();
  }

  /**
   * Creates an oriented grid layout with the specified number of rows and columns. All components in the layout are
   * given equal size.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can be zero, which means that any number of objects
   * can be placed in a row or in a column.
   *
   * @param inRows the rows, with the value zero meaning any number of rows.
   * @param inColumns the columns, with the value zero meaning any number of columns.
   */
  public OrientedGridLayout(final int inRows, final int inColumns) {
    super(inRows, inColumns);
  }

  /**
   * Creates an oriented grid layout with the specified number of rows and columns. All components in the layout are
   * given equal size.
   * <p>
   * In addition, the horizontal and vertical gaps are set to the specified values. Horizontal gaps are placed between
   * each of the columns. Vertical gaps are placed between each of the rows.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can be zero, which means that any number of objects
   * can be placed in a row or in a column.
   * <p>
   * All <code>GridLayout</code> constructors defer to this one.
   *
   * @param inRows the rows, with the value zero meaning any number of rows
   * @param inColumns the columns, with the value zero meaning any number of columns
   * @param inHGap the horizontal gap
   * @param inVGap the vertical gap
   * @exception IllegalArgumentException if the value of both <code>rows</code> and <code>cols</code> is set to zero
   */
  public OrientedGridLayout(final int inRows, final int inColumns, final int inHGap, final int inVGap) {
    super(inRows, inColumns, inHGap, inVGap);
  }

  /**
   * Creates an oriented grid layout with the specified number of rows and columns. All components in the layout are
   * given equal size.
   * <p>
   * In addition, the horizontal and vertical gaps are set to the specified values. Horizontal gaps are placed between
   * each of the columns. Vertical gaps are placed between each of the rows.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can be zero, which means that any number of objects
   * can be placed in a row or in a column.
   * <p>
   * All <code>GridLayout</code> constructors defer to this one.
   *
   * @param inRows the rows, with the value zero meaning any number of rows
   * @param inColumns the columns, with the value zero meaning any number of columns
   * @param inHGap the horizontal gap
   * @param inVGap the vertical gap
   * @param inVOrientation the grid orientation
   * @exception IllegalArgumentException if the value of both <code>rows</code> and <code>cols</code> is set to zero
   */
  public OrientedGridLayout(final int inRows, final int inColumns, final int inHGap, final int inVGap, final VOrientation inVOrientation) {
    super(inRows, inColumns, inHGap, inVGap);

    vOrientation = inVOrientation;
  }

  /**
   * Creates an oriented grid layout with the specified number of rows and columns. All components in the layout are
   * given equal size.
   * <p>
   * One, but not both, of <code>rows</code> and <code>cols</code> can be zero, which means that any number of objects
   * can be placed in a row or in a column.
   *
   * @param inRows the rows, with the value zero meaning any number of rows.
   * @param inColumns the columns, with the value zero meaning any number of columns.
   * @param inVOrientation the grid orientation
   */
  public OrientedGridLayout(final int inRows, final int inColumns, final VOrientation inVOrientation) {
    super(inRows, inColumns);

    vOrientation = inVOrientation;
  }

  /**
   * Creates an oriented grid layout with a default of one column per component, in a single row.
   *
   * @param inVOrientation the grid orientation
   */
  public OrientedGridLayout(final VOrientation inVOrientation) {
    super();

    vOrientation = inVOrientation;
  }

  /**
   * Getter for the VOrientation property.
   *
   * @return The VOrientation value.
   */
  public VOrientation getVOrientation() {
    return vOrientation;
  }

  @Override
  public void layoutContainer(final Container parent) {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int nrows = getRows();
      int ncols = getColumns();
      boolean ltr = parent.getComponentOrientation().isLeftToRight();

      if (ncomponents == 0) {
        return;
      }

      if (nrows > 0) {
        ncols = (ncomponents + nrows - 1) / nrows;
      } else {
        nrows = (ncomponents + ncols - 1) / ncols;
      }

      // To position components in the center we should:
      // 1. get an amount of extra space within Container
      // 2. incorporate half of that value to the left/top position
      // Note that we use truncating division for widthOnComponent
      // The reminder goes to extraWidthAvailable
      int totalGapsWidth = (ncols - 1) * getHgap();
      int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
      int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
      int extraWidthAvailable = (widthWOInsets - (widthOnComponent * ncols + totalGapsWidth)) / 2;

      int totalGapsHeight = (nrows - 1) * getVgap();
      int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
      int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
      int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nrows + totalGapsHeight)) / 2;
      int startx;
      int starty;
      int incx;
      int incy;

      if (ltr) {
        startx = insets.left + extraWidthAvailable;
        incx = widthOnComponent + getHgap();
      } else {
        startx = parent.getWidth() - insets.right - widthOnComponent - extraWidthAvailable;
        incx = -widthOnComponent - getHgap();
      }

      if (vOrientation == VOrientation.TOP_BOTTOM) {
        starty = insets.top + extraHeightAvailable;
        incy = heightOnComponent + getVgap();
      } else {
        starty = parent.getHeight() - insets.bottom - heightOnComponent - extraHeightAvailable;
        incy = -heightOnComponent - getVgap();
      }

      for (int c = 0, x = startx; c < ncols; c++, x += incx) {
        for (int r = 0, y = starty; r < nrows; r++, y += incy) {
          int i = r * ncols + c;
          if (i < ncomponents) {
            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
          }
        }
      }
    }
  }

  /**
   * Setter for the VOrientation property.
   *
   * @param inVOrientation the VOrientation value.
   */
  public void setVOrientation(final VOrientation inVOrientation) {
    vOrientation = inVOrientation;
  }
}
