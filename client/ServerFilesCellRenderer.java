import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class ServerFilesCellRenderer extends JLabel implements ListCellRenderer<Object> {
  private JLabel label;
  protected Color textSelectionColor;
  protected Color textNonSelectionColor;
  protected Color bkSelectionColor;
  protected Color bkNonSelectionColor;
  protected Color borderSelectionColor;

  public ServerFilesCellRenderer() {
    super();
    textSelectionColor = UIManager.getColor("JList.selectionForeground");
    textNonSelectionColor = UIManager.getColor("JList.textForeground");
    bkSelectionColor = UIManager.getColor("JList.selectionBackground");
    bkNonSelectionColor = UIManager.getColor("JList.textBackground");
    borderSelectionColor = UIManager.getColor("JList.selectionBorderColor");
    setOpaque(false);
  }

  public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
    boolean cellHasFocus) {
    ServerFilesListEntry entry = (ServerFilesListEntry) value;

    setText(value.toString());
    setIcon(entry.getIcon());

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);

    return this;
  }
}
