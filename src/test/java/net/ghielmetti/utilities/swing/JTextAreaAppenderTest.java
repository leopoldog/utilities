package net.ghielmetti.utilities.swing;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ghielmetti.utilities.log4j.JTextAreaAppender;

/**
 * Tests for {@link JTextAreaAppender}
 *
 * @author Leopoldo Ghielmetti
 */
public class JTextAreaAppenderTest extends JFrame {
  /** Tests {@link JTextAreaAppender}. */
  @Test
  public void testOK() {
    setLayout(new GridBagLayout());
    Logger textArea1 = LoggerFactory.getLogger("");
    Logger textArea2 = LoggerFactory.getLogger("LogTextArea2");
    JTextAreaPanel panel1 = JTextAreaAppender.getTextAreaPanel("textArea1");
    JTextAreaPanel panel2 = JTextAreaAppender.getTextAreaPanel("textArea2");
    add(panel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    add(panel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    setPreferredSize(new Dimension(400, 800));
    setLocationRelativeTo(null);
    pack();
    setVisible(true);

    for (int i = 0; i < 100; i++) {
      textArea1.debug("A message: {}", Integer.valueOf(i));
    }

    Thread.yield();

    assertEquals(3, panel1.getComponentCount());

    JTextArea textAreaComponent1 = panel1.getTextArea();
    JTextArea textAreaComponent2 = panel2.getTextArea();
    JCheckBox autoCheck2 = panel2.getAutoCheckBox();

    assertEquals(DefaultCaret.ALWAYS_UPDATE, ((DefaultCaret) textAreaComponent1.getCaret()).getUpdatePolicy());
    assertEquals(DefaultCaret.ALWAYS_UPDATE, ((DefaultCaret) textAreaComponent2.getCaret()).getUpdatePolicy());
    autoCheck2.doClick();
    Thread.yield();
    assertEquals(DefaultCaret.NEVER_UPDATE, ((DefaultCaret) textAreaComponent2.getCaret()).getUpdatePolicy());
    int firstPartTextLength = textAreaComponent1.getText().length();
    assertEquals(firstPartTextLength, ((DefaultCaret) textAreaComponent1.getCaret()).getDot());
    assertEquals(0, ((DefaultCaret) textAreaComponent2.getCaret()).getDot());

    for (int i = 100; i < 200; i++) {
      textArea2.debug("A message: {}", Integer.valueOf(i));
    }

    Thread.yield();
    int allTextLength = textAreaComponent1.getText().length();
    assertEquals(allTextLength, ((DefaultCaret) textAreaComponent1.getCaret()).getDot());
    assertEquals(0, ((DefaultCaret) textAreaComponent2.getCaret()).getDot());

    autoCheck2.setSelected(true);
    Thread.yield();
    assertEquals(textAreaComponent1.getText().length(), ((DefaultCaret) textAreaComponent1.getCaret()).getDot());
    assertEquals(textAreaComponent2.getText().length(), ((DefaultCaret) textAreaComponent2.getCaret()).getDot());

    panel1.getClearButton().doClick();
    Thread.yield();
    assertEquals(0, ((DefaultCaret) textAreaComponent1.getCaret()).getDot());
    panel2.getClearButton().doClick();
    Thread.yield();
    assertEquals(0, ((DefaultCaret) textAreaComponent2.getCaret()).getDot());
  }
}
