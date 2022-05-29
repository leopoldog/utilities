package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link Pair} class.
 *
 * @author Leopoldo Ghielmetti
 */
@Deprecated
public class PairTest {
  /** Tests {@link Pair#Pair(Object, Object)}. */
  @Test
  public void constructor_always_createsAnObject() {
    assertNotNull(new Pair<>("a", "b"));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_equalObjects_returnsTrue() {
    assertTrue(new Pair<>("1", "2").equals(new Pair<>("1", "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_equalObjectsN1_returnsTrue() {
    assertTrue(new Pair<>(null, "2").equals(new Pair<>(null, "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_equalObjectsN2_returnsTrue() {
    assertTrue(new Pair<>("1", null).equals(new Pair<>("1", null)));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjects_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(new Pair<>("a", "b")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjects1_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(new Pair<>("0", "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjects2_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(new Pair<>("1", "0")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN1_returnsFalse() {
    assertFalse(new Pair<>(null, "2").equals(new Pair<>("1", "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN2_returnsFalse() {
    assertFalse(new Pair<>("1", null).equals(new Pair<>("1", "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN3_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(new Pair<>(null, "2")));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_notEqualObjectsN4_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(new Pair<>("1", null)));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_null_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(null));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @Test
  public void equals_sameObjects_returnsTrue() {
    Pair<String, String> t = new Pair<>("1", "2");
    assertTrue(t.equals(t));
  }

  /** Tests {@link Pair#equals(Object)}. */
  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void equals_unrelatedObject_returnsFalse() {
    assertFalse(new Pair<>("1", "2").equals(""));
  }

  /** Tests {@link Pair#getLeft()}. */
  @Test
  public void getLeft_always_returnsTheLeftElement() {
    assertEquals("a", new Pair<>("a", "b").getLeft());
  }

  /** Tests {@link Pair#getRight()}. */
  @Test
  public void getRight_always_returnsTheRightElement() {
    assertEquals("b", new Pair<>("a", "b").getRight());
  }

  /** Tests {@link Pair#hashCode()}. */
  @Test
  public void hashCode_equalObjects_returnsSameHash() {
    assertEquals(new Pair<>("1", "2").hashCode(), new Pair<>("1", "2").hashCode());
  }

  /** Tests {@link Pair#hashCode()}. */
  @Test
  public void hashCode_equalObjects1_returnsSameHash() {
    assertEquals(new Pair<>(null, "2").hashCode(), new Pair<>(null, "2").hashCode());
  }

  /** Tests {@link Pair#hashCode()}. */
  @Test
  public void hashCode_equalObjects2_returnsSameHash() {
    assertEquals(new Pair<>("1", null).hashCode(), new Pair<>("1", null).hashCode());
  }

  /** Tests {@link Pair#hashCode()}. */
  @Test
  public void hashCode_notEqualObjects_returnsDifferentHash() {
    assertTrue(new Pair<>("1", "2").hashCode() != new Pair<>("a", "b").hashCode());
  }

  /** Tests {@link Pair#toString()}. */
  @Test
  public void toString_always_returnsAString() {
    assertEquals("(a,b)", new Pair<>("a", "b").toString());
  }
}
