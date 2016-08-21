package net.ghielmetti.utilities;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Classe contenant les styles pour les feuilles Excel
 *
 * @author Leopoldo Ghielmetti
 *
 */
public class POIStyles {
  private Workbook               workBook;
  private Map<Character, Font>   fontHidden = new HashMap<>();
  private Font                   fontBold;
  private DataFormat             dataFormat;
  private Map<String, CellStyle> styles     = new HashMap<>();

  public POIStyles(final Workbook inWorkBook) {
    workBook = inWorkBook;
    dataFormat = inWorkBook.createDataFormat();
  }

  /**
   * The Style string is constructed concatenating the following letters:<br>
   * <dl>
   * <dt>couleur</dt>
   * <dl>
   * N (none), W (white), O (orange), T (turquois), G (grey_25), r (red), g
   * (green), b (blue)
   * </dl>
   * <dt>font</dt>
   * <dl>
   * N (normal), B (bold), H (hidden)
   * </dl>
   * <dt>alignement</dt>
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
   * Always top-left-bottom-right order, uppercase means Thick border, lowercase
   * Thin border.
   * </dl>
   * </dl>
   *
   * @param inStyle
   *          The style format string
   * @return The Style object for the POI Cells
   */
  public CellStyle getStyle(final String inStyle) {
    CellStyle s = styles.get(inStyle);

    if (s == null) {
      s = workBook.createCellStyle();

      // - couleur: W (white), O (orange), T (turquois), G (grey_25)
      switch (inStyle.charAt(0)) {
        case 'W': // white
          s.setFillForegroundColor(IndexedColors.WHITE.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'G': // gray
          s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'O': // orange
          s.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'T': // turquois
          s.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'g': // green
          s.setFillForegroundColor(IndexedColors.GREEN.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'b': // blue
          s.setFillForegroundColor(IndexedColors.BLUE.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        case 'r': // red
          s.setFillForegroundColor(IndexedColors.RED.getIndex());
          s.setFillPattern(CellStyle.SOLID_FOREGROUND);
          break;

        default: // none
          break;
      }

      // - font: N (normal), B (bold), H (hidden)
      switch (inStyle.charAt(1)) {
        case 'B': // bold
          if (fontBold == null) {
            fontBold = workBook.createFont();
            fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
          }

          s.setFont(fontBold);
          break;

        case 'H': // hidden
        {
          Font hidden = fontHidden.get(new Character(inStyle.charAt(0)));

          if (hidden == null) {
            hidden = workBook.createFont();
            hidden.setColor(s.getFillForegroundColor());
            fontHidden.put(new Character(inStyle.charAt(0)), hidden);
          }

          s.setFont(hidden);
        }
          break;

        case 'N': // normal
        default: // normal
          break;
      }

      // - alignement: L (left), C (center), R (right), N (normal)
      switch (inStyle.charAt(2)) {
        case 'L': // Left
          s.setAlignment(CellStyle.ALIGN_LEFT);
          break;

        case 'C': // Center
          s.setAlignment(CellStyle.ALIGN_CENTER);
          break;

        case 'R': // Right
          s.setAlignment(CellStyle.ALIGN_RIGHT);
          break;

        case 'N': // normal
        default: // normal
          break;
      }

      // - rotation: 0 (0°), 1 (90°), 2 (180°), 3 (270°)
      switch (inStyle.charAt(3)) {
        case '1': // 90°
          s.setRotation((short) 90);
          break;

        case '2': // 180°
          s.setRotation((short) 180);
          break;

        case '3': // 270°
          s.setRotation((short) 270);
          break;

        case '0': // 0°
        default: // 0°
          break;
      }

      // - format: V (variable), N (numeric), T (text)
      switch (inStyle.charAt(4)) {
        case 'N': // numeric
          s.setDataFormat(dataFormat.getFormat("0.000#"));
          break;

        case 'T': // text
          s.setDataFormat(dataFormat.getFormat("@"));
          s.setWrapText(true);
          break;

        case 'V': // variable
        default: // variable
          break;
      }

      // - border: always top-left-bottom-right order, uppercase Thick border,
      // lowercase Thin border
      // t (top), l (left), b (bottom), r (right), n (not present)
      switch (inStyle.charAt(5)) {
        case 't': // Thin
          s.setBorderTop(CellStyle.BORDER_THIN);
          s.setTopBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'T': // Thick
          s.setBorderTop(CellStyle.BORDER_THICK);
          s.setTopBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'n': // not present
        default: // not present
          break;
      }

      switch (inStyle.charAt(6)) {
        case 'l': // Thin
          s.setBorderLeft(CellStyle.BORDER_THIN);
          s.setLeftBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'L': // Thick
          s.setBorderLeft(CellStyle.BORDER_THICK);
          s.setLeftBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'n': // not present
        default: // not present
          break;
      }

      switch (inStyle.charAt(7)) {
        case 'b': // Thin
          s.setBorderBottom(CellStyle.BORDER_THIN);
          s.setBottomBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'B': // Thick
          s.setBorderBottom(CellStyle.BORDER_THICK);
          s.setBottomBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'n': // not present
        default: // not present
          break;
      }

      switch (inStyle.charAt(8)) {
        case 'r': // Thin
          s.setBorderRight(CellStyle.BORDER_THIN);
          s.setRightBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'R': // Thick
          s.setBorderRight(CellStyle.BORDER_THICK);
          s.setRightBorderColor(IndexedColors.BLACK.getIndex());
          break;

        case 'n': // not present
        default: // not present
          break;
      }

      styles.put(inStyle, s);
    }

    return s;
  }
}
