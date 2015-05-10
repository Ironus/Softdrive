import java.awt.*;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame {
  private Dimension minimumDimension;
  public static short minimumHeight = 410;
  public static short minimumWidth = 500;

  private ConnectionPanel connectionPanel;
  private FileTreePanel fileTreePanel;
  public ServerFilesPanel serverFilesPanel;

  private JPanel filesPanel;

  public ClientGUI() {
    super("SoftDrive - Client");
    initGUI();
  }

  private void initGUI() {
    setLayout(new BorderLayout());

    serverFilesPanel = new ServerFilesPanel();
    connectionPanel = new ConnectionPanel(serverFilesPanel);
    fileTreePanel = new FileTreePanel();

    filesPanel = new JPanel();
    filesPanel.setLayout(new GridBagLayout());

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0;
    gc.gridy = 0;
    gc.weightx = 1;
    gc.weighty = 1;
    gc.fill = GridBagConstraints.BOTH;

    filesPanel.add(fileTreePanel, gc);

    gc.gridx = 1;
    filesPanel.add(serverFilesPanel, gc);

    add(connectionPanel, BorderLayout.PAGE_START);
    add(filesPanel, BorderLayout.CENTER);

    minimumDimension = new Dimension(minimumWidth, minimumHeight);
    setMinimumSize(minimumDimension);
    setSize(getMinimumSize());

    setLocationRelativeTo(null);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        connectionPanel.closeConnection();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
        dispose();
      }
    });

    setVisible(true);
  }
}
