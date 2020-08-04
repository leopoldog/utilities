package net.ghielmetti.utilities.log4j;

import java.util.Enumeration;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.ghielmetti.utilities.swing.JTextAreaPanel;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A TextArea that is updated via the Log4j utility.<br>
 *
 * @author Leopoldo Ghielmetti
 */
public class JTextAreaAppender extends AppenderSkeleton {
  private JTextAreaPanel panel = new JTextAreaPanel();

  /** Constructor, the Log4J library requires a public constructor. */
  public JTextAreaAppender() {
    // nothing to do
  }

  /**
   * Returns a {@link JComponent} containing this TextAreaAppender component.
   *
   * @param inName The name of this {@link JTextAreaAppender}.
   * @return The panel
   */
  public static JTextAreaPanel getTextAreaPanel(final String inName) {
    JTextAreaAppender appender = getAppender(inName);
    return appender == null ? null : appender.panel;
  }

  private static JTextAreaAppender getAppender(final String inName) {
    JTextAreaAppender appender = searchAppender(inName, LogManager.getRootLogger());

    if (appender == null) {
      for (Enumeration<?> loggers = LogManager.getCurrentLoggers(); appender == null && loggers.hasMoreElements();) {
        appender = searchAppender(inName, (Logger)loggers.nextElement());
      }
    }

    return appender;
  }

  private static JTextAreaAppender searchAppender(final String inName, final Logger inLogger) {
    for (Enumeration<?> appenders = inLogger.getAllAppenders(); appenders.hasMoreElements();) {
      Appender appender = (Appender)appenders.nextElement();

      if (appender instanceof JTextAreaAppender && appender.getName().equals(inName)) {
        return (JTextAreaAppender)appender;
      }
    }

    return null;
  }

  @Override
  public void close() {
    panel.close();
  }

  @Override
  public boolean requiresLayout() {
    return true;
  }

  @Override
  protected void append(final LoggingEvent inEvent) {
    if (checkEntryConditions()) {
      StringBuilder message = new StringBuilder(layout.format(inEvent));

      if (layout.ignoresThrowable()) {
        String[] s = inEvent.getThrowableStrRep();

        if (s != null) {
          int len = s.length;

          for (int i = 0; i < len; i++) {
            message.append(s[i]).append(Layout.LINE_SEP);
          }
        }
      }

      // Append formatted message to textArea using the Swing Thread.
      SwingUtilities.invokeLater(() -> panel.append(message.toString()));
    }
  }

  /**
   * This method determines if the append operation can be done.
   *
   * @return <code>true</code> if the conditions are ok.
   */
  private boolean checkEntryConditions() {
    if (layout == null) {
      errorHandler.error("No layout set for the appender named [" + name + "].");
      return false;
    }
    return true;
  }
}
