package net.ghielmetti.utilities.awt.layout;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.Test;

/**
 * Tests for {@link ThreePanelLayout}.
 *
 * @author lghi
 */
public class ThreePanelLayoutTest {
  private static final int       INSET_TOP             = 5;
  private static final int       INSET_LEFT            = 6;
  private static final int       INSET_BOTTOM          = 7;
  private static final int       INSET_RIGHT           = 8;
  private static final int       PANEL_HEIGHT          = 600;
  private static final int       PANEL_WIDTH           = 800;
  private static final Dimension WEST_MINIMUM_SIZE     = new Dimension(50, 20);
  private static final Dimension CENTER_MINIMUM_SIZE   = new Dimension(10, 10);
  private static final Dimension EAST_MINIMUM_SIZE     = new Dimension(30, 40);
  private static final Dimension WEST_PREFERRED_SIZE   = new Dimension(100, 200);
  private static final Dimension CENTER_PREFERRED_SIZE = new Dimension(20, 20);
  private static final Dimension EAST_PREFERRED_SIZE   = new Dimension(300, 80);

  /** Tests {@link ThreePanelLayout#addLayoutComponent(String, java.awt.Component)} */
  @Test(expected = NullPointerException.class)
  public void addLayoutComponent_aNullComponent_throwsAnException() {
    ThreePanelLayout panel = new ThreePanelLayout();
    panel.addLayoutComponent(null, new JLabel());
  }

  /***/
  @Test
  public void testOK() {
    ThreePanelLayout panel = new ThreePanelLayout();
    Container container = mock(Container.class);
    JPanel westPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel eastPanel = new JPanel();

    westPanel.setMinimumSize(WEST_MINIMUM_SIZE);
    centerPanel.setMinimumSize(CENTER_MINIMUM_SIZE);
    eastPanel.setMinimumSize(EAST_MINIMUM_SIZE);

    westPanel.setPreferredSize(WEST_PREFERRED_SIZE);
    centerPanel.setPreferredSize(CENTER_PREFERRED_SIZE);
    eastPanel.setPreferredSize(EAST_PREFERRED_SIZE);

    when(container.getInsets()).thenReturn(new Insets(INSET_TOP, INSET_LEFT, INSET_BOTTOM, INSET_RIGHT));
    when(container.getWidth()).thenReturn(PANEL_WIDTH);
    when(container.getHeight()).thenReturn(PANEL_HEIGHT);

    panel.layoutContainer(container);

    assertEquals(new Dimension(0, 0), westPanel.getSize());
    assertEquals(new Dimension(0, 0), centerPanel.getSize());
    assertEquals(new Dimension(0, 0), eastPanel.getSize());
    assertEquals(new Dimension(INSET_LEFT + INSET_RIGHT, INSET_TOP + INSET_BOTTOM), panel.minimumLayoutSize(container));
    assertEquals(new Dimension(INSET_LEFT + INSET_RIGHT, INSET_TOP + INSET_BOTTOM), panel.preferredLayoutSize(container));

    panel.addLayoutComponent(ThreePanelLayout.WEST, westPanel);
    panel.addLayoutComponent(ThreePanelLayout.CENTER, centerPanel);
    panel.addLayoutComponent(ThreePanelLayout.EAST, eastPanel);
    panel.addLayoutComponent("Something", new JLabel());

    panel.layoutContainer(container);
    assertEquals(new Dimension((PANEL_WIDTH - INSET_LEFT - INSET_RIGHT) / 2, PANEL_HEIGHT - INSET_TOP - INSET_BOTTOM), westPanel.getSize());
    assertEquals(new Dimension(CENTER_PREFERRED_SIZE.width, PANEL_HEIGHT - INSET_TOP - INSET_BOTTOM), centerPanel.getSize());
    assertEquals(new Dimension((PANEL_WIDTH - INSET_LEFT - INSET_RIGHT) / 2, PANEL_HEIGHT - INSET_TOP - INSET_BOTTOM), eastPanel.getSize());
    assertEquals(
        new Dimension(WEST_MINIMUM_SIZE.width + CENTER_MINIMUM_SIZE.width + EAST_MINIMUM_SIZE.width + INSET_LEFT + INSET_RIGHT, Math.max(Math.max(WEST_MINIMUM_SIZE.height, CENTER_MINIMUM_SIZE.height), EAST_MINIMUM_SIZE.height) + INSET_TOP + INSET_BOTTOM),
        panel.minimumLayoutSize(container));
    assertEquals(new Dimension(WEST_PREFERRED_SIZE.width + CENTER_PREFERRED_SIZE.width + EAST_PREFERRED_SIZE.width + INSET_LEFT + INSET_RIGHT,
        Math.max(Math.max(WEST_PREFERRED_SIZE.height, CENTER_PREFERRED_SIZE.height), EAST_PREFERRED_SIZE.height) + INSET_TOP + INSET_BOTTOM), panel.preferredLayoutSize(container));

    panel.removeLayoutComponent(westPanel);
    panel.removeLayoutComponent(eastPanel);
    panel.removeLayoutComponent(centerPanel);
    panel.removeLayoutComponent(container);
  }
}
