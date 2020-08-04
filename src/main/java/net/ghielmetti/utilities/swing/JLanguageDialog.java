package net.ghielmetti.utilities.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import net.ghielmetti.utilities.Translations;

/**
 * Defines a dialog window that can be used to select a locale.
 *
 * @author Leopoldo Ghielmetti
 */
public class JLanguageDialog extends JDialog {
  private Locale        language = Locale.getDefault();
  private JButton       buttonCancel;
  private JButton       buttonOk;
  private JList<Locale> localeList;

  /**
   * Constructor.<br>
   * Opens a dialog with the list of available Locales.
   *
   * @param inOwner The owner for this window.
   */
  public JLanguageDialog(final Frame inOwner) {
    super(inOwner, Translations.translate("dialog.chooseLanguage.title"), true);

    getContentPane().add(getListPanel(), BorderLayout.CENTER);
    getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);

    buttonOk.setEnabled(false);
    localeList.setSelectedValue(language, true);

    buttonOk.addActionListener(e -> setLanguage());
    buttonCancel.addActionListener(e -> dispose());

    getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    getRootPane().setDefaultButton(buttonOk);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(inOwner);
    setPreferredSize(new Dimension(200, 200));
    pack();
  }

  /**
   * Returns the selected Locale.
   *
   * @return The selected Locale.
   */
  public Locale getLanguage() {
    return language;
  }

  /**
   * Returns an array of all the available Locale.<br>
   * This method can be overridden to specify the Locale to propose.
   *
   * @return The available Locales.
   */
  protected Locale[] getAvailableLocales() {
    return Locale.getAvailableLocales();
  }

  private JPanel getButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonOk = new JButton(Translations.translate("default.button.ok"));
    buttonCancel = new JButton(Translations.translate("default.button.cancel"));
    buttonPanel.add(buttonOk);
    buttonPanel.add(buttonCancel);
    return buttonPanel;
  }

  private JPanel getListPanel() {
    JPanel listPanel = new JPanel(new BorderLayout(5, 5));
    Locale[] locales = getAvailableLocales();
    Arrays.sort(locales, (l1, l2) -> l1.toString().compareTo(l2.toString()));
    localeList = new JList<>(locales);
    JScrollPane scroll = new JScrollPane(localeList);
    localeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    localeList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 2) {
          setLanguage();
        }
      }
    });
    listPanel.add(scroll, BorderLayout.CENTER);
    listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    localeList.addListSelectionListener(e -> buttonOk.setEnabled(localeList.getSelectedIndex() >= 0));
    return listPanel;
  }

  /** Internally used to set the language. */
  void setLanguage() {
    language = localeList.getSelectedValue();
    dispose();
  }
}
