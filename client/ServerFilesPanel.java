import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerFilesPanel extends JPanel {
  private JList<ServerFilesListEntry> fileList;
  private DefaultListModel<ServerFilesListEntry> listModel;
  private ServerFilesCellRenderer listRenderer;
  private ServerFilesListEntry defaultListElement;

  public ServerFilesPanel() {
    setLayout(new BorderLayout());

    fileList = new JList<ServerFilesListEntry>();
    listModel = new DefaultListModel<ServerFilesListEntry>();
    listRenderer = new ServerFilesCellRenderer();
    defaultListElement = new ServerFilesListEntry("File names will be shown here", FileTreePanel.computerIcon);

    fileList.setModel(listModel);
    fileList.setCellRenderer(listRenderer);
    listModel.addElement(defaultListElement);

    Border innerBorder = BorderFactory.createTitledBorder("Server files");
    Border outerBorder = BorderFactory.createEmptyBorder(5, 0, 5, 5);
    setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    add(new JScrollPane(fileList), BorderLayout.CENTER);
  }

  public void addToServerFilesList(String fileName, long isDirectory) {
    if(isDirectory > 0) {
      if(listModel.firstElement() != defaultListElement)
        listModel.addElement(new ServerFilesListEntry(fileName, FileTreePanel.folderCloseIcon));
      else
        listModel.setElementAt(new ServerFilesListEntry(fileName, FileTreePanel.folderCloseIcon), 0);
    }
    else {
      if(listModel.firstElement() != defaultListElement)
        listModel.addElement(new ServerFilesListEntry(fileName, FileTreePanel.fileIcon));
      else
        listModel.setElementAt(new ServerFilesListEntry(fileName, FileTreePanel.folderCloseIcon), 0);
    }
  }
}
