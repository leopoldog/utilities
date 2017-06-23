package net.ghielmetti.utilities.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TextArea that is updated via the Log4j utility.<br>
 * <b>WARNING:</b> in the current implementation only one instance is allowed of
 * this component.<br>
 * <br>
 * <b>TODO IMPROVEMENT leghielm 20160531</b> This component must be improved to
 * be able to manage many log facilities with multiple components instances.
 */
public class TextAreaAppender extends WriterAppender {
  private static JTextArea        textArea;
  private static JCheckBox        auto;
  private static JScrollPane      scroll;
  private static JButton          clear;
  private static TextAreaAppender instance;
  private static Logger           logger;
  /**
   * Initializer for the TextAreaAppender.<br>
   * <b>WARNING:</b> in the current implementation only one instance is allowed
   * of this component.
   */
  {
    textArea = new JTextArea();
    scroll = new JScrollPane(textArea);
    auto = new JCheckBox("Auto scroll");
    clear = new JButton("Clear");
    textArea.setEditable(false);
    auto.addItemListener(inEvent -> setCaretPolicy(auto.isSelected()));
    clear.addActionListener(inEvent -> clear());

    // Force the Auto to true (the listener is invoked and the policy updated
    // accordingly)
    auto.setSelected(true);
  }

  /**
   * The constructor mustn't be invoked because this component is a
   * singleton.<br>
   * Actually the Log4J need the constructor be available, so it's visible.<br>
   */
  public TextAreaAppender() {
    // nothing to do
  }

  /**
   * Clear the textArea
   */
  public static void clear() {
    textArea.setText("");
  }

  /**
   * Returns the instance of the TextAreaAppender
   *
   * @return The TextAreaAppender
   */
  public static TextAreaAppender getInstance() {
    if (instance == null) {
      instance = new TextAreaAppender();
    }

    return instance;
  }

  /**
   * This LOGGER is used to write into the JTextArea, all the messages are
   * logged on the "LOG" facility that can be configured into the log4j.xml
   * file.
   *
   * @return The logger
   */
  public static Logger getLogger() {
    if (logger == null) {
      logger = LoggerFactory.getLogger("LOG");
    }
    return logger;
  }

  /**
   * Returns a {@link JComponent} containing this TextAreaAppender component.
   *
   * @return The panel
   */
  public static JComponent getTextAreaPanel() {
    // This is done just to force the initialization of an instance. Unuseful if
    // the singleton is implemented correctly.
    // TODO IMPROVEMENT leghielm 20160531 To remove when we reimplement a better
    // class
    getInstance();

    JPanel panel = new JPanel(new GridBagLayout());

    panel.add(scroll, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    panel.add(auto, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    panel.add(clear, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));

    return panel;
  }

  /**
   * Append a message into the JTextArea.
   *
   * @param inMessage
   *          The message to add
   */
  private static synchronized void append(final String inMessage) {
    textArea.append(inMessage);

    if (auto.isSelected()) {
      textArea.setCaretPosition(textArea.getDocument().getLength());
    }
  }

  /**
   * Defines the caret policy based on the auto update value
   *
   * @param inAutoUpdate
   *          true if the component is in auto update mode, false otherwise
   */
  private static synchronized void setCaretPolicy(final boolean inAutoUpdate) {
    if (inAutoUpdate) {
      ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
      textArea.setCaretPosition(textArea.getDocument().getLength());
    } else {
      ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    }
  }

  @Override
  public void append(final LoggingEvent inLoggingEvent) {
    String message = layout.format(inLoggingEvent);

    // Append formatted message to textArea using the Swing Thread.
    SwingUtilities.invokeLater(() -> append(message));
  }

  JCheckBox getAutoCheckBox() {
    return auto;
  }

  JButton getClearButton() {
    return clear;
  }

  JTextArea getTextArea() {
    return textArea;
  }
}