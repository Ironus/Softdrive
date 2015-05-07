import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

class IconData {
  protected Icon icon;
  protected Icon expandedIcon;
  protected Object data;

  public IconData(Icon _icon, Object _data) {
    icon = _icon;
    expandedIcon = null;
    data = _data;
  }

  public IconData(Icon _icon, Icon _expandedIcon, Object _data) {
    icon = _icon;
    expandedIcon = _expandedIcon;
    data = _data;
  }

  public Icon getIcon() {
    return icon;
  }

  public Icon getExpandedIcon() {
    return expandedIcon != null ? expandedIcon : icon;
  }

  public Object getObject() {
    return data;
  }

  public String toString() {
    return data.toString();
  }
}
