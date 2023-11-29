import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PrimeServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(11111);
            System.out.println("Server listening...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                int number = (int) inputStream.readObject();

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(isPrime(number));

                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }//end main


    static boolean isPrime(int number)
    {
        if (number <= 1)
            return false;

        for (int i = 2; i < number; i++)
            if (number % i == 0)
                return false;

        return true;
    }//end isPrime
}//end class
