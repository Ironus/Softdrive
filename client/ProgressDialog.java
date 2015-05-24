import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {
  private JButton okButton;
  private JProgressBar progressBar;

  public ProgressDialog() {
    okButton = new JButton("OK");
    progressBar = new JProgressBar();

    setModalityType(ModalityType.APPLICATION_MODAL);
    setLocationRelativeTo(null);
    setLayout(new FlowLayout());

    Dimension size = okButton.getPreferredSize();
    size.width = progressBar.getPreferredSize().width * 2;
    progressBar.setPreferredSize(size);

    add(progressBar);
    add(okButton);

    pack();
  }

  public void setTile(String title) {
    setTitle(title);
  }

  public void setMaximum(int value) {
    progressBar.setMaximum(value);
  }

  public void setValue(int value) {
    progressBar.setValue(value);
  }

  public void setVisible(final boolean visible) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ProgressDialog.super.setVisible(visible);
      }
    });
  }
}
