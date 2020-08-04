package net.ghielmetti.utilities.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.InputVerifier;
import org.junit.Test;

/**
 * Tests for {@link JNumberField}
 *
 * @author Leopoldo Ghielmetti
 */
public class JNumberFieldTest {
  /***/
  @Test
  public void testInputVerifier() {
    JNumberField field1 = new JNumberField(true, 8, 6);
    JNumberField field2 = new JNumberField(8, 6);
    JNumberField field3 = new JNumberField(true, 8);
    JNumberField field4 = new JNumberField(8);
    InputVerifier verifier = field1.getInputVerifier();

    assertEquals(Color.WHITE, field1.getOriginalBackgroundColor());

    field1.setText("1.23456");
    field1.setSelectionStart(3);
    field1.setSelectionEnd(4);
    field1.replaceSelection("1.234");

    // TODO This is strange, why the code accepts this value ? Maybe we have to
    // change the validation for the input characters.
    assertEquals("1.21.234456", field1.getText());

    verifier.verify(null);
    assertFalse(field1.isValid());
    assertEquals(Color.RED, field1.getBackground());
    assertEquals("1.21.234456", field1.getText());
    assertFalse(field1.isEmpty());

    assertTrue(field1.isValid("1234"));
    assertFalse(field1.isValid("abcd"));
    assertTrue(field1.isValid("+-.eE"));
    assertTrue(field3.isValid("+-"));
    assertFalse(field2.isValid("-"));
    assertFalse(field3.isValid(".eE"));
    assertFalse(field4.isValid("-"));

    field1.setText("111.111");
    verifier.verify(null);
    assertTrue(field1.isValid());
    assertEquals(Color.WHITE, field1.getBackground());
    assertEquals("111.111", field1.getText());
    assertFalse(field1.isEmpty());

    KeyEvent event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, 'A');
    field2.getKeyListeners()[0].keyTyped(event);
    assertTrue(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, '8');
    field2.getKeyListeners()[0].keyTyped(event);
    assertFalse(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, '-');
    field2.getKeyListeners()[0].keyTyped(event);
    assertTrue(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, '.');
    field2.getKeyListeners()[0].keyTyped(event);
    assertFalse(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, '+');
    field2.getKeyListeners()[0].keyTyped(event);
    assertFalse(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, 'e');
    field2.getKeyListeners()[0].keyTyped(event);
    assertFalse(event.isConsumed());
    event = new KeyEvent(field2, KeyEvent.KEY_TYPED, new Date().getTime(), 0, KeyEvent.VK_UNDEFINED, 'E');
    field2.getKeyListeners()[0].keyTyped(event);
    assertFalse(event.isConsumed());
  }

  /***/
  @Test(expected = NumberFormatException.class)
  public void testNOK1() {
    JNumberField field1 = new JNumberField(1);

    field1.setText(1.2);
  }

  /***/
  @Test
  public void testOK() {
    JNumberField field0 = new JNumberField(-1);
    JNumberField field1 = new JNumberField(-1, -1);
    JNumberField field2 = new JNumberField(true, -1, -1);
    JNumberField field3 = new JNumberField(5);
    JNumberField field4 = new JNumberField(true, 5);
    JNumberField field5 = new JNumberField(5, 4);
    JNumberField field6 = new JNumberField(true, 5, 4);

    assertEquals("(\\d{1,1})?", field0.getPattern().toString());
    assertEquals("(\\d{1,1})?", field1.getPattern().toString());
    assertEquals("-?(\\d{1,1})?", field2.getPattern().toString());
    assertEquals("(\\d{1,5})?", field3.getPattern().toString());
    assertEquals("-?(\\d{1,5})?", field4.getPattern().toString());
    assertEquals("(\\d{0,5}(\\.\\d{1,4})?)?", field5.getPattern().toString());
    assertEquals("-?(\\d{0,5}(\\.\\d{1,4})?)?", field6.getPattern().toString());

    field0.setText(1);
    assertEquals(1, field0.getNumber().intValue());
    field0.setText((String)null);
    assertEquals(0, field0.getNumber().intValue());
    field1.setText(1);
    assertEquals(1, field1.getNumber().intValue());
    field1.setText("");
    assertEquals(0, field1.getNumber().intValue());
    field2.setText(-1);
    assertEquals(-1, field2.getNumber().intValue());
    field2.setText((Number)null);
    assertEquals(0, field2.getNumber().intValue());
    field3.setText(12345);
    assertEquals(12345, field3.getNumber().intValue());
    field3.setText("-5");
    assertEquals(0, field3.getNumber().intValue());
    field3.setText("123456");
    assertEquals(0, field3.getNumber().intValue());
    field4.setText(-12345L);
    assertEquals(-12345, field4.getNumber().intValue());
    field5.setText(12345.6789D);
    assertEquals(12345.6789, field5.getNumber().doubleValue(), 1E-10);
    field5.setText("12345.6789");
    assertEquals(12345.6789, field5.getNumber().doubleValue(), 1E-10);
    field5.setText("-12345.6789");
    assertEquals(0, field5.getNumber().doubleValue(), 1E-10);
    field6.setText(-12345.6789F);
    assertEquals(-12345.679, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("-12345.6789");
    assertEquals(-12345.6789, field6.getNumber().doubleValue(), 1E-10);
    field6.setText(Double.valueOf(-12345.6789D));
    assertEquals(-12345.6789, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("1.23456789e4");
    assertEquals(12345.6789, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("1.23456789e10");
    assertEquals(0, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("123456789e-4");
    assertEquals(12345.6789, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("123456789e10");
    assertEquals(0, field6.getNumber().doubleValue(), 1E-10);
    field6.setText("abcd");
    assertEquals(0, field6.getNumber().intValue());
    assertTrue(field6.isValid());
    assertTrue(field6.isEmpty());
    field6.setText(12345.6789);
    assertNull(field6.getSelectedText());
    field6.getFocusListeners()[2].focusGained(null);
    field6.getFocusListeners()[2].focusLost(null);
    assertEquals("12345.6789", field6.getSelectedText());
    assertFalse(field6.isAllowedCharacter((char)0));
  }
}
