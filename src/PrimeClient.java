import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PrimeClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 11111;//change this according to the port set in PrimeServer

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            int number = 0;
            String numberInput = JOptionPane.showInputDialog( "enter a number");
            number = Integer.parseInt(numberInput);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(number);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            boolean prime = (boolean) inputStream.readObject();

            if (prime)
                JOptionPane.showMessageDialog(null, "The number you entered is prime");
            else
                JOptionPane.showMessageDialog(null, "The number you entered is not prime");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }//end main

}//end class
