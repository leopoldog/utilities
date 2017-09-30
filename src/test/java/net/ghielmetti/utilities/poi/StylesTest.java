package net.ghielmetti.utilities.poi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link Styles} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class StylesTest {
  private Styles styles;

  /** Tests {@link Styles#Styles(Workbook)}. */
  @Test
  public void constructor_always_createsAnObject() {
    assertNotNull(styles);
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_aKnownValidStyleName_returnsTheSameStyleObject() {
    assertSame(styles.getStyle("WBR2Vtlbr"), styles.getStyle("WBR2Vtlbr"));
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleAlignments_returnsAStyle() {
    for (char alignment : "LCRN".toCharArray()) {
      assertNotNull(styles.getStyle("gN" + alignment + "2Vtlbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleBottomBorders_returnsAStyle() {
    for (char bottom : "bBn".toCharArray()) {
      assertNotNull(styles.getStyle("rHR0Ntl" + bottom + "r"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleColors_returnsAStyle() {
    for (char color : "NWOTGrgb".toCharArray()) {
      assertNotNull(styles.getStyle(color + "BR0Vtlbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleFonts_returnsAStyle() {
    for (char font : "NBH".toCharArray()) {
      assertNotNull(styles.getStyle("r" + font + "R1Vtlbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleFormats_returnsAStyle() {
    for (char format : "VNT".toCharArray()) {
      assertNotNull(styles.getStyle("bBN0" + format + "tlbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleLeftBorders_returnsAStyle() {
    for (char left : "lLn".toCharArray()) {
      assertNotNull(styles.getStyle("bBN0Nt" + left + "br"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleRightBorders_returnsAStyle() {
    for (char right : "rRn".toCharArray()) {
      assertNotNull(styles.getStyle("bBN0Vtlb" + right));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleRotations_returnsAStyle() {
    for (char rotation : "0123".toCharArray()) {
      assertNotNull(styles.getStyle("bBR" + rotation + "Vtlbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_allPossibleTopBorders_returnsAStyle() {
    for (char top : "tTn".toCharArray()) {
      assertNotNull(styles.getStyle("bBN0T" + top + "lbr"));
    }
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidAlignment_throwsAnException() {
    styles.getStyle("rBX2Vtlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidBorderBottom_throwsAnException() {
    styles.getStyle("rBR2VtlXr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidBorderLeft_throwsAnException() {
    styles.getStyle("rBR2VtXbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidBorderRight_throwsAnException() {
    styles.getStyle("rBR2VtlbX");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidBorderTop_throwsAnException() {
    styles.getStyle("rBR2VXlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidColor_throwsAnException() {
    styles.getStyle("XBR2Vtlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidFont_throwsAnException() {
    styles.getStyle("rXR2Vtlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidFormat_throwsAnException() {
    styles.getStyle("rBR2Xtlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidRotation_throwsAnException() {
    styles.getStyle("rBRXVtlbr");
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_anInvalidStyleName_throwsAnException() {
    assertNull(styles.getStyle("INVALID"));
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test(expected = IllegalArgumentException.class)
  public void getStyle_aNullStyleName_throwsAnException() {
    assertNull(styles.getStyle(null));
  }

  /** Tests {@link Styles#getStyle(String)}. */
  @Test
  public void getStyle_aValidStyleName_returnsTheStyle() {
    assertNotNull(styles.getStyle("WBR2Vtlbr"));
  }

  /**
   * Initializes the tests.
   *
   * @throws Exception Not expected.
   */
  @Before
  @SuppressWarnings("resource")
  public void setUp() throws Exception {
    styles = new Styles(new HSSFWorkbook());
  }
}
