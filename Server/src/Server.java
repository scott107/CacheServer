import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by brenn on 4/25/2017.
 */

public class Server {

    private static int serverPort = 8098;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args){
        
        //Setting up sockets and receiving incoming requests
        
        setupServerSocket();
        while(true){
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted.");
            }catch(IOException e){}

            //Start multithreading for seperate requests
            
            new Thread(
                    new ServerHandleRequest(clientSocket)
            ).start();
        }
    }
    
    //Setting up the server socket

    public static void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(serverPort);
        }catch(Exception e){}
    }

}
