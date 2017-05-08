
import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by brenn on 5/4/2017.
 */
public class ServerHandleRequest implements Runnable {

    private Socket clientSocket;
    private String rootDir = "C:\\CS";
    private String target;
    private Long epoch;

    ServerHandleRequest(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        try{

            //Setting up the request handling

            System.out.println("Request received.");
            //TimeUnit.SECONDS.sleep(10);
            long epoch = System.currentTimeMillis() / 1000;
            byte[] inputByteArray = new byte[1000];
            byte[] outputByteArray = new byte[1000];

            //Starting the file streams

            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            in.read(inputByteArray, 0, 1000);
            String data = new String(inputByteArray);
            System.out.println(data);

            //Parsing out the information in the request

            String[] lines =  data.split(System.getProperty("line.separator"));

            //Getting the filename

            for(String line: lines)
                if(line.contains("Filename"))
                    target = line.split(" ")[1];

            final File file = new File(rootDir, URLDecoder.decode(target, "UTF-8"));
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                
                //Registering the file doesn't exist and sending such info

                out.write(("HTTP/1.1 404\r\nTime: " + epoch + "\r\n\r\nFile does not exist.").getBytes());
            }else if(!file.canRead()){
                
                //Something went wrong can't read from file
                
            }else{
                
                //Registering file is on server and sending the file

                FileInputStream fis = new FileInputStream(file);
                fis.read(outputByteArray);

                out.write(("HTTP/1.1 200\r\nTime: " + epoch + "\r\n\r\n" + new String(outputByteArray)).getBytes());
            }

            //Closing the streams

            out.close();
            in.close();

            //Outputting completion of request

            System.out.println("done");
        }catch(Exception e){

        }
    }
}
