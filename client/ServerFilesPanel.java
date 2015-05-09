import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerFilesPanel extends JPanel {
  private JList<String> fileList;
  private DefaultListModel<String> emptyListModel;

  public ServerFilesPanel() {
    setLayout(new BorderLayout());

    fileList = new JList<String>();
    emptyListModel = new DefaultListModel<String>();
    emptyListModel.addElement("File names will be shown here");
    fileList.setModel(emptyListModel);

    Border innerBorder = BorderFactory.createTitledBorder("Server files");
    Border outerBorder = BorderFactory.createEmptyBorder(5, 0, 5, 5);
    setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    add(new JScrollPane(fileList), BorderLayout.CENTER);
  }
}
