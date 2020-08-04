package net.ghielmetti.utilities.awt.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A {@link LayoutManager} that creates two panel ({@link #WEST} and {@link #EAST}) with same horizontal size and a
 * panel ({@link #CENTER}) with a fixed horizontal size.
 *
 * @author Leopoldo Ghielmetti
 */
public class ThreePanelLayout implements LayoutManager {
  /** Identifies the West panel */
  public static final String WEST   = "West";
  /** Identifies the Center panel */
  public static final String CENTER = "Center";
  /** Identifies the East panel */
  public static final String EAST   = "East";

  private Component _westComponent;
  private Component _centerComponent;
  private Component _eastComponent;

  @Override
  public void addLayoutComponent(final String inName, final Component inComp) {
    switch (inName) {
      case WEST:
        _westComponent = inComp;
        break;
      case CENTER:
        _centerComponent = inComp;
        break;
      case EAST:
        _eastComponent = inComp;
        break;
      default:
        break;
    }
  }

  @Override
  public void layoutContainer(final Container inTarget) {
    synchronized (inTarget.getTreeLock()) {
      Insets insets = inTarget.getInsets();
      int top = insets.top;
      int bottom = inTarget.getHeight() - insets.bottom;
      int left = insets.left;
      int right = inTarget.getWidth() - insets.right;
      int wingsWidth = (right - left - (_centerComponent != null ? _centerComponent.getWidth() : 0)) / 2;

      if (_eastComponent != null) {
        _eastComponent.setBounds(right - wingsWidth, top, wingsWidth, bottom - top);
      }

      if (_westComponent != null) {
        _westComponent.setBounds(left, top, wingsWidth, bottom - top);
      }

      if (_centerComponent != null) {
        _centerComponent.setSize(_centerComponent.getWidth(), bottom - top);
        Dimension d = _centerComponent.getPreferredSize();
        _centerComponent.setBounds(wingsWidth + insets.left, top, d.width, bottom - top);
      }
    }
  }

  @Override
  public Dimension minimumLayoutSize(final Container inTarget) {
    synchronized (inTarget.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);

      if (_eastComponent != null) {
        Dimension d = _eastComponent.getMinimumSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      if (_centerComponent != null) {
        Dimension d = _centerComponent.getMinimumSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      if (_westComponent != null) {
        Dimension d = _westComponent.getMinimumSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      Insets insets = inTarget.getInsets();
      dim.width += insets.left + insets.right;
      dim.height += insets.top + insets.bottom;

      return dim;
    }
  }

  @Override
  public Dimension preferredLayoutSize(final Container inTarget) {
    synchronized (inTarget.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);

      if (_eastComponent != null) {
        Dimension d = _eastComponent.getPreferredSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      if (_centerComponent != null) {
        Dimension d = _centerComponent.getPreferredSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      if (_westComponent != null) {
        Dimension d = _westComponent.getPreferredSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      Insets insets = inTarget.getInsets();
      dim.width += insets.left + insets.right;
      dim.height += insets.top + insets.bottom;

      return dim;
    }
  }

  @Override
  public void removeLayoutComponent(final Component inComponent) {
    synchronized (inComponent.getTreeLock()) {
      if (inComponent == _centerComponent) {
        _centerComponent = null;
      } else if (inComponent == _eastComponent) {
        _eastComponent = null;
      } else if (inComponent == _westComponent) {
        _westComponent = null;
      }
    }
  }
}
