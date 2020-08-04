package net.ghielmetti.utilities.poi;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Class containing the styles for the Excel sheets.
 *
 * @author Leopoldo Ghielmetti
 */
public class Styles {
  private Workbook               workBook;
  private Map<Character, Font>   fontHidden = new HashMap<>();
  private Font                   fontBold;
  private DataFormat             dataFormat;
  private Map<String, CellStyle> stylesMap  = new HashMap<>();

  /**
   * Constructor.
   *
   * @param inWorkBook The workbook.
   */
  public Styles(final Workbook inWorkBook) {
    workBook = inWorkBook;
    dataFormat = inWorkBook.createDataFormat();
  }

  /**
   * The Style string is constructed concatenating the following letters:<br>
   * <dl>
   * <dt>color</dt>
   * <dl>
   * N (none), W (white), O (orange), T (turquoise), G (grey_25), r (red), g (green), b (blue)
   * </dl>
   * <dt>font</dt>
   * <dl>
   * N (normal), B (bold), H (hidden)
   * </dl>
   * <dt>alignment</dt>
   * <dl>
   * L (left), C (center), R (right), N (normal)
   * </dl>
   * <dt>rotation</dt>
   * <dl>
   * 0 (0°), 1 (90°), 2 (180°), 3 (270°)
   * </dl>
   * <dt>format</dt>
   * <dl>
   * V (variable), N (numeric), T (text)
   * </dl>
   * <dt>border</dt>
   * <dl>
   * t (top), l (left), b (bottom), r (right), n (not present) <br>
   * Always top-left-bottom-right order, uppercase means Thick border, lowercase Thin border.
   * </dl>
   * </dl>
   *
   * @param inStyle The style format string
   * @return The Style object for the POI Cells
   */
  public CellStyle getStyle(final String inStyle) {
    if (inStyle == null || inStyle.length() != 9) {
      throw new IllegalArgumentException("Invalid style name");
    }

    CellStyle cellStyle = stylesMap.get(inStyle);

    if (cellStyle == null) {
      cellStyle = workBook.createCellStyle();
      char color = inStyle.charAt(0);
      char fontWeight = inStyle.charAt(1);
      char alignment = inStyle.charAt(2);
      char rotation = inStyle.charAt(3);
      char format = inStyle.charAt(4);
      char borderTop = inStyle.charAt(5);
      char borderLeft = inStyle.charAt(6);
      char borderBottom = inStyle.charAt(7);
      char borderRight = inStyle.charAt(8);
      setColor(cellStyle, color);
      setFontWeight(inStyle, cellStyle, fontWeight);
      setAlignment(cellStyle, alignment);
      setRotation(cellStyle, rotation);
      setFormat(cellStyle, format);
      setBorder(cellStyle, borderTop, borderLeft, borderBottom, borderRight);
      stylesMap.put(inStyle, cellStyle);
    }

    return cellStyle;
  }

  /**
   * @param inCellStyle Cell style to set
   * @param inAlignment
   *          <ul>
   *          <li><b>L</b>: left</li>
   *          <li><b>C</b>: center</li>
   *          <li><b>R</b>: right</li>
   *          <li><b>N</b>: normal</li>
   *          </ul>
   */
  private void setAlignment(final CellStyle inCellStyle, final char inAlignment) {
    switch (inAlignment) {
      case 'L': // Left
        inCellStyle.setAlignment(HorizontalAlignment.LEFT);
        break;

      case 'C': // Center
        inCellStyle.setAlignment(HorizontalAlignment.CENTER);
        break;

      case 'R': // Right
        inCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        break;

      case 'N': // Normal
        break;

      default:
        throw new IllegalArgumentException("Invalid alignment \"" + inAlignment + "\", should be one of (L, C, R, N)");
    }
  }

  /**
   * Sets the border, always in top-left-bottom-right order.<br>
   * Uppercase for <b>Thick</b> border, lowercase for <b>Thin</b> border:<br>
   * <ul>
   * <li><b>t</b>: top</li>
   * <li><b>l</b>: left</li>
   * <li><b>b</b>: bottom</li>
   * <li><b>r</b>: right</li>
   * <li><b>n</b>: not present</li>
   * </ul>
   *
   * @param inCellStyle Cell style to set
   * @param inBorderTop The top border description (t, T or n)
   * @param inBorderLeft The left border description (l, L or n)
   * @param inBorderBottom The bottom border description (b, B or n)
   * @param inBorderRight The right border description (r, R or n)
   */
  private void setBorder(final CellStyle inCellStyle, final char inBorderTop, final char inBorderLeft, final char inBorderBottom, final char inBorderRight) {
    switch (inBorderTop) {
      case 't': // Thin
        inCellStyle.setBorderTop(BorderStyle.THIN);
        inCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'T': // Thick
        inCellStyle.setBorderTop(BorderStyle.THICK);
        inCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'n': // Not present
        break;

      default:
        throw new IllegalArgumentException("Invalid border top \"" + inBorderTop + "\", should be one of (t, T, n)");
    }

    switch (inBorderLeft) {
      case 'l': // Thin
        inCellStyle.setBorderLeft(BorderStyle.THIN);
        inCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'L': // Thick
        inCellStyle.setBorderLeft(BorderStyle.THICK);
        inCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'n': // Not present
        break;

      default:
        throw new IllegalArgumentException("Invalid border left \"" + inBorderLeft + "\", should be one of (l, L, n)");
    }

    switch (inBorderBottom) {
      case 'b': // Thin
        inCellStyle.setBorderBottom(BorderStyle.THIN);
        inCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'B': // Thick
        inCellStyle.setBorderBottom(BorderStyle.THICK);
        inCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'n': // Not present
        break;

      default:
        throw new IllegalArgumentException("Invalid border bottom \"" + inBorderBottom + "\", should be one of (b, B, n)");
    }

    switch (inBorderRight) {
      case 'r': // Thin
        inCellStyle.setBorderRight(BorderStyle.THIN);
        inCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'R': // Thick
        inCellStyle.setBorderRight(BorderStyle.THICK);
        inCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        break;

      case 'n': // Not present
        break;

      default:
        throw new IllegalArgumentException("Invalid border right \"" + inBorderRight + "\", should be one of (r, R, n)");
    }
  }

  /**
   * @param inCellStyle Cell style to set
   * @param inColor
   *          <ul>
   *          <li><b>W</b>: white</li>
   *          <li><b>O</b>: orange</li>
   *          <li><b>T</b>: turquoise</li>
   *          <li><b>G</b>: grey_25</li>
   *          </ul>
   */
  private void setColor(final CellStyle inCellStyle, final char inColor) {
    switch (inColor) {
      case 'N': // None
        break;

      case 'W': // white
        inCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'G': // gray
        inCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'O': // orange
        inCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'T': // Turquoise
        inCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'g': // green
        inCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'b': // blue
        inCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      case 'r': // red
        inCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        inCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        break;

      default:
        throw new IllegalArgumentException("Invalid color \"" + inColor + "\", should be one of (N, W, G, O, T, g, b, r)");
    }
  }

  private void setFontBold(final CellStyle inCellStyle) {
    if (fontBold == null) {
      fontBold = workBook.createFont();
      fontBold.setBold(true);
    }

    inCellStyle.setFont(fontBold);
  }

  private void setFontHidden(final CellStyle inCellStyle, final String inStyle) {
    Font hidden = fontHidden.get(Character.valueOf(inStyle.charAt(0)));

    if (hidden == null) {
      hidden = workBook.createFont();
      hidden.setColor(inCellStyle.getFillForegroundColor());
      fontHidden.put(Character.valueOf(inStyle.charAt(0)), hidden);
    }

    inCellStyle.setFont(hidden);
  }

  /**
   * @param inCellStyle Cell style to set
   * @param inFont
   *          <ul>
   *          <li><b>N</b>: normal</li>
   *          <li><b>B</b>: bold</li>
   *          <li><b>H</b>: hidden</li>
   *          </ul>
   */
  private void setFontWeight(final String inStyle, final CellStyle inCellStyle, final char inFontWeight) {
    switch (inFontWeight) {
      case 'B': // Bold
        setFontBold(inCellStyle);
        break;

      case 'H': // Hidden
        setFontHidden(inCellStyle, inStyle);
        break;

      case 'N': // Normal
        break;

      default:
        throw new IllegalArgumentException("Invalid font weight \"" + inFontWeight + "\", should be one of (B, H, N)");
    }
  }

  /**
   * @param inCellStyle Cell style to set
   * @param inFormat
   *          <ul>
   *          <li><b>V</b>: variable</li>
   *          <li><b>N</b>: numeric</li>
   *          <li><b>T</b>: text</li>
   *          </ul>
   */
  private void setFormat(final CellStyle inCellStyle, final char inFormat) {
    switch (inFormat) {
      case 'N': // Numeric
        inCellStyle.setDataFormat(dataFormat.getFormat("0.000#"));
        break;

      case 'T': // Text
        inCellStyle.setDataFormat(dataFormat.getFormat("@"));
        inCellStyle.setWrapText(true);
        break;

      case 'V': // Variable
        break;

      default:
        throw new IllegalArgumentException("Invalid format \"" + inFormat + "\", should be one of (N, T, V)");
    }
  }

  /**
   * @param inCellStyle Cell style to set
   * @param inRotation
   *          <ul>
   *          <li><b>0</b>: 0°</li>
   *          <li><b>1</b>: 90°</li>
   *          <li><b>2</b>: 180°</li>
   *          <li><b>3</b>: 270°</li>
   *          </ul>
   */
  private void setRotation(final CellStyle inCellStyle, final char inRotation) {
    switch (inRotation) {
      case '1': // 90°
        inCellStyle.setRotation((short)90);
        break;

      case '2': // 180°
        inCellStyle.setRotation((short)180);
        break;

      case '3': // 270°
        inCellStyle.setRotation((short)-90);
        break;

      case '0': // 0°
        break;

      default:
        throw new IllegalArgumentException("Invalid rotation \"" + inRotation + "\", should be one of (0, 1, 2, 3)");
    }
  }
}
