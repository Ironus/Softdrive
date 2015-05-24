import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class ServerFilesListEntry {
   private String value;
   private ImageIcon icon;
   private boolean directory;

   public ServerFilesListEntry(String _value, boolean _directory, ImageIcon _icon) {
      value = _value;
      directory = _directory;
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

   public boolean isDirectory() {
     return directory;
   }
}
