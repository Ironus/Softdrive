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
      int number = dis.readInt();
      dos.write(110);
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
