package net.ghielmetti.utilities.swing.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * Document with a specified max length. The length is controlled by the {@link MaxLengthDocumentFilter}.
 *
 * @author Leopoldo Ghielmetti
 */
public class JMaxLengthDocument extends PlainDocument {
  /**
   * DocumentFilter that won't let a too long string in the document content. Can be used by any Document that have the
   * setDocumentFilter() method.
   *
   * @author Leopoldo Ghielmetti
   */
  public static class MaxLengthDocumentFilter extends DocumentFilter {
    /** Max length authorized for the document */
    private int                maxlength;
    private JMaxLengthDocument owner;

    /**
     * Default Constructor with max length if not associated to a JFormattedTextField.
     *
     * @param inOwner The owner of the document filter
     * @param inMaxLength The maximum length of the document
     */
    public MaxLengthDocumentFilter(final JMaxLengthDocument inOwner, final int inMaxLength) {
      owner = inOwner;
      maxlength = inMaxLength;
    }

    @Override
    public void insertString(final FilterBypass inFb, final int inOffset, final String inText, final AttributeSet inAttr) throws BadLocationException {
      String text = inText;

      if (owner.checkIfValid(text)) {
        if (text != null && text.length() + inFb.getDocument().getLength() > maxlength) {
          text = text.substring(0, maxlength - inFb.getDocument().getLength());
        }

        super.insertString(inFb, inOffset, text, inAttr);
      }
    }

    @Override
    public void replace(final FilterBypass inFb, final int inOffset, final int inLength, final String inText, final AttributeSet inAttrs) throws BadLocationException {
      String text = inText;

      if (owner.checkIfValid(text)) {
        if (text != null && text.length() + inFb.getDocument().getLength() - inLength > maxlength) {
          text = text.substring(0, inLength + maxlength - inFb.getDocument().getLength());
        }

        super.replace(inFb, inOffset, inLength, text, inAttrs);
      }
    }
  }

  /**
   * Create a Document with a specified max length.
   *
   * @param inMaxLength The maximum length permitted in this document.
   */
  public JMaxLengthDocument(final int inMaxLength) {
    setDocumentFilter(new MaxLengthDocumentFilter(this, inMaxLength < 0 ? 0 : inMaxLength));
  }

  /**
   * Validate the given text
   *
   * @param inText The text to validate
   * @return <code>true</code>
   */
  public boolean checkIfValid(final String inText) {
    // We always return true for a generic text document
    return true;
  }
}
