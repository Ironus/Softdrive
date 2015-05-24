import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;

public class Client {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ClientGUI mainFrame = new ClientGUI();
      }
    });
  }
}
