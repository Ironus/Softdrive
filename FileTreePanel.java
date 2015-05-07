import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTreePanel extends JPanel {
  private JTree fileTree;

  public FileTreePanel() {
    
    fileTree = new JTree(createFileTree());

    setLayout(new BorderLayout());

    add(new JScrollPane(fileTree), BorderLayout.CENTER);
  }

  private DefaultMutableTreeNode createFileTree() {
    DefaultMutableTreeNode top = new DefaultMutableTreeNode();
  }
}
