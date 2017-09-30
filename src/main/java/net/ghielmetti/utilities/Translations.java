package net.ghielmetti.utilities;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read the files named "labels_xx.properties" where xx is the country code specified by the default {@link Locale}. And
 * defines the {@link Properties} accordingly. Then is possible to ask to translate a specific tag (with arguments) to
 * the corresponding string in the properties file.
 *
 * @author Leopoldo Ghielmetti
 */
public class Translations {
  private static final Logger   LOGGER = LoggerFactory.getLogger(Translations.class);
  private static ResourceBundle labels;

  static {
    initialize();
  }

  private Translations() {
    // nothing to do
  }

  /**
   * Initializes the translations.<br>
   * This method can be called when the {@link Locale} has changed to reload the translations.
   */
  public static void initialize() {
    labels = ResourceBundle.getBundle("labels");
  }

  /**
   * Gets the translation for the given key and put the arguments in the returned {@link String}.<br>
   * The arguments are put following the {@link MessageFormat} rules.
   *
   * @param inKey The key to use for the translation.
   * @param inArguments The arguments to put in the String.
   * @return The requested translation.
   */
  public static String translate(final String inKey, final Object... inArguments) {
    try {
      return new MessageFormat(labels.getString(inKey)).format(inArguments);
    } catch (MissingResourceException e) {
      LOGGER.warn("Translation not found for message {}", inKey, e);
      return inKey;
    }
  }
}
