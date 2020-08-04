package net.ghielmetti.utilities;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 * An indenter for simple formulas.
 *
 * @author lghi
 */
public class Indenter implements Appendable {
  private static final String                             NULL    = "null";
  private final Deque<MutablePair<String, StringBuilder>> lines   = new LinkedList<>();
  private final String                                    indentingStep;
  private boolean                                         newLine;
  private Deque<String>                                   amounts = new LinkedList<>();
  private boolean                                         used    = true;

  /**
   * Constructor.
   *
   * @param inIndentingStep The indenting step (aka indentation level for one step).
   */
  public Indenter(final int inIndentingStep) {
    indentingStep = new String(new char[inIndentingStep]).replace('\0', ' ');
    lines.add(new MutablePair<>(null, new StringBuilder()));
    amounts.push("");
  }

  /**
   * Append a boolean to the formula to indent.
   *
   * @param inBoolean The boolean to append.
   * @return this
   */
  public Indenter append(final boolean inBoolean) {
    lines.peekLast().getRight().append(inBoolean);
    checkAmount();
    return this;
  }

  @Override
  public Indenter append(final char inChar) {
    lines.peekLast().getRight().append(inChar);
    checkAmount();
    return this;
  }

  /**
   * Append a string to the formula to indent.
   *
   * @param inString The string to append.
   * @return this
   */
  public Indenter append(final char[] inString) {
    lines.peekLast().getRight().append(inString);
    checkAmount();
    return this;
  }

  /**
   * Append a string to the formula to indent.
   *
   * @param inString The string to append.
   * @param inOffset The first character to append.
   * @param inLen The length.
   * @return this
   */
  public Indenter append(final char[] inString, final int inOffset, final int inLen) {
    lines.peekLast().getRight().append(inString, inOffset, inLen);
    checkAmount();
    return this;
  }

  @Override
  public Indenter append(final CharSequence inSequence) {
    lines.peekLast().getRight().append(inSequence);
    checkAmount();
    return this;
  }

  @Override
  public Indenter append(final CharSequence inSequence, final int inStart, final int inEnd) {
    lines.peekLast().getRight().append(inSequence, inStart, inEnd);
    checkAmount();
    return this;
  }

  /**
   * Append a double to the formula to indent.
   *
   * @param inNumber The double to append.
   * @return this
   */
  public Indenter append(final double inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  /**
   * Append a float to the formula to indent.
   *
   * @param inNumber The float to append.
   * @return this
   */
  public Indenter append(final float inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  /**
   * Append an {@link Indentable} to the formula to indent.
   *
   * @param inIndentable The {@link Indentable} to append.
   * @return this
   */
  public Indenter append(final Indentable inIndentable) {
    return append(inIndentable, Integer.MAX_VALUE);
  }

  /**
   * Append an {@link Indentable} to the formula to indent.
   *
   * @param inIndentable The {@link Indentable} to append.
   * @param inPriority The priority (add parenthesis if the {@link Indentable} priority is greater than the specified priority).
   * @return this
   */
  public Indenter append(final Indentable inIndentable, final int inPriority) {
    if (inIndentable == null) {
      append(NULL);
    } else {
      if (inPriority < inIndentable.getPriority()) {
        inIndentable.appendTo(append("(")).append(")");
      } else {
        inIndentable.appendTo(this);
      }
    }
    return this;
  }

  /**
   * Append an int to the formula to indent.
   *
   * @param inNumber The int to append.
   * @return this
   */
  public Indenter append(final int inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  /**
   * Append the specified {@link Iterable} to the formula to indent.
   *
   * @param <T> The type of the elements in the {@link Iterable}.
   * @param inIterable The {@link Iterable} to append.
   * @param inPriority The priority (add parenthesis if an {@link Indentable} priority is greater than the specified priority).
   * @param inSeparator The separator to use between elements.
   * @param inNewLine request to add a new line at the end of the formula.
   * @return this
   */
  public <T> Indenter append(final Iterable<T> inIterable, final int inPriority, final String inSeparator, final boolean inNewLine) {
    if (inIterable == null) {
      return append(NULL);
    }
    String separator = "";
    String sep = inSeparator == null ? "" : inSeparator;
    for (T ad : inIterable) {
      append(separator).checkNewLine();
      if (ad instanceof Indentable) {
        if (inPriority < ((Indentable)ad).getPriority()) {
          ((Indentable)ad).appendTo(append("(")).append(")");
        } else {
          ((Indentable)ad).appendTo(this);
        }
      } else {
        append(ad);
      }
      if (inNewLine) {
        requestNewLine();
      }
      separator = sep;
    }
    return this;
  }

  /**
   * Append an long to the formula to indent.
   *
   * @param inNumber The long to append.
   * @return this
   */
  public Indenter append(final long inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  /**
   * Append an {@link Object} to the formula to indent.
   *
   * @param inObject The {@link Object} to append.
   * @return this
   */
  public Indenter append(final Object inObject) {
    lines.peekLast().getRight().append(inObject);
    checkAmount();
    return this;
  }

  /**
   * Append a {@link String} to the formula to indent.
   *
   * @param inString The {@link String} to append.
   * @return this
   */
  public Indenter append(final String inString) {
    lines.peekLast().getRight().append(inString);
    checkAmount();
    return this;
  }

  /**
   * Append a {@link StringBuffer} to the formula to indent.
   *
   * @param inStringBuffer The {@link StringBuffer} to append.
   * @return this
   */
  public Indenter append(final StringBuffer inStringBuffer) {
    lines.peekLast().getRight().append(inStringBuffer);
    checkAmount();
    return this;
  }

  /**
   * Append a {@link StringBuilder} to the formula to indent.
   *
   * @param inStringBuilder The {@link StringBuilder} to append.
   * @return this
   */
  public Indenter append(final StringBuilder inStringBuilder) {
    lines.peekLast().getRight().append(inStringBuilder);
    checkAmount();
    return this;
  }

  /**
   * Append the specified array to the formula to indent.
   *
   * @param <T> The type of the elements in the array.
   * @param inArray The array to append.
   * @param inPriority The priority (add parenthesis if an {@link Indentable} priority is greater than the specified priority).
   * @param inSeparator The separator to use between elements.
   * @param inNewLine request to add a new line at the end of the formula.
   * @return this
   */
  public <T> Indenter append(final T[] inArray, final int inPriority, final String inSeparator, final boolean inNewLine) {
    return append(Arrays.asList(inArray), inPriority, inSeparator, inNewLine);
  }

  /**
   * Append a codePoint to the formula to indent.
   *
   * @param inCodePoint The codePoint to append.
   * @return this
   */
  public Indenter appendCodePoint(final int inCodePoint) {
    lines.peekLast().getRight().appendCodePoint(inCodePoint);
    checkAmount();
    return this;
  }

  /**
   * Add a new line if it was requested.
   *
   * @return this
   */
  public Indenter checkNewLine() {
    if (newLine) {
      forceNewLine();
    }
    return this;
  }

  /**
   * Force append a new line (even if it was not requested).
   *
   * @return this
   */
  public Indenter forceNewLine() {
    newLine = false;
    if (lines.peekLast().getRight().length() != 0) {
      lines.add(new MutablePair<>(null, new StringBuilder()));
    }
    return this;
  }

  /**
   * Exists one level of indenting.
   *
   * @return this
   */
  public Indenter pop() {
    amounts.pop();
    if (amounts.isEmpty()) {
      amounts.push("");
    }
    return this;
  }

  /**
   * Go inside one level of indenting.
   *
   * @return this
   */
  public Indenter push() {
    if (used) {
      amounts.push(amounts.peek() + indentingStep);
      used = false;
    } else {
      String indent = amounts.pop();
      amounts.push(amounts.peek());
      amounts.push(indent);
    }
    return this;
  }

  /**
   * Request a new line. It's honored if there is no already something that imply it.
   *
   * @return this
   */
  public Indenter requestNewLine() {
    newLine = true;
    return this;
  }

  /**
   * Sets the indentation level to the actual cursor position.
   *
   * @return this
   */
  public Indenter set() {
    int lenR = lines.peekLast().getRight().length();
    int lenL = lines.peekLast().getLeft() == null ? 0 : lines.peekLast().getLeft().length();
    if (lenR > 0) {
      amounts.pop();
    }
    amounts.push(new String(new char[lenR + lenL]).replace('\0', ' '));
    return this;
  }

  /**
   * Sets the indentation to the specified amount.
   *
   * @param inAmount The spaces to indent.
   * @return this
   */
  public Indenter set(final int inAmount) {
    int len = lines.peekLast().getRight().length();
    if (len > 0) {
      amounts.pop();
    }
    amounts.push(new String(new char[inAmount]).replace('\0', ' '));
    return this;
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    boolean eol = false;

    for (MutablePair<String, StringBuilder> line : lines) {
      if (eol) {
        out.append("\n");
        eol = false;
      }

      StringBuilder l = trim(line.getRight());

      if (l.length() > 0) {
        if (line.getLeft() != null) {
          out.append(line.getLeft());
        }

        out.append(l);
        eol = true;
      }
    }

    return out.toString();
  }

  private void checkAmount() {
    if (lines.peekLast().getLeft() == null && lines.peekLast().getRight().length() > 0) {
      lines.peekLast().setLeft(amounts.peek());
      used = true;
    }
  }

  private StringBuilder trim(final StringBuilder inStringBuilder) {
    while (inStringBuilder.length() > 0 && Character.isSpaceChar(inStringBuilder.charAt(inStringBuilder.length() - 1))) {
      inStringBuilder.deleteCharAt(inStringBuilder.length() - 1);
    }
    return inStringBuilder;
  }
}
