package net.ghielmetti.utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.junit.Test;

/**
 * Test the QR-Code generator.<br>
 * This test was made only as a reminder on how we use this library. :-)
 *
 * @author Leopoldo Ghielmetti
 */
public class QRCodeTest {
  /**
   * Creates a dummy QR-Code
   *
   * @throws IOException Not expected
   */
  @Test
  public void testQRCode() throws IOException {
    try (FileOutputStream f = new FileOutputStream("/tmp/testQR.jpg")) {
      ByteArrayOutputStream s = QRCode.from("https://github.com").to(ImageType.JPG).stream();
      s.writeTo(f);
    }
  }
}
