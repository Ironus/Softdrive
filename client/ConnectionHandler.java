//import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Scanner;
import javax.swing.*;

public class ConnectionHandler {
  private ServerFilesPanel serverFilesPanel;
  private InetAddress address;
  private int port;

  private Socket socket;
  private DataOutputStream dos;
  private DataInputStream dis;

  private ProgressDialog progressDialog;
  private static int fileSize;

  public boolean isConnected;

  public ConnectionHandler(ServerFilesPanel _serverFilesPanel, String ipAddress, String port) {
    isConnected = false;
    progressDialog = new ProgressDialog();

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

  public void downloadFile(String fileName, String pathToSave) {
    try {
      String fileDw = "fileDw";
      dos.write(fileDw.getBytes("UTF-8"));
    } catch(Exception e) {
      e.printStackTrace();
    }

    DecimalFormat twoDecimalPlaces = new DecimalFormat("##.00");

    try {
      dos.write(fileName.getBytes("UTF-8"));
    } catch(Exception e) {
      e.printStackTrace();
    }

    try {
      fileSize = dis.readInt();
    } catch(Exception e) {
      e.printStackTrace();
    }
    progressDialog.setTitle("Downloading...");
    progressDialog.setMaximum(fileSize);
    progressDialog.setVisible(true);

    SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
      protected void done() {
        try {
          progressDialog.setVisible(false);
        } catch(Exception e) {
          e.printStackTrace();
        }
      }

      protected void process(List<Integer> received) {
        int total = received.get(received.size() - 1);

        progressDialog.setValue(total);
      }

      protected Void doInBackground() throws Exception {
        try {
          int incomingBytes = 0;
          byte[] buffer = new byte[1024];
          int total = 0;
          double totalMB = 0;
          long currentTime;
          long startTime = System.currentTimeMillis() / 1000;
          long middleTime = startTime;
          double downloadSpeed;

          FileOutputStream fos = new FileOutputStream(pathToSave + "\\" + fileName);
          do {
            incomingBytes = dis.read(buffer, 0, (int) Math.min(buffer.length, ConnectionHandler.fileSize));
            fos.write(buffer, 0, incomingBytes);

            total += incomingBytes;
            totalMB = (double)(total/1024)/1024;

            currentTime = System.currentTimeMillis()/1000;
            fileSize -= incomingBytes;
            publish(total);
          } while (fileSize > 0 && incomingBytes != -1);
          long endTime = System.currentTimeMillis() / 1000;
          downloadSpeed = (totalMB*8)/(endTime - startTime);
          System.out.println("Downloaded. Total time: " + Math.round((endTime - startTime))
            + "sec. Average speed " + twoDecimalPlaces.format(downloadSpeed) + "Mb/s.");
          fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
      }
    };
    worker.execute();
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
