import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

class IconCellRenderer extends JLabel implements TreeCellRenderer {
  protected Color textSelectionColor;
  protected Color textNonSelectionColor;
  protected Color bkSelectionColor;
  protected Color bkNonSelectionColor;
  protected Color borderSelectionColor;

  protected boolean selected;

  public IconCellRenderer() {
    super();
    textSelectionColor = UIManager.getColor("Tree.selectionForeground");
    textNonSelectionColor = UIManager.getColor("Tree.textForeground");
    bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
    bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
    borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
    setOpaque(false);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
    boolean leaf, int row, boolean hasFocus) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
    Object object = node.getUserObject();
    setText(object.toString());

    if (object instanceof Boolean)
      setText("Retrieving data...");

    if (object instanceof IconData) {
      IconData idata = (IconData)object;
      if (expanded)
        setIcon(idata.getExpandedIcon());
      else
        setIcon(idata.getIcon());
    }
    else
      setIcon(null);

    setFont(tree.getFont());
    setForeground(sel ? textSelectionColor : textNonSelectionColor);
    setBackground(sel ? bkSelectionColor : bkNonSelectionColor);
    selected = sel;
    return this;
  }

  public void paintComponent(Graphics g) {
    Color bColor = getBackground();
    Icon icon = getIcon();

    g.setColor(bColor);
    int offset = 0;
    if(icon != null && getText() != null)
      offset = (icon.getIconWidth() + getIconTextGap());
    g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

    if (selected) {
      g.setColor(borderSelectionColor);
      g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
    }
    super.paintComponent(g);
  }
}
