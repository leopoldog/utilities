package net.ghielmetti.utilities;

import java.util.Deque;
import java.util.LinkedList;

import org.apache.commons.lang3.tuple.MutablePair;

public class Indenter implements Appendable {
  private static final String                             NULL    = "null";
  private final Deque<MutablePair<String, StringBuilder>> lines   = new LinkedList<>();
  private final String                                    step;
  private boolean                                         newLine;
  private Deque<String>                                   amounts = new LinkedList<>();
  private boolean                                         used    = true;

  public Indenter(final int inStep) {
    step = new String(new char[inStep]).replace('\0', ' ');
    lines.add(new MutablePair<>(null, new StringBuilder()));
    amounts.push("");
  }

  public Indenter append(final boolean inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  @Override
  public Indenter append(final char inChar) {
    lines.peekLast().getRight().append(inChar);
    checkAmount();
    return this;
  }

  public Indenter append(final char[] inString) {
    lines.peekLast().getRight().append(inString);
    checkAmount();
    return this;
  }

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

  public Indenter append(final double inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  public Indenter append(final float inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  public Indenter append(final Indentable inIndentable) {
    if (inIndentable == null) {
      append(NULL);
    } else {
      inIndentable.appendTo(this);
    }
    return this;
  }

  public Indenter append(final int inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  public <T extends Indentable> Indenter append(final Iterable<T> inList) {
    return append(inList, ", ", false);
  }

  public <T extends Indentable> Indenter append(final Iterable<T> inList, final String inSeparator) {
    return append(inList, inSeparator, false);
  }

  public <T extends Indentable> Indenter append(final Iterable<T> inList, final String inSeparator, final boolean inNewLine) {
    if (inList == null) {
      append(NULL);
    } else {
      String separator = "";
      for (T ad : inList) {
        if (inNewLine) {
          ad.appendTo(append(separator).checkNewLine()).requestNewLine();
        } else {
          ad.appendTo(append(separator));
        }
        separator = inSeparator;
      }
    }
    return this;
  }

  public Indenter append(final long inNumber) {
    lines.peekLast().getRight().append(inNumber);
    checkAmount();
    return this;
  }

  public Indenter append(final Object inObject) {
    lines.peekLast().getRight().append(inObject);
    checkAmount();
    return this;
  }

  public Indenter append(final String inString) {
    lines.peekLast().getRight().append(inString);
    checkAmount();
    return this;
  }

  public Indenter append(final StringBuffer inStringBuffer) {
    lines.peekLast().getRight().append(inStringBuffer);
    checkAmount();
    return this;
  }

  public Indenter append(final StringBuilder inStringBuilder) {
    lines.peekLast().getRight().append(inStringBuilder);
    checkAmount();
    return this;
  }

  public <T extends Indentable> Indenter append(final T[] inList) {
    return append(inList, ", ", false);
  }

  public <T extends Indentable> Indenter append(final T[] inList, final String inSeparator) {
    return append(inList, inSeparator, false);
  }

  public <T extends Indentable> Indenter append(final T[] inList, final String inSeparator, final boolean inNewLine) {
    if (inList == null) {
      append(NULL);
    } else {
      String separator = "";
      for (T ad : inList) {
        if (inNewLine) {
          ad.appendTo(append(separator).checkNewLine()).requestNewLine();
        } else {
          ad.appendTo(append(separator));
        }
        separator = inSeparator;
      }
    }
    return this;
  }

  public Indenter appendCodePoint(final int inCodePoint) {
    lines.peekLast().getRight().appendCodePoint(inCodePoint);
    checkAmount();
    return this;
  }

  public Indenter checkNewLine() {
    if (newLine) {
      forceNewLine();
    }
    return this;
  }

  public Indenter forceNewLine() {
    newLine = false;
    if (lines.peekLast().getRight().length() != 0) {
      lines.add(new MutablePair<>(null, new StringBuilder()));
    }
    return this;
  }

  public Indenter pop() {
    amounts.pop();
    if (amounts.isEmpty()) {
      amounts.push("");
    }
    return this;
  }

  public Indenter push() {
    if (used) {
      amounts.push(amounts.peek() + step);
      used = false;
    } else {
      String indent = amounts.pop();
      amounts.push(amounts.peek());
      amounts.push(indent);
    }
    return this;
  }

  public Indenter requestNewLine() {
    newLine = true;
    return this;
  }

  public Indenter set() {
    int lenR = lines.peekLast().getRight().length();
    int lenL = lines.peekLast().getLeft() == null ? 0 : lines.peekLast().getLeft().length();
    if (lenR > 0) {
      amounts.pop();
    }
    amounts.push(new String(new char[lenR + lenL]).replace('\0', ' '));
    return this;
  }

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
