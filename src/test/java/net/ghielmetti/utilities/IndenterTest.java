package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link Indenter} class.
 *
 * @author lghi
 */
public class IndenterTest {
  @Test
  public void append_allKindOfObjects_doesTheRightThing() {
    Indenter i = new Indenter(2);

    CharSequence cs = "12";

    i.append(true).forceNewLine();
    i.append(1).forceNewLine();
    i.append(2L).forceNewLine();
    i.append(3.0F).forceNewLine();
    i.append(4.0D).forceNewLine();
    i.append("5").forceNewLine();
    i.append('6').forceNewLine();
    i.append(new char[] {
        '7'
    }).forceNewLine();
    i.append(new char[] {
        '7', '8', '9'
    }, 1, 1).forceNewLine();
    i.append(Integer.valueOf(9)).forceNewLine();
    i.append(new StringBuffer("10")).forceNewLine();
    i.append(new StringBuilder("11")).forceNewLine();
    i.append(cs).forceNewLine();
    i.append("121314", 2, 4).forceNewLine();
    i.appendCodePoint(1234).forceNewLine();

    assertEquals("true\n" //
        + "1\n" //
        + "2\n" //
        + "3.0\n" //
        + "4.0\n" //
        + "5\n" //
        + "6\n" //
        + "7\n" //
        + "8\n" //
        + "9\n" //
        + "10\n" //
        + "11\n" //
        + "12\n" //
        + "13\n" //
        + "Ӓ\n", //
        i.toString());
  }

  @Test
  public void test1() {
    Indenter i = new Indenter(2);

    i.forceNewLine();
    i.checkNewLine().append("stmt0;").forceNewLine();
    i.forceNewLine();
    i.checkNewLine().append("for ").append("x ").append("in ").append("0..100 ");
    i.push().requestNewLine();
    i.append("{").push().forceNewLine();
    i.checkNewLine().append("stmt1;").forceNewLine();
    i.checkNewLine().append("stmt2;").forceNewLine();
    i.checkNewLine().append("").forceNewLine();
    i.pop().append("}").requestNewLine();
    i.pop().forceNewLine();
    i.checkNewLine().append("stmt3;").forceNewLine();

    assertEquals("stmt0;\n" //
        + "for x in 0..100 {\n" //
        + "  stmt1;\n" //
        + "  stmt2;\n" //
        + "}\n" //
        + "stmt3;\n", //
        i.toString());
  }

  @Test
  public void test2() {
    Indenter i = new Indenter(2);

    i.forceNewLine();
    i.checkNewLine().append("stmt0;").forceNewLine();
    i.forceNewLine();
    i.append("for ").append("x ").append("in ").append("0..100 ").push().requestNewLine();
    i.checkNewLine().append("stmt1;").forceNewLine();
    i.checkNewLine().append("stmt2;").forceNewLine();
    i.pop().requestNewLine().forceNewLine();
    i.checkNewLine().append("stmt3;").forceNewLine();

    assertEquals("stmt0;\n" //
        + "for x in 0..100\n" //
        + "  stmt1;\n" //
        + "  stmt2;\n" //
        + "stmt3;\n", //
        i.toString());
  }

  @Test
  public void test3() {
    Indenter i = new Indenter(2);

    i.checkNewLine().append("stmt0;").forceNewLine();
    i.append("a = (").push().set();
    i.checkNewLine().append("1,").requestNewLine();
    i.checkNewLine().append("2,").requestNewLine();
    i.checkNewLine().append("3").requestNewLine();
    i.pop().append(");").forceNewLine();
    i.checkNewLine().append("stmt1;").forceNewLine();
    i.set().append("stmt2;").forceNewLine();
    i.set(20).append("stmt3;").set(15).append("stmt4;").requestNewLine();
    i.checkNewLine().append("stmt5;");

    assertEquals("stmt0;\n" //
        + "a = (1,\n" //
        + "     2,\n" //
        + "     3);\n" //
        + "stmt1;\n" //
        + "stmt2;\n" //
        + "                    stmt3;stmt4;\n" //
        + "               stmt5;", //
        i.toString());
  }

  @Test
  public void test4() {
    Indenter i = new Indenter(2);

    Indentable in1 = new Indentable() {
      @Override
      public Indenter appendTo(final Indenter inIndenter) {
        return inIndenter.append("in1").requestNewLine();
      }

      @Override
      public int getPriority() {
        return 3;
      }
    };

    Indentable in2 = new Indentable() {
      @Override
      public Indenter appendTo(final Indenter inIndenter) {
        return inIndenter.append("in2").requestNewLine();
      }

      @Override
      public int getPriority() {
        return 2;
      }
    };

    Object[] list = new Object[] {
        "a", null, "b", in1, "c", in2, "d"
    };

    i.checkNewLine().append("var1 = ").push().set().append(list, Integer.MAX_VALUE, "/", false).append(";").pop().requestNewLine();
    i.checkNewLine().append(in1).checkNewLine().append((Indentable)null).forceNewLine().append(in2, 1).forceNewLine().append(in2, 2).forceNewLine().append(in2, 3).requestNewLine();
    i.checkNewLine().append((Iterable<?>)null, Integer.MAX_VALUE, "-", false).requestNewLine();
    i.checkNewLine().append("var2 = ").push().set().append(list, 2, "/", true).append(";").pop().forceNewLine();

    assertEquals("var1 = a/null/b/in1/\n" //
        + "       c/in2/\n" //
        + "       d;\n" //
        + "in1\n" //
        + "null\n" //
        + "(in2)\n" //
        + "in2\n" //
        + "in2\n" //
        + "null\n" //
        + "var2 = a/\n" //
        + "       null/\n" //
        + "       b/\n" //
        + "       (in1)/\n" //
        + "       c/\n" //
        + "       in2/\n" //
        + "       d;\n", //
        i.toString());
  }

  @Test
  public void toString_aValidIndenterWithText_indentsTheText() {
    Indenter i = new Indenter(2);

    i.append("{").push().requestNewLine() //
        .append('\\').requestNewLine().checkNewLine() //
        .append("AAA ").checkNewLine().requestNewLine().checkNewLine() //
        .append("BBB ").pop().forceNewLine() //
        .append("ciao", 1, 3).append("}").pop().forceNewLine();

    assertEquals("{\\\n" //
        + "  AAA\n" //
        + "  BBB\n" //
        + "ia}\n", //
        i.toString());
  }
}
