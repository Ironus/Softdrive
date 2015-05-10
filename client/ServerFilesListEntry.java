import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class ServerFilesListEntry {
   private String value;
   private ImageIcon icon;

   public ServerFilesListEntry(String _value, ImageIcon _icon) {
      value = _value;
      icon = _icon;
   }

   public String getValue() {
      return value;
   }

   public ImageIcon getIcon() {
      return icon;
   }

   public String toString() {
      return value;
   }
}
