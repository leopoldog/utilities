package net.ghielmetti.utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class TestQRCode {
  @SuppressWarnings("static-method")
  @Test
  public void testQRCode() throws IOException {
    FileOutputStream f = new FileOutputStream("/tmp/testQR.jpg");

    ByteArrayOutputStream s = QRCode.from("http://pm.ghielmetti.net:8080/polyweb/home.seam").to(ImageType.JPG).stream();

    s.writeTo(f);

    f.close();
  }
}
