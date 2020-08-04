package net.ghielmetti.utilities.swing;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComboBox;

/**
 * A scroller for input objects.<br>
 * For now he only supports {@link JComboBox} and {@link JNumberField} with int/long values.
 *
 * @author Leopoldo Ghielmetti
 */
public class Scroller implements MouseWheelListener {
  private Number min;
  private Number max;

  /** Constructor. */
  public Scroller() {
    // nothing to do
  }

  /**
   * Constructor.
   *
   * @param inMin The minimum value (or <code>null</code> if no minimum).
   * @param inMax The maximum value (or <code>null</code> if no maximum).
   */
  public Scroller(final Number inMin, final Number inMax) {
    min = inMin;
    max = inMax;
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent inMouseWeelEvent) {
    if (inMouseWeelEvent.getSource() instanceof JComboBox) {
      scrollComboBox(inMouseWeelEvent);
    } else if (inMouseWeelEvent.getSource() instanceof JNumberField) {
      scrollNumber(inMouseWeelEvent);
    }
  }

  private void scrollComboBox(final MouseWheelEvent inMouseWeelEvent) {
    JComboBox<?> combo = (JComboBox<?>)inMouseWeelEvent.getSource();
    int index = combo.getSelectedIndex() + inMouseWeelEvent.getWheelRotation();

    if (index >= 0 && index < combo.getItemCount()) {
      combo.setSelectedIndex(index);
    }
  }

  private void scrollNumber(final MouseWheelEvent inMouseWeelEvent) {
    JNumberField field = (JNumberField)inMouseWeelEvent.getSource();
    int ctrl = (inMouseWeelEvent.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0 ? 100 : 1;
    int shift = (inMouseWeelEvent.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0 ? 10 : 1;
    long value = field.getNumber().longValue() + inMouseWeelEvent.getWheelRotation() * ctrl * shift;

    if (min != null && value < min.longValue()) {
      value = min.longValue();
    }

    if (max != null && value > max.longValue()) {
      value = max.longValue();
    }

    field.setText(value);
  }
}
