import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSide {
    private static final int PORT = 1234;
    private static InetAddress host;

    public static void main(String[] args) {
        try{
            host = InetAddress.getByName("fe80::8d90:8830:43bc:c726%6");
        } catch (UnknownHostException exception){
            System.out.println("Error Cause" + exception.getMessage());
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket link = new Socket();
        try{
            link = new Socket(host, PORT);
            Scanner scanner = new Scanner(link.getInputStream());

            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            Scanner enter = new Scanner(System.in);
            String message , response;

            do{
                System.out.println("Enter Message : ");
                message = enter.nextLine();
                output.println(message);
                response = scanner.nextLine();
                System.out.println("Received Message : " + response);
            } while (!message.equals("close-connections"));

        } catch (IOException exception){
                exception.printStackTrace();
        }
        finally {
            try{
                System.out.println("Closing Connection");
                link.close();
            } catch (IOException exception){
                System.out.println("Unable to disconnect : " + exception.getMessage());
                System.exit(1);
            }
        }
    }

}
