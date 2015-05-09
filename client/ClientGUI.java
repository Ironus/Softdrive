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
  private ServerFilesPanel serverfilesPanel;

  private JPanel filesPanel;

  public ClientGUI() {
    super("SoftDrive - Client");
    initGUI();
  }

  private void initGUI() {
    setLayout(new BorderLayout());

    connectionPanel = new ConnectionPanel();
    fileTreePanel = new FileTreePanel();
    serverfilesPanel = new ServerFilesPanel();

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
    filesPanel.add(serverfilesPanel, gc);

    add(connectionPanel, BorderLayout.PAGE_START);
    add(filesPanel, BorderLayout.CENTER);

    minimumDimension = new Dimension(minimumWidth, minimumHeight);
    setMinimumSize(minimumDimension);
    setSize(getMinimumSize());

    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }
}
