package net.ghielmetti.utilities.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Makes given {@link JComboBox} editable and filterable.
 *
 * @author Somebody on internet.
 */
public class JComboBoxDecorator {
  private JComboBoxDecorator() {
    // nothing to do
  }

  /**
   * Decorate the specified {@link JComboBox} with the filter actions.
   *
   * @param inComboBox The {@link JComboBox} to decorate.
   * @param inEditable <code>true</code> if the {@link JComboBox} must be editable.
   */
  public static void decorate(final JComboBox<String> inComboBox, final boolean inEditable) {
    List<String> entries = new ArrayList<>();
    for (int i = 0; i < inComboBox.getItemCount(); i++) {
      entries.add(inComboBox.getItemAt(i));
    }
    decorate(inComboBox, inEditable, entries);
  }

  /**
   * Decorate the specified {@link JComboBox} with the filter actions.
   *
   * @param inComboBox The {@link JComboBox} to decorate.
   * @param inEditable <code>true</code> if the {@link JComboBox} must be editable.
   * @param inEntries The {@link Collection} of available entries for the {@link JComboBox}.
   */
  public static void decorate(final JComboBox<String> inComboBox, final boolean inEditable, final Collection<String> inEntries) {
    inComboBox.setEditable(inEditable);
    inComboBox.setModel(new DefaultComboBoxModel<>(inEntries.toArray(new String[inEntries.size()])));
    inComboBox.setSelectedItem(null);

    final JTextField textField = (JTextField)inComboBox.getEditor().getEditorComponent();

    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(final KeyEvent e) {
        SwingUtilities.invokeLater(() -> comboFilter(textField.getText(), inComboBox, inEntries));
      }
    });
  }

  /**
   * Build a list of entries that match the given filter.
   *
   * @param inEnteredText The text typed by the user.
   * @param inComboBox The {@link JComboBox} to filter.
   * @param inEntries The {@link Collection} of available entries for the {@link JComboBox}.
   */
  static void comboFilter(final String inEnteredText, final JComboBox<String> inComboBox, final Collection<String> inEntries) {
    List<String> entriesFiltered = new ArrayList<>();

    for (String entry : inEntries) {
      if (entry.toLowerCase().contains(inEnteredText.toLowerCase())) {
        entriesFiltered.add(entry);
      }
    }

    if (!entriesFiltered.isEmpty()) {
      DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(entriesFiltered.toArray(new String[entriesFiltered.size()]));
      inComboBox.setModel(model);
      inComboBox.setSelectedItem(inEnteredText);
      inComboBox.showPopup();
    } else {
      inComboBox.hidePopup();
    }
  }
}
