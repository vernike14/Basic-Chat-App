import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    // this is a tcp based socket
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        System.out.println("OPENING PORT " + PORT + "\n");

        try{
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Unable to attach to port : " + e.getMessage());
            System.exit(1);
        }
        do {
            handleClient();
        } while (true);

    }

    public static void handleClient() {
        Socket link = null;
        try {
            link = serverSocket.accept();

            Scanner scanner = new Scanner(link.getInputStream());
            PrintWriter printWriter = new PrintWriter(link.getOutputStream(), true);
            int numMessage = 0;
            String message = scanner.nextLine();

            while (!message.equals("close-connections")){
                System.out.println("message received : " + message);
                numMessage++;
                Scanner scanner1 = new Scanner(System.in);
                String outMessage =  scanner1.nextLine();
                printWriter.println(outMessage);
                message = scanner.nextLine();
                printWriter.println(numMessage + " messages received");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                System.out.println("+++Closing+++");
                link.close();
            } catch (IOException e){
                System.out.println("Connection Closed " + e.getMessage());
                System.exit(1);
            }
        }
    }
}