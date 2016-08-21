package net.ghielmetti.utilities;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class UnderlinedLabel extends JLabel {
  private static final long serialVersionUID = -2423236630049440763L;

  public UnderlinedLabel() {
    this("");
  }

  public UnderlinedLabel(final String text) {
    super(text);
  }

  @Override
  public void paint(final Graphics g) {
    Rectangle r;
    super.paint(g);
    r = g.getClipBounds();
    g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont()).stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
  }
}
