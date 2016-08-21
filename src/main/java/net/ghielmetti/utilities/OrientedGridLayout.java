package net.ghielmetti.utilities;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;

public class OrientedGridLayout extends GridLayout {
  public enum VOrientation {
    TOP_BOTTOM, BOTTOM_TOP
  }

  private static final long serialVersionUID = -6065966057531121688L;
  private VOrientation      vOrientation     = VOrientation.TOP_BOTTOM;

  public OrientedGridLayout() {
    super();
  }

  public OrientedGridLayout(final int inRows, final int inColumns) {
    super(inRows, inColumns);
  }

  public OrientedGridLayout(final int inRows, final int inCols, final int inHGap, final int inVGap) {
    super(inRows, inCols, inHGap, inVGap);
  }

  public OrientedGridLayout(final int inRows, final int inCols, final int inHGap, final int inVGap, final VOrientation inVOrientation) {
    super(inRows, inCols, inHGap, inVGap);

    vOrientation = inVOrientation;
  }

  public OrientedGridLayout(final int inRows, final int inColumns, final VOrientation inVOrientation) {
    super(inRows, inColumns);

    vOrientation = inVOrientation;
  }

  public OrientedGridLayout(final VOrientation inVOrientation) {
    super();

    vOrientation = inVOrientation;
  }

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
        ncols = ((ncomponents + nrows) - 1) / nrows;
      } else {
        nrows = ((ncomponents + ncols) - 1) / ncols;
      }

      // 4370316. To position components in the center we should:
      // 1. get an amount of extra space within Container
      // 2. incorporate half of that value to the left/top position
      // Note that we use truncating division for widthOnComponent
      // The reminder goes to extraWidthAvailable
      int totalGapsWidth = (ncols - 1) * getHgap();
      int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
      int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
      int extraWidthAvailable = (widthWOInsets - ((widthOnComponent * ncols) + totalGapsWidth)) / 2;

      int totalGapsHeight = (nrows - 1) * getVgap();
      int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
      int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
      int extraHeightAvailable = (heightWOInsets - ((heightOnComponent * nrows) + totalGapsHeight)) / 2;
      int startx;
      int starty;
      int incx;
      int incy;

      if (ltr) {
        startx = insets.left + extraWidthAvailable;
        incx = widthOnComponent + getHgap();
      } else {
        startx = (parent.getWidth() - insets.right - widthOnComponent) - extraWidthAvailable;
        incx = -widthOnComponent - getHgap();
      }

      if (vOrientation == VOrientation.TOP_BOTTOM) {
        starty = insets.top + extraHeightAvailable;
        incy = heightOnComponent + getVgap();
      } else {
        starty = (parent.getHeight() - insets.bottom - heightOnComponent) - extraHeightAvailable;
        incy = -heightOnComponent - getVgap();
      }

      for (int c = 0, x = startx; c < ncols; c++, x += incx) {
        for (int r = 0, y = starty; r < nrows; r++, y += incy) {
          int i = (r * ncols) + c;
          if (i < ncomponents) {
            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
          }
        }
      }
    }
  }

  public void setVOrientation(final VOrientation inVOrientation) {
    vOrientation = inVOrientation;
  }
}
