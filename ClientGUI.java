import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame {
  private Dimension minimumDimension;
  private static short minimumHeight = 500;
  private static short minimumWidth = 350;

  private ConnectionPanel connectionPanel;
  private FileTreePanel fileTreePanel;

  public ClientGUI() {
    super("SoftDrive - Client");
    initGUI();
  }

  private void initGUI() {
    setLayout(new BorderLayout());

    connectionPanel = new ConnectionPanel();
    fileTreePanel = new FileTreePanel();

    add(connectionPanel, BorderLayout.PAGE_START);
    add(fileTreePanel, BorderLayout.CENTER);

    minimumDimension = new Dimension(minimumWidth, minimumHeight);
    setMinimumSize(minimumDimension);
    setSize(getMinimumSize());

    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }
}
