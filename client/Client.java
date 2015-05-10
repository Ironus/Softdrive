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
    /*Scanner keyboardInput = new Scanner(System.in);
    DecimalFormat twoDecimalPlaces = new DecimalFormat("##.00");
    try {
      System.out.println("Connecting to " + args[0] + args[1]);
      InetAddress address = InetAddress.getByName(args[0]);
      int port = Integer.parseInt(args[1]);
      Socket socket = new Socket(address, port);
      System.out.println("Connected to server.");

      System.out.println("Creating output and input streams.");
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      System.out.println("Created.");

      //System.out.print("File to download: ");
      //String path = keyboardInput.nextLine();
      downloadFile("./Mortdecai.mp4", dos, dis);

      System.out.println("Closing streams.");
      dis.close();
      dos.close();

      System.out.println("Closing connection.");
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }

  public static int downloadFile(String fileName, DataOutputStream dos, DataInputStream dis) {
    DecimalFormat twoDecimalPlaces = new DecimalFormat("##.00");
    String path = fileName;
    System.out.println("Sending " + path + " download demand.");
    long fileSize = 0;
    byte[] pathBytes;
    try {
      pathBytes = path.getBytes("UTF-8");
      dos.writeInt(path.length());
      dos.write(pathBytes);
    } catch(Exception e) {
      e.printStackTrace();
    }
    System.out.println("Retrieving file size.");
    try {
      fileSize = dis.readInt();
      System.out.println("File size: " + (fileSize/1024)/1024 + " MB.");
    } catch(Exception e) {
      e.printStackTrace();
    }

    System.out.println("Downloading...");
    int incomingBytes = 0;
    byte[] buffer = new byte[1024];
    long total = 0;
    double totalMB = 0;
    long currentTime;
    long startTime = System.currentTimeMillis() / 1000;
    long middleTime = startTime;
    double downloadSpeed;
    try {
      FileOutputStream fos = new FileOutputStream("Mortdecai2.mp4");
      do {
        incomingBytes = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize));
        fos.write(buffer, 0, incomingBytes);

        total += incomingBytes;
        totalMB = (double)(total/1024)/1024;

        currentTime = System.currentTimeMillis()/1000;
        if((currentTime - middleTime) >= 3) {
          downloadSpeed = (totalMB*8)/(currentTime - startTime);
          System.out.println("Downloaded " + twoDecimalPlaces.format(totalMB)
            + " MB. Speed " + twoDecimalPlaces.format(downloadSpeed) + "Mb/s.");
          middleTime = currentTime;
        }
        fileSize -= incomingBytes;
      } while (fileSize > 0 && incomingBytes != -1);
      long endTime = System.currentTimeMillis() / 1000;
      downloadSpeed = (totalMB*8)/(endTime - startTime);
      System.out.println("Downloaded. Total time: " + Math.round((endTime - startTime))
        + "sec. Average speed " + twoDecimalPlaces.format(downloadSpeed) + "Mb/s.");
      fos.close();
      return 0;
    } catch (IOException ex) {
        ex.printStackTrace();
        return -1;
    }
  }
}
