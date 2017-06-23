package net.ghielmetti.utilities.swing;

import static org.junit.Assert.assertEquals;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.junit.Test;

/**
 * Tests for {@link TextAreaAppender}
 *
 * @author lghi
 */
public class JTextAreaAppenderTest {
  /**
   * @throws InterruptedException
   */
  @Test
  public void testOK() throws InterruptedException {
    TextAreaAppender textArea = TextAreaAppender.getInstance();

    assertEquals(textArea, TextAreaAppender.getInstance());

    TextAreaAppender.clear();

    for (int i = 0; i < 100; i++) {
      TextAreaAppender.getLogger().debug("A message: {}", Integer.valueOf(i));
    }

    Thread.sleep(1000);

    JComponent panel = TextAreaAppender.getTextAreaPanel();

    panel.setSize(200, 400);

    assertEquals(3, panel.getComponentCount());

    JTextArea textAreaComponent = textArea.getTextArea();
    JButton clearButton = textArea.getClearButton();
    JCheckBox autoCheck = textArea.getAutoCheckBox();

    assertEquals(DefaultCaret.ALWAYS_UPDATE, ((DefaultCaret) textAreaComponent.getCaret()).getUpdatePolicy());
    autoCheck.doClick();
    Thread.sleep(1000);
    assertEquals(DefaultCaret.NEVER_UPDATE, ((DefaultCaret) textAreaComponent.getCaret()).getUpdatePolicy());
    int firstPartTextLength = textAreaComponent.getText().length();
    assertEquals(firstPartTextLength, ((DefaultCaret) textAreaComponent.getCaret()).getDot());

    for (int i = 100; i < 200; i++) {
      TextAreaAppender.getLogger().debug("A message: {}", Integer.valueOf(i));
    }

    Thread.sleep(1000);
    assertEquals(firstPartTextLength, ((DefaultCaret) textAreaComponent.getCaret()).getDot());

    autoCheck.setSelected(true);
    Thread.sleep(1000);
    assertEquals(textAreaComponent.getText().length(), ((DefaultCaret) textAreaComponent.getCaret()).getDot());

    clearButton.doClick();
    Thread.sleep(1000);
    assertEquals(0, ((DefaultCaret) textAreaComponent.getCaret()).getDot());
  }
}
