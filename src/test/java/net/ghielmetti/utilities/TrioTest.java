package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link Trio} class.
 *
 * @author Leopoldo Ghielmetti
 */
@Deprecated
@SuppressWarnings("deprecation")
public class TrioTest {
  /** Tests {@link Trio#Trio(Object, Object, Object)}. */
  @Test
  public void constructor_always_createsAnObject() {
    assertNotNull(new Trio<>("a", "b", "c"));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_equalObjects_returnsTrue() {
    assertTrue(new Trio<>("1", "2", "3").equals(new Trio<>("1", "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_equalObjectsN1_returnsTrue() {
    assertTrue(new Trio<>(null, "2", "3").equals(new Trio<>(null, "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_equalObjectsN2_returnsTrue() {
    assertTrue(new Trio<>("1", null, "3").equals(new Trio<>("1", null, "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_equalObjectsN3_returnsTrue() {
    assertTrue(new Trio<>("1", "2", null).equals(new Trio<>("1", "2", null)));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjects_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("a", "b", "c")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjects1_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("0", "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjects2_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("1", "0", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjects3_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("1", "2", "0")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN1_returnsFalse() {
    assertFalse(new Trio<>(null, "2", "3").equals(new Trio<>("1", "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN2_returnsFalse() {
    assertFalse(new Trio<>("1", null, "3").equals(new Trio<>("1", "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN3_returnsFalse() {
    assertFalse(new Trio<>("1", "2", null).equals(new Trio<>("1", "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN4_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>(null, "2", "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN5_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("1", null, "3")));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN6_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(new Trio<>("1", "2", null)));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_null_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(null));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_sameObjects_returnsTrue() {
    Trio<String, String, String> t = new Trio<>("1", "2", "3");
    assertTrue(t.equals(t));
  }

  /** Tests {@link Trio#equals(Object)}. */
  @Test
  public void equals_unrelatedObject_returnsFalse() {
    assertFalse(new Trio<>("1", "2", "3").equals(""));
  }

  /** Tests {@link Trio#getCenter()}. */
  @Test
  public void getCenter_always_returnsTheCenterElement() {
    assertEquals("b", new Trio<>("a", "b", "c").getCenter());
  }

  /** Tests {@link Trio#getLeft()}. */
  @Test
  public void getLeft_always_returnsTheLeftElement() {
    assertEquals("a", new Trio<>("a", "b", "c").getLeft());
  }

  /** Tests {@link Trio#getRight()}. */
  @Test
  public void getRight_always_returnsTheRightElement() {
    assertEquals("c", new Trio<>("a", "b", "c").getRight());
  }

  /** Tests {@link Trio#hashCode()}. */
  @Test
  public void hashCode_equalObjects_returnsSameHash() {
    assertEquals(new Trio<>("1", "2", "3").hashCode(), new Trio<>("1", "2", "3").hashCode());
  }

  /** Tests {@link Trio#hashCode()}. */
  @Test
  public void hashCode_equalObjects1_returnsSameHash() {
    assertEquals(new Trio<>(null, "2", "3").hashCode(), new Trio<>(null, "2", "3").hashCode());
  }

  /** Tests {@link Trio#hashCode()}. */
  @Test
  public void hashCode_equalObjects2_returnsSameHash() {
    assertEquals(new Trio<>("1", null, "3").hashCode(), new Trio<>("1", null, "3").hashCode());
  }

  /** Tests {@link Trio#hashCode()}. */
  @Test
  public void hashCode_equalObjects3_returnsSameHash() {
    assertEquals(new Trio<>("1", "2", null).hashCode(), new Trio<>("1", "2", null).hashCode());
  }

  /** Tests {@link Trio#hashCode()}. */
  @Test
  public void hashCode_notEqualObjects_returnsDifferentHash() {
    assertTrue(new Trio<>("1", "2", "3").hashCode() != new Trio<>("a", "b", "c").hashCode());
  }

  /** Tests {@link Trio#toString()}. */
  @Test
  public void toString_always_returnsAString() {
    assertEquals("(a,b,c)", new Trio<>("a", "b", "c").toString());
  }
}
