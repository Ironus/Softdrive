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

  private ConnectionPanel connectionPanel;

  public ServerFilesPanel(ConnectionPanel _connectionPanel) {
    connectionPanel = _connectionPanel;
    setLayout(new BorderLayout());

    fileList = new JList<ServerFilesListEntry>();
    listModel = new DefaultListModel<ServerFilesListEntry>();
    listRenderer = new ServerFilesCellRenderer();
    defaultListElement = new ServerFilesListEntry("File names will be shown here", true, FileTreePanel.computerIcon);

    fileList.setModel(listModel);
    fileList.setCellRenderer(listRenderer);
    listModel.addElement(defaultListElement);

    fileList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) {
          ServerFilesListEntry selectedListElement = fileList.getSelectedValue();

          Rectangle bounds = fileList.getCellBounds(fileList.getSelectedIndex(), 0);
          int x = bounds.x;
          int y = bounds.y;

          JPopupMenu popup = new JPopupMenu();

          JMenuItem downloadFileMenuItem = new JMenuItem("Download");
          downloadFileMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              connectionPanel.connectionHandler.downloadFile(selectedListElement.getValue());
            }
          });
          JMenuItem openFolderMenuItem = new JMenuItem("Open");
          openFolderMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              connectionPanel.connectionHandler.openFolder(selectedListElement.getValue());
            }
          });

          if(!selectedListElement.isDirectory()) {
            popup.add(downloadFileMenuItem);
          }
          else {
            if(selectedListElement.getValue() != "File names will be shown here")
              popup.add(openFolderMenuItem);
          }
          popup.show(ServerFilesPanel.this, x, y);
          //selectedListElement.isDirectory();
        }
      }
    });

    Border innerBorder = BorderFactory.createTitledBorder("Server files");
    Border outerBorder = BorderFactory.createEmptyBorder(5, 0, 5, 5);
    setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    add(new JScrollPane(fileList), BorderLayout.CENTER);
  }

  public void addToServerFilesList(String fileName, long isDirectory) {
    if(isDirectory > 0) {
      if(listModel.firstElement() != defaultListElement)
        listModel.addElement(new ServerFilesListEntry(fileName, true, FileTreePanel.folderCloseIcon));
      else
        listModel.setElementAt(new ServerFilesListEntry(fileName, true, FileTreePanel.folderCloseIcon), 0);
    }
    else {
      if(listModel.firstElement() != defaultListElement)
        listModel.addElement(new ServerFilesListEntry(fileName, false, FileTreePanel.fileIcon));
      else
        listModel.setElementAt(new ServerFilesListEntry(fileName, true, FileTreePanel.folderCloseIcon), 0);
    }
  }
}
