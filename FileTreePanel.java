import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTreePanel extends JPanel {
  public static final ImageIcon computerIcon = new ImageIcon("./images/computerIcon.png");
  public static final ImageIcon hardDriveIcon = new ImageIcon("./images/hardDriveIcon.png");
  public static final ImageIcon folderCloseIcon = new ImageIcon("./images/folderCloseIcon.png");
  public static final ImageIcon folderOpenIcon = new ImageIcon("./images/folderOpenIcon.png");

  private JTree fileTree;
  private DefaultTreeModel fileTreeModel;

  public FileTreePanel() {
    createFileTree();
    setLayout(new BorderLayout());

    add(new JScrollPane(fileTree), BorderLayout.CENTER);
  }

  private void createFileTree() {
    DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(computerIcon, null, "Computer"));
    DefaultMutableTreeNode node;

    File[] roots = File.listRoots();
    for(int i = 0; i < roots.length; i++) {
      node = new DefaultMutableTreeNode(new IconData(hardDriveIcon, null, new FileNode(roots[i])));
      top.add(node);
      node.add(new DefaultMutableTreeNode(new Boolean(true)));
    }

    fileTreeModel = new DefaultTreeModel(top);
    fileTree = new JTree(fileTreeModel);

    fileTree.putClientProperty("JTree.lineStyle", "Angled");

    TreeCellRenderer renderer = new IconCellRenderer();
    fileTree.setCellRenderer(renderer);

    fileTree.addTreeExpansionListener(new TreeExpansionListener() {
      public void treeExpanded(TreeExpansionEvent event) {
        final DefaultMutableTreeNode node = getTreeNode(event.getPath());
        final FileNode fileNode = getFileNode(node);

        Thread runner = new Thread(){
          public void run() {
            if (fileNode != null && fileNode.expand(node)) {
              Runnable runnable = new Runnable() {
                public void run() {
                  fileTreeModel.reload(node);
                }
              };
              SwingUtilities.invokeLater(runnable);
            }
          }
        };
        runner.start();
      }

      public void treeCollapsed(TreeExpansionEvent event) {}
    });

    fileTree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent event) {
        DefaultMutableTreeNode node = getTreeNode(event.getPath());
        FileNode fileNode = getFileNode(node);
        if (fileNode != null) {
          //display.setText(fileNode.getFile().getAbsolutePath());
        }
        else {
          //display.setText("");
        }
      }
    });

    fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    fileTree.setShowsRootHandles(true);
    fileTree.setEditable(false);
  }

  DefaultMutableTreeNode getTreeNode(TreePath path) {
    return (DefaultMutableTreeNode)(path.getLastPathComponent());
  }

  FileNode getFileNode(DefaultMutableTreeNode node) {
    if (node == null)
      return null;
    Object object = node.getUserObject();
    if (object instanceof IconData)
      object = ((IconData)object).getObject();
    if (object instanceof FileNode)
      return (FileNode)object;
    else
      return null;
  }
}
