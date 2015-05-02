import java.io.*;
import java.net.*;

public class Client {

  public static void main(String[] args) {
    try {
      // ustal adres serwera
      InetAddress addr = InetAddress.getByName(args[0]);

      // ustal port
      int port = Integer.parseInt(args[1]);

      // utworz gniazdo i od razu podlacz je
      // do addr:port
      Socket socket = new Socket(addr, port);

      // pobierz strumienie i zbuduj na nich
      // "lepsze" strumienie
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      DataInputStream dis = new DataInputStream(socket.getInputStream());

      String path = "./Mortdecai.mp4";
      byte[] pathBytes = path.getBytes("UTF-8");

      dos.writeInt(path.length());
      dos.write(pathBytes);

      int fileSize = dis.readInt();
      System.out.println(fileSize);

      byte[] buffer = new byte[1024];
      int incomingBytes;
      int total = 0;
      try {
        FileOutputStream fos = new FileOutputStream("Mortdecai2.mp4");
        do {
          incomingBytes = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize));
          fos.write(buffer, 0, incomingBytes);
          fileSize -= incomingBytes;
        }while (fileSize > 0 && incomingBytes != -1);
        fos.close();

      } catch (IOException ex) {
                // Do exception handling
      }

      dis.close();
      dos.close();

            // koniec rozmowy
      socket.close();

      // moga byc wyjatki dot. gniazd,
      // getByName, parseInt i strumieni
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Klient zakonczyl dzialanie");
  }
}
