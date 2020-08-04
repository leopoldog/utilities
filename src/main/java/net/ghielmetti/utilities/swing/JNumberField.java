package net.ghielmetti.utilities.swing;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import net.ghielmetti.utilities.swing.text.JMaxLengthDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description : number field derived from {@link JTextField}.
 *
 * @author Leopoldo Ghielmetti
 */
public class JNumberField extends JTextField {
  private static final Logger LOGGER  = LoggerFactory.getLogger(JNumberField.class);
  private Color               originalBackground;
  private boolean             isSigned;
  private int                 nbInt;
  private int                 nbFlt;
  private String              good;
  private boolean             isValid = true;
  private Pattern             pattern;

  /**
   * Constructor for an integer number
   *
   * @param inSigned <code>true</code> if the number is signed
   * @param inNbInt number of digits
   */
  public JNumberField(final boolean inSigned, final int inNbInt) {
    this(inSigned, inNbInt, 0);
  }

  /**
   * Constructor for a float number
   *
   * @param inSigned <code>true</code> if the number is signed
   * @param inNbInt number of digits of the integer part
   * @param inNbFlt number of digits of the floating part
   */
  public JNumberField(final boolean inSigned, final int inNbInt, final int inNbFlt) {
    super();

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(final KeyEvent inEvent) {
        if (!isAllowedCharacter(inEvent.getKeyChar())) {
          inEvent.consume();
        }
      }
    });

    addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(final FocusEvent inEvent) {
        selectAll();
      }
    });

    isSigned = inSigned;
    nbInt = Math.max(inNbInt, 1);
    nbFlt = Math.max(inNbFlt, 0);

    int size = nbInt + nbFlt + (nbFlt > 0 ? 1 : 0) + (isSigned ? 1 : 0);

    if (nbFlt == 0) {
      pattern = Pattern.compile((inSigned ? "-?" : "") + "(\\d{1," + nbInt + "})?");
    } else {
      pattern = Pattern.compile((inSigned ? "-?" : "") + "(\\d{0," + nbInt + "}(\\.\\d{1," + nbFlt + "})?)?");
    }

    originalBackground = getBackground();

    setColumns(size);

    setDocument(new JMaxLengthDocument(size) {
      @Override
      public boolean checkIfValid(final String inText) {
        return isValid(inText);
      }
    });

    setInputVerifier(new InputVerifier() {
      @Override
      public boolean verify(final JComponent input) {
        setValid(getPattern().matcher(getText()).matches());

        if (isValid()) {
          setBackground(getOriginalBackgroundColor());
        } else {
          setBackground(Color.RED);
        }

        return isValid();
      }
    });
  }

  /**
   * Constructor for an unsigned integer number
   *
   * @param inNbInt number of integer digits
   */
  public JNumberField(final int inNbInt) {
    this(false, inNbInt);
  }

  /**
   * Constructor for an unsigned float number
   *
   * @param inNbInt number of digits of the integer part
   * @param inNbFlt number of digits of the floating part
   */
  public JNumberField(final int inNbInt, final int inNbFlt) {
    this(false, inNbInt, inNbFlt);
  }

  /**
   * Check of length and signature of the textual double as arg
   *
   * @param inText the textual Double to check
   * @param inIntegerDigits the number of digits for the integer part
   * @param inFractionDigits the number of digits for the floating part, SQL NOTATION !!!!
   * @param inSigned sign allowed ?
   * @return the Double if test ok, else null
   */
  private static Number toDoubleNumber(final String inText, final int inIntegerDigits, final int inFractionDigits, final boolean inSigned) {
    String str = inText;
    int numi = inIntegerDigits;
    int numf = inFractionDigits;

    try {
      Double d = Double.valueOf(str);
      str = d.toString(); // if this line passes, we already have a double,
      // we're sure that .123 is now 0.123

      if (str.charAt(0) == '-') { // sign -
        if (inSigned) {
          numi++;
        } else {
          LOGGER.error("Sign error");
          return null;
        }
      }

      int pnt = str.indexOf('.'); // index of the dot on str
      int exp = str.indexOf('E'); // index of the E (exp) on str
      int nx = 0;

      if (exp < 0) {
        // 1.234 or 1234
        exp = str.length();
      } else {
        String spt = str.substring(exp + 1, str.length());
        // number of exponent chars, which shifts requested sizes for integer or
        // floating parts
        nx = Integer.parseInt(spt);
        numi = numi - nx;
        numf = numf + nx;
      }

      if (pnt > numi || exp - pnt - 1 > numf) {
        // size check
        LOGGER.error("Size error");
        return null;
      }

      return d;
    } catch (NumberFormatException e) {
      LOGGER.error("Conversion error: {}", e.getMessage());
      return null;
    }
  }

  /**
   * Check of length and signature of the integer as arg
   *
   * @param inText the integer to check
   * @param inMaxDigits the number of digits
   * @param inSigned sign allowed ?
   * @return true if test ok, else false
   */
  private static Number toLongNumber(final String inText, final int inMaxDigits, final boolean inSigned) {
    int max = inMaxDigits;
    long number = Long.parseLong(inText);

    if (number < 0) { // sign
      if (inSigned) {
        // can be negative
        max++;
      } else {
        return null;
      }
    }

    if (Long.toString(number).length() > max) {
      return null;
    }

    return Long.valueOf(number);
  }

  /**
   * Returns get current value of the field
   *
   * @return The value of the field
   */
  public Number getNumber() {
    try {
      String value = good;

      if (isValid()) {
        value = super.getText();
      }

      if (nbFlt != 0) {
        return Double.valueOf(value);
      }

      return Long.valueOf(value);
    } catch (NumberFormatException e) {
      LOGGER.debug("{}, returning 0.", e.getMessage());
      return Long.valueOf(0);
    }
  }

  /**
   * <code>true</code> if the field is empty
   *
   * @return if the field is empty
   */
  public boolean isEmpty() {
    if (isValid()) {
      return getText().length() == 0;
    }

    return good == null || good.length() == 0;
  }

  @Override
  public boolean isValid() {
    return isValid;
  }

  /**
   * Set the value of the field to the specified value
   *
   * @param inNum The new value for the field
   */
  public void setText(final double inNum) {
    setText(Double.toString(inNum));
  }

  /**
   * Set the value of the field to the specified value
   *
   * @param inNum The new value for the field
   */
  public void setText(final float inNum) {
    setText(Float.toString(inNum));
  }

  /**
   * Set the value of the field to the specified value
   *
   * @param inNum The new value for the field
   */
  public void setText(final int inNum) {
    setText(Integer.toString(inNum));
  }

  /**
   * Set the value of the field to the specified value
   *
   * @param inNum The new value for the field
   */
  public void setText(final long inNum) {
    setText(Long.toString(inNum));
  }

  /**
   * Set the value of the field to the specified value
   *
   * @param inNum The new value for the field
   */
  public void setText(final Number inNum) {
    setText(inNum == null ? "" : inNum.toString());
  }

  /**
   * Sets the field to the ip address provided as arg, else fill with a blank IP
   *
   * @param inText ip address as a String
   */
  @Override
  public void setText(final String inText) {
    if (inText != null && !inText.isEmpty()) {
      Number value;

      if (nbFlt == 0) {
        value = toLongNumber(inText, nbInt, isSigned);
      } else {
        value = toDoubleNumber(inText, nbInt, nbFlt, isSigned);
      }

      if (value != null) {
        String v = value.toString();

        if (!v.equals(super.getText())) {
          super.setText(value.toString());
        }
      } else if (!"".equals(super.getText())) {
        super.setText("");
      }
    } else if (!"".equals(super.getText())) {
      super.setText("");
    }

    setValid(true);
    good = super.getText();
    setBackground(originalBackground);
  }

  /**
   * Returns the original background color of this component
   *
   * @return The color
   */
  Color getOriginalBackgroundColor() {
    return originalBackground;
  }

  /**
   * Returns the pattern used to validate the number
   *
   * @return The pattern
   */
  Pattern getPattern() {
    return pattern;
  }

  /**
   * Returns true if the character is allowed for a numeric representation.
   *
   * @param inCharacter The character to check.
   * @return <code>true</code> if valid.
   */
  boolean isAllowedCharacter(final char inCharacter) {
    // Disallow special characters
    if (Character.isIdentifierIgnorable(inCharacter)) {
      return false;
    }

    // All the numeric and "+" characters are allowed
    if ("+0123456789".indexOf(inCharacter) != -1) {
      return true;
    }

    // The "-" character is allowed only if the number allows negative values
    if (inCharacter == '-' && isSigned) {
      return true;
    }

    // Scientific notation symbols are allowed only if we have a float number
    return (inCharacter == '.' || inCharacter == 'e' || inCharacter == 'E') && nbFlt != 0;
  }

  /**
   * Returns <code>true</code> if all the characters in the string are allowed
   *
   * @param inValue The string value to test
   * @return if the value has only allowed characters
   */
  boolean isValid(final String inValue) {
    for (int i = 0; i < inValue.length(); i++) {
      if (!isAllowedCharacter(inValue.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Set the valid flag
   *
   * @param inValid the new valid flag value
   */
  void setValid(final boolean inValid) {
    isValid = inValid;
  }
}
