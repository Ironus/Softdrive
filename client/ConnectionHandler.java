import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Scanner;
import javax.swing.*;

public class ConnectionHandler {
  private ServerFilesPanel serverFilesPanel;
  private InetAddress address;
  private int port;

  private Socket socket;
  private DataOutputStream dos;
  private DataInputStream dis;

  public boolean isConnected;

  public ConnectionHandler(ServerFilesPanel _serverFilesPanel, String ipAddress, String port) {
    isConnected = false;
    try {
      serverFilesPanel = _serverFilesPanel;
      address = InetAddress.getByName(ipAddress);
      this.port = Integer.parseInt(port);
      connectionHandle();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  private void connectionHandle() {
    try {
      socket = new Socket(address, port);

      dos = new DataOutputStream(socket.getOutputStream());
      dis = new DataInputStream(socket.getInputStream());

      getCatalogueList();
      isConnected = true;
      /*//System.out.print("File to download: ");
      //String path = keyboardInput.nextLine();
      downloadFile("./Mortdecai.mp4", dos, dis);

      System.out.println("Closing streams.");
      dis.close();
      dos.close();

      System.out.println("Closing connection.");
      socket.close();*/
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getCatalogueList() {
    try {
      String fileLi = "fileLi";
      dos.write(fileLi.getBytes("UTF-8"));

      int countFiles = dis.readInt(), fileNameLength;
      long isDirectory;
      byte[] fileName;
      String fileNameString;
      String dirToIgnore1 = new String(".");
      String dirToIgnore2 = new String("..");
      for(int i = 0 ; i < countFiles; i++) {
        isDirectory = dis.readInt();
        fileNameLength = dis.readInt();
        fileName = new byte[fileNameLength];
        dis.read(fileName, 0, fileName.length);

        fileNameString = new String(fileName);
        if(!fileNameString.equals(dirToIgnore1) && !fileNameString.equals(dirToIgnore2))
          serverFilesPanel.addToServerFilesList(new String(fileName), isDirectory);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void openFolder(String folderName) {
    
  }

  public void sendFile(String path, String fileName) {
    try{
      String fileUp = "fileUp";
      dos.write(fileUp.getBytes("UTF-8"));
      dos.write(fileName.getBytes("UTF-8"));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void downloadFile(String fileName) {

  }

  public void closeConnection() {
    try {
      String endCon = "endCon";
      dos.write(endCon.getBytes("UTF-8"));
      dos.close();
      dis.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
