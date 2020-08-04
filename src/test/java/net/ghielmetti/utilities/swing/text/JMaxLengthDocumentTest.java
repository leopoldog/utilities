package net.ghielmetti.utilities.swing.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.text.BadLocationException;
import org.junit.Test;

/**
 * Tests for {@link JMaxLengthDocument} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class JMaxLengthDocumentTest {
  /**
   * @throws BadLocationException
   */
  @Test
  public void testOK() throws BadLocationException {
    JMaxLengthDocument document1 = new JMaxLengthDocument(12);
    JMaxLengthDocument document2 = new JMaxLengthDocument(-1);

    // This test returns always true
    assertTrue(document1.checkIfValid("Test"));

    document1.insertString(0, "test1", null);
    document1.insertString(0, "test2", null);
    document1.insertString(0, "test3", null);

    document2.insertString(0, "test1", null);

    assertEquals("tetest2test1", document1.getText(0, document1.getLength()));
    assertEquals(0, document2.getLength());

    document1.replace(1, 10, "he big ", null);

    assertEquals("the big 1", document1.getText(0, document1.getLength()));

    document1.replace(8, 1, "2 too", null);

    assertEquals("the big 2 to", document1.getText(0, document1.getLength()));
  }
}
