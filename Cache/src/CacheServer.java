import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by brenn on 4/25/2017.
 */

public class CacheServer {

    private static int serverPort = 8080;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args){
        setupServerSocket();
        while(true){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted.");
            }catch(IOException e){}

            new Thread(
                    new CacheHandleRequest(clientSocket)
            ).start();
        }
    }

    public static void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(serverPort);
        }catch(Exception e){}
    }

}