package net.ghielmetti.utilities.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.ghielmetti.utilities.swing.JTextAreaPanel;
import org.slf4j.LoggerFactory;

/**
 * A TextArea that is updated via the Log4j utility.<br>
 *
 * @author Leopoldo Ghielmetti
 * @param <E> The event object to append.
 */
public class JTextAreaAppender<E> extends AppenderBase<E> {
  private Encoder<E>     encoder;
  private JTextAreaPanel panel = new JTextAreaPanel();

  /**
   * Returns a {@link JComponent} containing this TextAreaAppender component.
   *
   * @param inName The name of this {@link JTextAreaAppender}.
   * @return The panel
   */
  public static <E> JTextAreaPanel getTextAreaPanel(final String inName) {
    JTextAreaAppender<E> appender = getAppender(inName);
    return appender == null ? null : appender.panel;
  }

  private static <E> JTextAreaAppender<E> getAppender(final String inName) {
    JTextAreaAppender<E> appender = null;
    LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();

    for (Logger logger : context.getLoggerList()) {
      appender = searchAppender(inName, logger);
      if (appender != null) {
        break;
      }
    }

    return appender;
  }

  @SuppressWarnings("unchecked")
  private static <E> JTextAreaAppender<E> searchAppender(final String inName, final Logger inLogger) {
    for (Iterator<Appender<ILoggingEvent>> index = inLogger.iteratorForAppenders(); index.hasNext();) {
      Appender<ILoggingEvent> appender = index.next();
      if (appender instanceof JTextAreaAppender && appender.getName().equals(inName)) {
        return (JTextAreaAppender<E>)appender;
      }
    }
    return null;
  }

  /**
   * Getter.
   *
   * @return The {@link Encoder} used to render the objects.
   */
  public Encoder<E> getEncoder() {
    return encoder;
  }

  /**
   * Defines the encoder to use to render the messages.
   *
   * @param inEncoder The {@link Layout}.
   */
  public void setEncoder(final Encoder<E> inEncoder) {
    encoder = inEncoder;
  }

  /**
   * Defines the layout to use to render the messages.
   *
   * @param inLayout The {@link Layout}.
   */
  public void setLayout(final Layout<E> inLayout) {
    LayoutWrappingEncoder<E> lwe = new LayoutWrappingEncoder<>();
    lwe.setLayout(inLayout);
    lwe.setContext(context);
    encoder = lwe;
  }

  @Override
  protected void append(final E inEvent) {
    if (checkEntryConditions()) {
      String message = new String(encoder.encode(inEvent));

      // Append formatted message to textArea using the Swing Thread.
      SwingUtilities.invokeLater(() -> panel.append(message));
    }
  }

  /**
   * This method determines if the append operation can be done.
   *
   * @return <code>true</code> if the conditions are ok.
   */
  private boolean checkEntryConditions() {
    if (encoder == null) {
      addError("No layout set for the appender named [" + name + "].");
      return false;
    }
    return true;
  }
}
