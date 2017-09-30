package net.ghielmetti.utilities.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import net.ghielmetti.utilities.log4j.JTextAreaAppender;

/**
 * A panel that display the informations from the {@link JTextAreaAppender}.
 *
 * @author Leopoldo Ghielmetti
 */
public class JTextAreaPanel extends JPanel {
  private JTextArea textArea;
  private JCheckBox auto;
  private JButton   clear;

  /** Constructor. */
  public JTextAreaPanel() {
    super(new GridBagLayout());
    initialize();
  }

  /**
   * Append a message into the JTextArea.
   *
   * @param inMessage The message to add
   */
  public synchronized void append(final String inMessage) {
    textArea.append(inMessage);

    if (auto.isSelected()) {
      textArea.setCaretPosition(textArea.getDocument().getLength());
    }
  }

  /** Clear the textArea */
  public void clear() {
    textArea.setText("");
  }

  /** Close the appender. */
  public void close() {
    removeAll();
    add(new JLabel("This appender is closed!"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
  }

  /**
   * Returns <code>true</code> if the auto scroll is on.
   *
   * @return <code>true</code> if auto scroll.
   */
  public boolean isAuto() {
    return auto.isSelected();
  }

  /**
   * Sets the auto scroll mode.
   *
   * @param inAuto <code>true</code> if auto scroll wanted.
   */
  public void setAuto(final boolean inAuto) {
    auto.setSelected(inAuto);
  }

  private void initialize() {
    textArea = new JTextArea();
    textArea.setEditable(false);

    JScrollPane scroll = new JScrollPane(textArea);

    clear = new JButton("Clear");
    clear.addActionListener(inEvent -> clear());

    auto = new JCheckBox("Auto scroll");
    auto.addItemListener(inEvent -> setCaretPolicy(isAuto()));
    // Force the Auto to true (the listener is invoked and the policy updated accordingly)
    auto.setSelected(true);

    add(scroll, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    add(auto, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    add(clear, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
  }

  /**
   * Defines the caret policy based on the auto update value
   *
   * @param inAutoUpdate true if the component is in auto update mode, false otherwise
   */
  private synchronized void setCaretPolicy(final boolean inAutoUpdate) {
    if (inAutoUpdate) {
      ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
      textArea.setCaretPosition(textArea.getDocument().getLength());
    } else {
      ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    }
  }

  /**
   * For test purposes.
   *
   * @return The auto scroll JCheckBox
   */
  JCheckBox getAutoCheckBox() {
    return auto;
  }

  /**
   * For test purposes.
   *
   * @return The clean JButton
   */
  JButton getClearButton() {
    return clear;
  }

  /**
   * For test purposes.
   *
   * @return The JTextArea
   */
  JTextArea getTextArea() {
    return textArea;
  }
}
