import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by brenn on 4/25/2017.
 */

public class CacheServer {
    
    //Setting up socket and showing acceptance of request

    private static int serverPort = 8080;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args){
        setupServerSocket();
        while(true){
            try {
                
                //Accepting client
                
                clientSocket = serverSocket.accept();
                System.out.println("Accepted.");
            }catch(IOException e){}

            //Start of multithreading and handling of requests
            
            new Thread(
                    new CacheHandleRequest(clientSocket)
            ).start();
        }
    }
    
    //Creating the server socket

    public static void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(serverPort);
        }catch(Exception e){}
    }

}
