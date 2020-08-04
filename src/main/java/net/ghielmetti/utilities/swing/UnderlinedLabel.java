package net.ghielmetti.utilities.swing;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;

/**
 * Creates a {@link JLabel} with underline.
 *
 * @author Leopoldo Ghielmetti
 */
public class UnderlinedLabel extends JLabel {
  private static final long serialVersionUID = -2423236630049440763L;

  /** Constructor. */
  public UnderlinedLabel() {
    this("");
  }

  /**
   * Constructor.
   *
   * @param inText The label's text.
   */
  public UnderlinedLabel(final String inText) {
    super(inText);
  }

  @Override
  public void paint(final Graphics inGraphics) {
    Rectangle r;
    super.paint(inGraphics);
    r = inGraphics.getClipBounds();
    inGraphics.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont()).stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
  }
}
