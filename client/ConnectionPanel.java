import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ConnectionPanel extends JPanel{
  private JTextField ipAddressTextArea;
  private JTextField portTextArea;
  private JButton connectButton;

  public ConnectionPanel() {
    setLayout(new FlowLayout());

    ipAddressTextArea = new JTextField(15);
    portTextArea = new JTextField(5);

    connectButton = new JButton("Connect");
    connectButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        ConnectionHandler connectionHandler = new ConnectionHandler(ipAddressTextArea.getText(), portTextArea.getText());
      }
    });

    add(ipAddressTextArea);
    add(portTextArea);
    add(connectButton);
  }
}
