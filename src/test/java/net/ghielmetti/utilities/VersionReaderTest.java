package net.ghielmetti.utilities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for {@link VersionReader} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class VersionReaderTest {
  private static final Logger LOG = LoggerFactory.getLogger(VersionReaderTest.class);
  private VersionReader       versionReader;

  /** Tests {@link VersionReader#getTime(Pair)}. */
  @Test
  public void getTime_aBadPackageIdentifier_returnsNull() {
    assertNull(versionReader.getTime(Pair.of("a.wrong.package", "name")));
  }

  /** Tests {@link VersionReader#getTime(Pair)}. */
  @Test
  public void getTime_aGoodPackageIdentifier_returnsThePackageCreationTime() {
    for (Pair<String, String> pckg : versionReader.getPackages()) {
      Long time = versionReader.getTime(pckg);
      LOG.debug("{}: {}", pckg, new Date(time.longValue()));
      assertNotNull(time);
    }
  }

  /** Tests {@link VersionReader#getVersion(Pair)}. */
  @Test
  public void getVersion_aBadPackageIdentifier_returnsNull() {
    assertNull(versionReader.getVersion(Pair.of("a.wrong.package", "name")));
  }

  /** Tests {@link VersionReader#getVersion(Pair)}. */
  @Test
  public void getVersion_aGoodPackageIdentifier_returnsThePackageCreationTime() {
    for (Pair<String, String> pckg : versionReader.getPackages()) {
      String version = versionReader.getVersion(pckg);
      LOG.debug("{}: {}", pckg, version);
      assertNotNull(version);
    }
  }

  /** Initializes the tests. */
  @Before
  public void setUp() {
    versionReader = new VersionReader();
  }

  /** Tests {@link VersionReader#toString()}. */
  @Test
  public void toString_always_returnsAString() {
    assertNotNull(versionReader.toString());
  }
}
