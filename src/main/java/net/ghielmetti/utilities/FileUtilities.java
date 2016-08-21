package net.ghielmetti.utilities;

import java.io.File;

public class FileUtilities {
  public final static String jpeg = "jpeg";
  public final static String jpg  = "jpg";
  public final static String gif  = "gif";
  public final static String tiff = "tiff";
  public final static String tif  = "tif";
  public final static String png  = "png";
  public final static String sh   = "sh";
  public final static String bat  = "bat";

  /*
   * Get the extension of a file.
   */
  public static String getExtension(final File inFile) {
    String ext = null;
    String s = inFile.getName();

    int i = s.lastIndexOf('.');

    if ((i > 0) && (i < (s.length() - 1))) {
      ext = s.substring(i + 1).toLowerCase();
    }

    return ext;
  }
}
