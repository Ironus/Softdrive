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

      String path = "./img1.JPG";
      byte[] pathBytes = path.getBytes("UTF-8");

      dos.writeInt(path.length());
      dos.write(pathBytes);

      int number = dis.readInt();
      System.out.println(number);
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
