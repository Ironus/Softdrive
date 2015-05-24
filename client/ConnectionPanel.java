import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ConnectionPanel extends JPanel{
  private JTextField ipAddressTextArea;
  private JTextField portTextArea;
  private JButton connectButton;

  public static ConnectionHandler connectionHandler;
  public ServerFilesPanel serverFilesPanel;

  public ConnectionPanel(ServerFilesPanel _serverFilesPanel) {
    serverFilesPanel = _serverFilesPanel;
    setLayout(new FlowLayout());

    ipAddressTextArea = new JTextField(15);
    portTextArea = new JTextField(5);

    connectButton = new JButton("Connect");
    connectButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        connectionHandler = new ConnectionHandler(serverFilesPanel, ipAddressTextArea.getText(), portTextArea.getText());
      }
    });

    add(ipAddressTextArea);
    add(portTextArea);
    add(connectButton);
  }

  public void closeConnection() {
    if(connectionHandler != null)
      connectionHandler.closeConnection();
  }
}
