package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link ResourceList} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class ResourceListTest {
  /**
   * Tests {@link Translations} constructor inaccessible.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void constructor_isPrivate_createsAnObject() throws Exception {
    Constructor<?>[] constructors = ResourceList.class.getDeclaredConstructors();
    assertEquals(1, constructors.length);

    assertFalse(constructors[0].isAccessible());

    // For coverage only, we call the constructor!
    constructors[0].setAccessible(true);
    constructors[0].newInstance((Object[]) null);
  }

  /** Tests {@link ResourceList#getResources(Pattern)}. */
  @Test
  public void getResources_aPattern_returnsResourcesMatchingThePattern() {
    Collection<ResourceEntry> resources = ResourceList.getResources(Pattern.compile(".*/pom.properties"));
    for (ResourceEntry entry : resources) {
      assertTrue(entry.getName().endsWith("/pom.properties"));
    }
  }

  /** Tests {@link ResourceList#getResources(Pattern)}. */
  @Test(expected = NullPointerException.class)
  public void getResources_nullPattern_throwsANullPointerException() {
    ResourceList.getResources(null);
  }
}
