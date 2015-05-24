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

  private JFileChooser fileChooser;

  private ConnectionHandler connectionHandler;

  public ServerFilesPanel() {
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

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
        connectionHandler = ConnectionPanel.connectionHandler;
        if(e.getValueIsAdjusting()) {
          ServerFilesListEntry selectedListElement = fileList.getSelectedValue();

          Rectangle bounds = fileList.getCellBounds(0, fileList.getSelectedIndex());
          int x = bounds.x;
          int y = bounds.y;

          JPopupMenu popup = new JPopupMenu();

          JMenuItem downloadFileMenuItem = new JMenuItem("Download");
          downloadFileMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if(fileChooser.showSaveDialog(ServerFilesPanel.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = selectedListElement.getValue();
                String pathToSave = fileChooser.getSelectedFile().getAbsolutePath();
                connectionHandler.downloadFile(fileName, pathToSave);
              }
            }
          });
          JMenuItem openFolderMenuItem = new JMenuItem("Open");
          openFolderMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

              connectionHandler.openFolder(selectedListElement.getValue());
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
