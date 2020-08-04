package net.ghielmetti.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Constructor;
import java.util.Locale;
import org.junit.Test;

/**
 * Tests the {@link Translations} class.
 *
 * @author Leopoldo Ghielmetti
 */
public class TranslationsTest {
  /**
   * Tests {@link Translations} constructor inaccessible.
   *
   * @throws Exception Not expected.
   */
  @Test
  public void constructor_always_isPrivate() throws Exception {
    Constructor<?>[] constructors = Translations.class.getDeclaredConstructors();
    assertEquals(1, constructors.length);

    assertFalse(constructors[0].isAccessible());

    // For coverage only, we call the constructor!
    constructors[0].setAccessible(true);
    constructors[0].newInstance((Object[])null);
  }

  /** Tests {@link Translations#translate(String, Object...)}. */
  @Test
  public void translate_aKnownStringWithArgumentsForADefinedLanguage_returnsTheTranslationFromRightPropertiesFile() {
    Locale.setDefault(Locale.ITALIAN);
    Translations.initialize();
    assertEquals("Una stringa conosciuta con 1 argomento.", Translations.translate("known.string.with.arguments", Integer.valueOf(1)));
  }

  /** Tests {@link Translations#translate(String, Object...)}. */
  @Test
  public void translate_aKnownStringWithArgumentsForNotDefinedLanguage_returnsTheTranslationFromDefaultProperties() {
    Locale.setDefault(Locale.ENGLISH);
    Translations.initialize();
    assertEquals("A known string with 1 argument.", Translations.translate("known.string.with.arguments", Integer.valueOf(1)));
  }

  /** Tests {@link Translations#translate(String, Object...)}. */
  @Test
  public void translate_aKnownStringWithoutArgumentsForADefinedLanguage_returnsTheTranslationFromRightPropertiesFile() {
    Locale.setDefault(Locale.ITALIAN);
    Translations.initialize();
    assertEquals("Una stringa conosciuta senza argomenti.", Translations.translate("known.string.without.arguments"));
  }

  /** Tests {@link Translations#translate(String, Object...)}. */
  @Test
  public void translate_aKnownStringWithoutArgumentsForNotDefinedLanguage_returnsTheTranslationFromDefaultProperties() {
    Locale.setDefault(Locale.ENGLISH);
    Translations.initialize();
    assertEquals("A known string without arguments.", Translations.translate("known.string.without.arguments"));
  }

  /** Tests {@link Translations#translate(String, Object...)}. */
  @Test
  public void translate_anUnknownString_returnsTheOriginalString() {
    Translations.initialize();
    assertEquals("unknown.string", Translations.translate("unknown.string"));
  }
}
