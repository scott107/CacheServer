
import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by brenn on 5/4/2017.
 */
public class CacheHandleRequest implements Runnable {

    private Socket clientSocket;
    private String rootDir = "C:\\CS";
    private String target;
    private Long epoch;
    private String url = "localhost";

    //Starting to handle request
    
    CacheHandleRequest(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        try{

            //Using sleep to show off multithreading
            
            System.out.println("Request received.");
            //TimeUnit.SECONDS.sleep(3);
            long epoch2 = System.currentTimeMillis();
            byte[] inputByteArray = new byte[1000];
            byte[] outputByteArray = new byte[1000];
            
            //Starting to read request and initialize the output stream

            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            in.read(inputByteArray, 0, 1000);
            String data = new String(inputByteArray);
            System.out.println(data);
            
            //Seperating out the information in the request

            String[] lines =  data.split(System.getProperty("line.separator"));

            //Getting the filename
            
            for(String line: lines)
                if(line.contains("Filename"))
                    target = line.split(" ")[1];

            final File file = new File(rootDir, URLDecoder.decode(target, "UTF-8"));
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                
                //If the file isn't in cache send request to origin serer

                System.out.println("not found");
                client Client = new client(file.getAbsolutePath());
                FileInputStream fis = new FileInputStream(file);
                fis.read(outputByteArray);
                long epoch3 = System.currentTimeMillis();
                out.write(("HTTP/1.1 200\r\nTime2: " + epoch2 + "\r\nTime3: " + epoch3 + "\r\n\r\n" + new String(outputByteArray)).getBytes());
            }else if(!file.canRead()){
                System.out.println("Error: cannot read from file at this time.");
                
                //Something went wrong can't read from file
                
            }else{
                
                //Registering file is in cahce and sending it out
                
                System.out.println("here");
                FileInputStream fis = new FileInputStream(file);
                fis.read(outputByteArray);
                long epoch3 = System.currentTimeMillis();
                out.write(("HTTP/1.1 200\r\nTime2: " + epoch2 + "\r\nTime3: " + epoch3 + "\r\n\r\n" + new String(outputByteArray)).getBytes());
            }

            //Closing the streams and finishing the request

            out.close();
            in.close();

            //TimeUnit.SECONDS.sleep(10);

            System.out.println("done");
        }catch(Exception e){

        }
    }
}
