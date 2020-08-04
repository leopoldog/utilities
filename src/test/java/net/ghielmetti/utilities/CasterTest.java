package net.ghielmetti.utilities;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.junit.Test;

/**
 * Tests the {@link Caster} class.
 *
 * @author lghi
 */
public class CasterTest {
  /** Tests {@link Caster#cast(java.util.Collection)}. */
  @Test
  public void cast_aCollection_castTheObject() {
    Collection<String> a = new ArrayList<>();
    Collection<Long> b = Caster.cast(a);
    assertSame(a, b);
  }

  /** Tests {@link Caster#cast(java.util.List)}. */
  @Test
  public void cast_aList_castTheObject() {
    List<String> a = new ArrayList<>();
    List<Long> b = Caster.cast(a);
    assertSame(a, b);
  }

  /** Tests {@link Caster#cast(java.util.Enumeration)}. */
  @Test
  public void cast_anEnumeration_castTheObject() {
    Enumeration<String> a = new Vector<String>().elements();
    Enumeration<Long> b = Caster.cast(a);
    assertSame(a, b);
  }

  /** Tests {@link Caster#cast(java.util.Set)}. */
  @Test
  public void cast_aSet_castTheObject() {
    Set<String> a = new HashSet<>();
    Set<Long> b = Caster.cast(a);
    assertSame(a, b);
  }

  /** Tests {@link Caster#castAny(Object)}. */
  @Test
  public void castAny_anything_castTheObject() {
    String a = "test";
    String b = Caster.castAny(a);
    assertSame(a, b);
  }
}
