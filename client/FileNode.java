import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

class FileNode {
  protected File file;

  public FileNode(File _file) {
    file = _file;
  }

  public File getFile() {
    return file;
  }

  public String toString() {
    return file.getName().length() > 0 ? file.getName() : file.getPath();
  }

  public boolean isDirectory() {
    if(file.isDirectory())
      return true;
    return false;
  }

  public boolean expand(DefaultMutableTreeNode parent) {
    DefaultMutableTreeNode flag = (DefaultMutableTreeNode)parent.getFirstChild();

    if (flag == null)
      return false;

    Object object = flag.getUserObject();
    if (!(object instanceof Boolean))
      return false;

    parent.removeAllChildren();

    File[] files = listFiles();
    if (files == null)
      return true;

    Vector<FileNode> vector = new Vector<FileNode>();

    for (int i = 0; i < files.length; i++) {
      File f = files[i];

      if (!(f.isDirectory())){
        FileNode newNode = new FileNode(f);

        boolean isAdded = false;
        for (int j = 0; j < vector.size(); j++) {
          FileNode nd = /*(FileNode)*/vector.elementAt(j);
          if (newNode.compareTo(nd) < 0) {
            vector.insertElementAt(newNode, j);
            isAdded = true;
            break;
          }
        }
        if (!isAdded)
          vector.addElement(newNode);
        continue;
      }

      FileNode newNode = new FileNode(f);

      boolean isAdded = false;
      for (int j = 0; j < vector.size(); j++) {
        FileNode nd = /*(FileNode)*/vector.elementAt(j);
        if (newNode.compareTo(nd) < 0) {
          vector.insertElementAt(newNode, j);
          isAdded = true;
          break;
        }
      }
      if (!isAdded)
        vector.addElement(newNode);
    }

    for (int j = 0; j < vector.size(); j++) {
      FileNode nd = /*(FileNode)*/vector.elementAt(j);
      IconData dirData = new IconData(FileTreePanel.folderCloseIcon, FileTreePanel.folderOpenIcon, nd);
      IconData fileData = new IconData(FileTreePanel.fileIcon, nd);

      DefaultMutableTreeNode node;
      if(vector.elementAt(j).isDirectory())
        node = new DefaultMutableTreeNode(dirData);
      else
        node = new DefaultMutableTreeNode(fileData);

      parent.add(node);

      if (nd.hasSubDirs())
        node.add(new DefaultMutableTreeNode(new Boolean(true)));
    }

    return true;
  }

  public boolean hasSubDirs() {
    File[] files = listFiles();

    if (files == null)
      return false;
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory())
        return true;
    }
    return false;
  }

  public int compareTo(FileNode toCompare) {
    return  file.getName().compareToIgnoreCase(toCompare.file.getName());
  }

  protected File[] listFiles() {
    if (!file.isDirectory())
      return null;

    try {
      return file.listFiles();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error reading directory "+ file.getAbsolutePath(),
        "Warning", JOptionPane.WARNING_MESSAGE);
      return null;
    }
  }

  public String getFileAbsolutePath() {
    return file.getAbsolutePath();
  }
}
